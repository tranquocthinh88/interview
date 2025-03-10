package com.code.bank.api.controllers;


import com.code.bank.api.dtos.requests.LoginRequestDto;
import com.code.bank.api.dtos.requests.UserRegisterDto;
import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.api.exceptions.DataNotFoundException;
import com.code.bank.models.Customer;
import com.code.bank.models.UserAccount;
import com.code.bank.repositories.CustomerRepository;
import com.code.bank.repositories.UserAccountRepository;
import com.code.bank.services.interfaces.AuthService;
import com.code.bank.services.interfaces.FirebaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final FirebaseService firebaseService;
    private final CustomerRepository customerRepository;
    private final UserAccountRepository userAccountRepository;

    @PostMapping("/register")
    public Response register(@RequestBody @Valid UserRegisterDto userRegisterDto) throws Exception {
        authService.register(userRegisterDto);
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "User registered successfully",
                "Check OTP in your email"
        );
    }

    @PostMapping("/login")
    public Response login(@RequestBody @Valid LoginRequestDto loginRequestDto) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Login successfully",
                authService.login(loginRequestDto)
        );
    }
}
