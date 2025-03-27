package com.code.bank.api.dtos.requests;

import com.code.bank.models.enums.Gender;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    @NotBlank(message = "temporary address must be not blank")
    private String temporaryAddress;
    @NotBlank(message = "permanent address must be not blank")
    private String permanentAddress;
    @NotBlank(message = "id card must be not blank")
    private String idCard;
    @NotNull(message = "date of birth must be not null")
    private LocalDateTime dateOfBirth;
    @Past(message = "date of issue must be past")
    private LocalDate dateOfIssue;
}
