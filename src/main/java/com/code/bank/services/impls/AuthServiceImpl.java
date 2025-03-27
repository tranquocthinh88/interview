package com.code.bank.services.impls;

import com.code.bank.api.dtos.requests.LoginRequestDto;
import com.code.bank.api.dtos.requests.UserRegisterDto;
import com.code.bank.api.dtos.responses.LoginResponse;
import com.code.bank.api.exceptions.DataExistsException;
import com.code.bank.api.exceptions.DataNotFoundException;
import com.code.bank.models.CustomUserDetails;
import com.code.bank.models.Customer;
import com.code.bank.models.Token;
import com.code.bank.models.UserAccount;
import com.code.bank.models.enums.Role;
import com.code.bank.repositories.CustomerRepository;
import com.code.bank.repositories.TokenRepository;
import com.code.bank.repositories.UserAccountRepository;
import com.code.bank.services.interfaces.AuthService;
import com.code.bank.services.interfaces.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void register(UserRegisterDto userRegisterDto) throws Exception {
        UserAccount userAccount = mapperToUser(userRegisterDto);
        userAccountRepository.save(userAccount);

    }

    @Override
    public LoginResponse login(LoginRequestDto loginRequestDto) throws DataNotFoundException {
        String phone = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        UserAccount userAccount = userAccountRepository.findByUsername(phone). orElseThrow(() ->
                new DataNotFoundException("user is not exists")
        );
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
        if (!passwordEncoder.matches(password, userAccount.getPassword())) {
            throw new DataNotFoundException("password is incorrect");
        }
        CustomUserDetails userDetail = new CustomUserDetails(userAccount);
        Token token = new Token();
        token.setAccessToken(jwtService.generateToken(userDetail));
        token.setRefreshToken(jwtService.generateRefreshToken(new HashMap<>(), userDetail));
        token.setUserAccount(userAccount);
        token.setIssueDate(LocalDateTime.now());
        tokenRepository.save(token);
        return LoginResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }


    private UserAccount mapperToUser(UserRegisterDto userRegisterDto) throws DataExistsException, DataNotFoundException {

        Optional<UserAccount> userAccount = userAccountRepository.findByUsername(userRegisterDto.getUsername());
        UserAccount userExist = new UserAccount();
        if (userAccount.isPresent()) {
            if (userAccount.get().isVerify()) {
                throw new DataExistsException("Email already used");
            }
            userExist = userAccount.get();
        }
        Customer customer = customerRepository.findByPhone(userRegisterDto.getUsername())
                .orElseThrow(() -> new DataNotFoundException("Customer not found"));
        UserAccount userResult =  UserAccount.builder()
                .username(userRegisterDto.getUsername())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .username(userRegisterDto.getUsername())
                .verify(true)
                .role(Role.CUSTOMER)
                .customer(customer)
                .build();
        userResult.setId(userExist.getId());
        return userResult;
    }
}
