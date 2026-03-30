package com.galaxybck.model.service;

import com.galaxybck.model.dto.PriceCalculatorResponse;
import com.galaxybck.model.dto.PriceConfigResponse;
import com.galaxybck.model.dto.RequestPrice;
import com.galaxybck.model.entity.PriceCalculation;
import com.galaxybck.model.repository.PriceCalculationRepository;
import com.galaxybck.model.repository.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PriceService {

    private static final Logger log = LoggerFactory.getLogger(PriceService.class);

    private final PriceRepository priceRepository;
    private final GoogleMapsService googleMapsService;
    private final PriceCalculationRepository priceCalculationRepository;

    public PriceService(PriceRepository priceRepository, GoogleMapsService googleMapsService, PriceCalculationRepository priceCalculationRepository) {
        this.priceRepository = priceRepository;
        this.googleMapsService = googleMapsService;
        this.priceCalculationRepository = priceCalculationRepository;
    }

    @Transactional
    public PriceCalculatorResponse getPriceConfig(RequestPrice request) {
        double km = this.googleMapsService.getDistanceInKm(request.getOrigin(), request.getDestination(), "ie");

        PriceConfigResponse config = priceRepository.findLatestById()
                .map(PriceConfigResponse::from)
                .orElse(null);

        if (config == null) {
            return null;
        }

        BigDecimal totalPrice = config.getBaseDeliveryPrice();
        BigDecimal kmDecimal = BigDecimal.valueOf(km);

        BigDecimal[] kmResult = applyKmCost(totalPrice, kmDecimal, config);
        totalPrice = kmResult[0];
        BigDecimal exceededKmCost = kmResult[1];

        // Service fee
        totalPrice = totalPrice.add(config.getServiceFee());
        log.info("Após service fee: {}", totalPrice.setScale(2, RoundingMode.HALF_UP));

        // Comissão (ex: 0.10 = 10%)
        BigDecimal commission = totalPrice.multiply(config.getCommissionRate());
        totalPrice = totalPrice.add(commission);
        log.info("Após comissão ({}%): {}", config.getCommissionRate().multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP), totalPrice.setScale(2, RoundingMode.HALF_UP));

        // Boost multiplier (opcional)
        if (config.getBoostMultiplier() != null) {
            totalPrice = totalPrice.multiply(config.getBoostMultiplier());
            log.info("Após boost (x{}): {}", config.getBoostMultiplier(), totalPrice.setScale(2, RoundingMode.HALF_UP));
        }

        log.info("Preço total final: {}", totalPrice.setScale(2, RoundingMode.HALF_UP));

        PriceCalculation calculation = new PriceCalculation();
        calculation.setTotalPrice(totalPrice.setScale(2, RoundingMode.HALF_UP));
        calculation.setBaseDeliveryPrice(config.getBaseDeliveryPrice());
        calculation.setCommissionValue(commission.setScale(2, RoundingMode.HALF_UP));
        calculation.setServiceFeeValue(config.getServiceFee());
        calculation.setExceededKmCost(exceededKmCost.setScale(2, RoundingMode.HALF_UP));
        calculation.setDistanceKm(kmDecimal.setScale(2, RoundingMode.HALF_UP));
        priceCalculationRepository.save(calculation);

        return new PriceCalculatorResponse(calculation.getId(), totalPrice, kmDecimal);
    }

    private BigDecimal[] applyKmCost(BigDecimal totalPrice, BigDecimal km, PriceConfigResponse config) {
        if (km.compareTo(config.getMinKm()) > 0) {
            BigDecimal exceededKm = km.subtract(config.getMinKm());
            BigDecimal exceededKmCost = exceededKm.multiply(config.getPricePerKm());
            log.info("KM excedido: {} km acima do mínimo, custo adicional: {}", exceededKm.setScale(2, RoundingMode.HALF_UP), exceededKmCost.setScale(2, RoundingMode.HALF_UP));
            return new BigDecimal[]{totalPrice.add(exceededKmCost), exceededKmCost};
        }
        return new BigDecimal[]{totalPrice, BigDecimal.ZERO};
    }

}
