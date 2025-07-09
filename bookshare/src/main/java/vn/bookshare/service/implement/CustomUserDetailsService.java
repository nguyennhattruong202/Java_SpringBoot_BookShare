package vn.bookshare.service.implement;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.bookshare.repository.UserAccountRepository;

@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository accountUserRepository;

    public CustomUserDetailsService(UserAccountRepository accountUserRepository) {
        this.accountUserRepository = accountUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountUserRepository.findByUsername(username)
                .map(user -> User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
