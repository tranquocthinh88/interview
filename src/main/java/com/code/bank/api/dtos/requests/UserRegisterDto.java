package com.code.bank.api.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegisterDto {
    @NotBlank(message = "user name must be not blank")
    private String username;
    @NotBlank(message = "Password must be not blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    private Integer customerId;
}
