package com.code.bank.repositories;

import com.code.bank.models.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepository extends BaseRepository<Customer, Integer> {
    @Query("select COUNT(cs) from Account ac \n" +
            "inner join Customer cs on cs.id = ac.customer.id\n" +
            "inner join Address ad on ad.id = ac.address.id \n" +
            "where ad.city like %?1% or ad.district like %?1% or ad.ward like %?1% or ad.street like %?1% or ad.addressDetail like %?1%")
    long countCustomersByLocation(String location);
    @Query("select cs from Account ac \n" +
            "inner join Customer cs on cs.id = ac.customer.id\n" +
            "inner join Address ad on ad.id = ac.address.id \n" +
            "where ad.city like %?1% or ad.district like %?1% or ad.ward like %?1% or ad.street like %?1% or ad.addressDetail like %?1%")
    List<Customer> findCustomersByLocation(String location);
}