package com.code.bank.services.impls;

import com.code.bank.models.CustomUserDetails;
import com.code.bank.models.UserAccount;
import com.code.bank.repositories.UserAccountRepository;
import com.code.bank.services.interfaces.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        UserAccount user = userAccountRepository.findByUsername(phone)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}
