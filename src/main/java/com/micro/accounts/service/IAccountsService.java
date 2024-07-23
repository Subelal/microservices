package com.micro.accounts.service;

import com.micro.accounts.dto.CustomerDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface IAccountsService {

    /**
     * @param customerDto
     */
    void createAccount(CustomerDto customerDto);

    /**
     * @param mobileNumber
     * @return
     */
    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccounts( CustomerDto customerDto);

    @Transactional
    @Modifying
    boolean deleteAccounts(String mobileNumber);
}
