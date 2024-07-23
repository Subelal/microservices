package com.micro.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message = "Mobile Number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be a 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "Account Type can not be null or empty")
    private String accountType;

    @NotEmpty(message = "Branch Address can not be null or empty")
    private String branchAddress;
}
