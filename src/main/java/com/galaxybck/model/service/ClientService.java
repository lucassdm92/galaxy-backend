package com.galaxybck.model.service;

import com.galaxybck.exception.ClientNotFoundException;
import com.galaxybck.exception.UserNotFoundExcpetion;
import com.galaxybck.model.dto.ClientRequest;
import com.galaxybck.model.dto.ClientResponse;
import com.galaxybck.model.dto.UserResponse;
import com.galaxybck.model.entity.ClientEntity;
import com.galaxybck.model.entity.User;
import com.galaxybck.model.repository.ClientRepository;
import com.galaxybck.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService implements IClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;
    private final UserService userService;
    private final UserRepository userRepository; //TODO, needs to check all repository to change to services. Its not good pratic call repository fro other services


    public ClientService(ClientRepository clientRepository, UserRepository userRepository, UserService userService) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.userService = userService;

    }

    @Transactional(readOnly = true)
    public ClientResponse getById(Integer id) {
        ClientEntity clientEntity = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        return ClientResponse.from(clientEntity);
    }

    @Transactional
    public ClientResponse register(ClientRequest request) {

        log.info("Register request: {}", request);
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new UserNotFoundExcpetion(request.userId()));

        log.info("Registered user: {}", user);
        ClientEntity clientEntity = ClientEntity.builder().name(request.name()).phone(request.phone())
                .address(request.address())
                .vat(request.vat())
                .createdBy(request.createdBy())
                .user(user).build();

        log.info("Client to insert: {}", clientEntity);

        ClientEntity saved = clientRepository.save(clientEntity);

        log.info("Client registered with id: {}", saved.getId());
        return ClientResponse.from(saved);
    }

    public ClientResponse searchClientById(final Integer id) {
        ClientEntity clientEntity = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        return ClientResponse.from(clientEntity);
    }

    public ClientEntity searchClientByUserId(final Integer id) {
        return clientRepository.findClientByUserId(id).orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    public ClientResponse retriveClientInfoByUserName(String userName) {
        log.info("[findClientByUserName] UserName: {}", userName);

        User user = userService.findUserByUserName(userName).orElseThrow(() -> new UserNotFoundExcpetion(userName));
        log.info("[findClientByUserName] User Entity: {}", user);

        ClientEntity clientEntity = clientRepository.findClientByUserId(user.getId()).orElseThrow(() -> new ClientNotFoundException(user.getId()));
        log.info("[findClientByUserName] ClientEntity: {}", clientEntity);

        return ClientResponse.from(clientEntity);
    }
}
