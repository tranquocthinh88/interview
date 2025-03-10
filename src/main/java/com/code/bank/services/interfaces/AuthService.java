package com.code.bank.services.interfaces;

import com.code.bank.api.dtos.requests.LoginRequestDto;
import com.code.bank.api.dtos.requests.UserRegisterDto;
import com.code.bank.api.dtos.responses.LoginResponse;
import com.code.bank.api.exceptions.DataNotFoundException;

public interface AuthService {
    void register(UserRegisterDto userRegisterDto) throws Exception;
    LoginResponse login(LoginRequestDto loginRequestDto) throws DataNotFoundException;
}
