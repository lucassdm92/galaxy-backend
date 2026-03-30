package com.galaxybck.model.service;

import com.galaxybck.model.dto.ClientRequest;
import com.galaxybck.model.dto.ClientResponse;
import com.galaxybck.model.entity.Client;
import com.galaxybck.model.entity.User;
import com.galaxybck.model.repository.ClientRepository;
import com.galaxybck.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public ClientService(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public ClientResponse getById(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found: " + id));
        return ClientResponse.from(client);
    }

    @Transactional
    public ClientResponse register(ClientRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getUserId()));

        Client client = new Client();


        client.setName(request.getName());
        client.setPhone(request.getPhone());
        client.setAddress(request.getAddress());
        client.setVat(request.getVat());
        client.setCreatedBy(request.getCreatedBy());
        client.setUser(user);

        Client saved = clientRepository.save(client);

        log.info("Client registered with id: {}", saved.getId());
        return ClientResponse.from(saved);
    }

    public ClientResponse searchClientById(final Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found: " + 1));
        return ClientResponse.from(client);
    }
}
