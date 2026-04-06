package com.galaxybck.model.service;

import com.galaxybck.model.dto.ClientResponse;

public interface IClientService {
   ClientResponse retriveClientInfoByUserName(String userName);
}
