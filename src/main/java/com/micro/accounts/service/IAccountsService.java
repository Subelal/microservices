package com.micro.accounts.service;

import com.micro.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * @param customerDto
     */
    void createAccount(CustomerDto customerDto);
}
