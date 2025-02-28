package com.code.bank.api.dtos.requests;

import com.code.bank.models.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerDto {
    @NotBlank(message = "full name must be not blank")
    private String fullName;
    @NotBlank(message = "phone must be not blank")
    private String phone;
    @NotBlank(message = "email must be not blank")
    private String email;
    @NotBlank(message = "gender must be not blank")
    private String gender;
    @NotBlank(message = "address must be not blank")
    private String address;
    @NotBlank(message = "id card must be not blank")
    private String idCard;
    @NotBlank(message = "date of birth must be not blank")
    private LocalDateTime dateOfBirth;
}
