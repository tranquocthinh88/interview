package com.code.bank.api.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDto {
    @NotNull(message = "street must be not null")
    private String street;
    @NotNull(message = "ward must be not null")
    private String ward;
    @NotNull(message = "district must be not null")
    private String district;
    @NotNull(message = "city must be not null")
    private String city;
}
