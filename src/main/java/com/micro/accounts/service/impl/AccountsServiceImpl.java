package com.micro.accounts.service.impl;

import com.micro.accounts.constants.AccountsConstants;
import com.micro.accounts.dto.AccountsDto;
import com.micro.accounts.dto.CustomerDto;
import com.micro.accounts.entity.Accounts;
import com.micro.accounts.entity.Customer;
import com.micro.accounts.exception.ResourceNotFoundException;
import com.micro.accounts.mapper.AccountsMapper;
import com.micro.accounts.mapper.CustomerMapper;
import com.micro.accounts.repository.AccountsRepository;
import com.micro.accounts.repository.CustomerRepository;
import com.micro.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    /**
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalMobileNumberCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
         if(optionalMobileNumberCustomer.isPresent()){
             throw new ArrayIndexOutOfBoundsException("Customer already exits with given mobile number "+
                     customerDto.getMobileNumber());
         }
        /* customer.setCreatedBy("Subelal");
         customer.setCreatedAt(LocalDateTime.now());
         customer.setUpdatedBy("Ranjan");
         customer.setUpdatedAt(LocalDateTime.now());
*/         Customer saveCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(saveCustomer));
    }
    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "Customer Id ", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
     return customerDto;
    }

    /**
     * @param customerDto
     * @return
     */
    @Override
    public boolean updateAccounts( CustomerDto customerDto ) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null){
            //   accountsRepository.findById(customerDto.getAccountsDto().getAccountNumber())
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Accounts", "Account Number", accountsDto.getAccountNumber().toString())
            );

            AccountsDto accountsDto1 = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
            accounts =  accountsRepository.save(accounts);
            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customer Id", customerId.toString())
            );

            Customer customerfromCustDto = CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customerfromCustDto);
            isUpdated = true;

        }
        return isUpdated;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteAccounts( String mobileNumber ) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer ", "Not Found with given mobile number ", mobileNumber.toString())
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 10000000000L+new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        /*newAccount.setCreatedBy("Subelal");
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setUpdatedBy("Ranjan");
        newAccount.setUpdatedAt(LocalDateTime.now());*/
        return newAccount;
    }
}
