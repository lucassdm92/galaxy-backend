package com.galaxybck.model.service;

import com.galaxybck.exception.PriceCalculationNotFoundException;
import com.galaxybck.exception.UserNotFoundExcpetion;
import com.galaxybck.model.dto.ClientResponse;
import com.galaxybck.model.dto.PriceCalculatorResponse;
import com.galaxybck.model.dto.PriceConfigResponse;
import com.galaxybck.model.dto.RequestPrice;
import com.galaxybck.model.entity.ClientEntity;
import com.galaxybck.model.entity.PriceCalculation;
import com.galaxybck.model.entity.PriceConfig;
import com.galaxybck.model.entity.User;
import com.galaxybck.model.repository.PriceCalculationRepository;
import com.galaxybck.model.repository.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class PriceService {

    private static final Logger log = LoggerFactory.getLogger(PriceService.class);

    private final PriceRepository priceRepository;
    private final GoogleMapsService googleMapsService;
    private final PriceCalculationRepository priceCalculationRepository;

    private final UserService userService;

    private final ClientService clientService;

    public PriceService(PriceRepository priceRepository, GoogleMapsService googleMapsService, PriceCalculationRepository priceCalculationRepository, ClientService clientService, UserService userService) {
        this.priceRepository = priceRepository;
        this.googleMapsService = googleMapsService;
        this.priceCalculationRepository = priceCalculationRepository;
        this.clientService = clientService;
        this.userService = userService;
    }

    @Transactional
    public PriceCalculatorResponse getPriceConfig(RequestPrice request) {

        PriceCalculation calculation = new PriceCalculation();

        BigDecimal distanceKm = BigDecimal.valueOf(request.distanceMeters()).divide(BigDecimal.valueOf(1000.0));

        User user = userService.findUserByUserName(request.username()).orElseThrow(() -> new UserNotFoundExcpetion(request.username()));
        ClientEntity client = clientService.searchClientByUserId(user.getId());

        PriceConfig config = priceRepository.findLatestById().orElseThrow(PriceCalculationNotFoundException::new);

        BigDecimal totalPrice = config.getBaseDeliveryPrice();

        BigDecimal[] kmResult = applyKmCost(totalPrice, distanceKm, config);
        totalPrice = kmResult[0];
        BigDecimal exceededKmCost = kmResult[1];

        // Boost multiplier (opcional)
        if (config.getBoostMultiplier() != null) {
            totalPrice = totalPrice.multiply(config.getBoostMultiplier());
            log.info("Após boost (x{}): {}", config.getBoostMultiplier(), totalPrice.setScale(2, RoundingMode.HALF_UP));
        }

        calculation.setRiderFee(totalPrice);
        log.info("To pay for rider: {}", totalPrice.setScale(2, RoundingMode.HALF_UP));

        // Service fee
        totalPrice = totalPrice.add(config.getServiceFee());
        log.info("Após service fee: {}", totalPrice.setScale(2, RoundingMode.HALF_UP));

        // Comissão (ex: 0.10 = 10%)
        BigDecimal commission = totalPrice.multiply(config.getCommissionRate());
        totalPrice = totalPrice.add(commission);
        log.info("Após comissão ({}%): {}", config.getCommissionRate().multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP), totalPrice.setScale(2, RoundingMode.HALF_UP));


        log.info("Preço total final: {}", totalPrice.setScale(2, RoundingMode.HALF_UP));


        calculation.setTotalPrice(totalPrice.setScale(2, RoundingMode.HALF_UP));
        calculation.setBaseDeliveryPrice(config.getBaseDeliveryPrice());
        calculation.setCommissionValue(commission.setScale(2, RoundingMode.HALF_UP));
        calculation.setServiceFeeValue(config.getServiceFee());
        calculation.setExceededKmCost(exceededKmCost.setScale(2, RoundingMode.HALF_UP));
        calculation.setDistanceKm(distanceKm.setScale(2, RoundingMode.HALF_UP));
        calculation.setClientEntity(client);
        priceCalculationRepository.save(calculation);

        return new PriceCalculatorResponse(calculation.getId(), totalPrice, distanceKm);
    }

    @Transactional(readOnly = true)
    public PriceCalculation getPriceCalculationById(Integer priceCalculationId) {
        Optional<PriceCalculation> priceCalculation = this.priceCalculationRepository
                .getPriceCalculationsById(priceCalculationId);
        if (priceCalculation.isPresent()) {
            return priceCalculation.get();
        }
        throw new PriceCalculationNotFoundException(priceCalculationId);
    }

    private BigDecimal[] applyKmCost(BigDecimal totalPrice, BigDecimal km, PriceConfig config) {
        if (km.compareTo(config.getMinKm()) > 0) {
            BigDecimal exceededKm = km.subtract(config.getMinKm());
            BigDecimal exceededKmCost = exceededKm.multiply(config.getPricePerKm());
            log.info("KM excedido: {} km acima do mínimo, custo adicional: {}", exceededKm.setScale(2, RoundingMode.HALF_UP), exceededKmCost.setScale(2, RoundingMode.HALF_UP));
            return new BigDecimal[]{totalPrice.add(exceededKmCost), exceededKmCost};
        }
        return new BigDecimal[]{totalPrice, BigDecimal.ZERO};
    }

}
