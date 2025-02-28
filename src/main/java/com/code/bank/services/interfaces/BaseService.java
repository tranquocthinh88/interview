package com.code.bank.services.interfaces;

import com.code.bank.api.exceptions.DataNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseService <T,ID>{
    T save(T t);
    Optional<T> findById(ID id);
    List<T> findAll();
    void delete(ID id) throws DataNotFoundException;
    T update(ID id,T t) throws DataNotFoundException;
    T updatePatch(ID id, Map<String, ?> data) throws DataNotFoundException;
}
