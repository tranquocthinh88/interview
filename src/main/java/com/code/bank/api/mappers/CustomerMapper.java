package com.code.bank.api.mappers;

import com.code.bank.api.dtos.requests.CustomerDto;
import com.code.bank.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

//@Component
//public class CustomerMapper {
//    public Customer customerDto2Customer(CustomerDto customerDto) {
//        return Customer.builder()
//                .fullName(customerDto.getFullName())
//                .phone(customerDto.getPhone())
//                .email(customerDto.getEmail())
//                .gender(customerDto.getGender())
//                .address(customerDto.getAddress())
//                .dateOfBirth(customerDto)
//                .build();
//    }
//}
//@Mapper(componentModel = "spring")
//public interface CustomerMapper {
//    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
//
//    @Mapping(source = "gender", target = "gender") // String sẽ tự động chuyển thành Enum
//    Customer CustomerDto2Customer(CustomerDto customerDto);
//}
@Mapper(componentModel = "spring")
//@Component
public interface CustomerMapper {
    @Mapping(source = "gender", target = "gender")
    Customer CustomerDto2Customer(CustomerDto customerDto);
}
