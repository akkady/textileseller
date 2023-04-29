package ma.akkady.textileseller.security.services;

import lombok.RequiredArgsConstructor;
import ma.akkady.textileseller.entities.Vendor;
import ma.akkady.textileseller.services.AccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Vendor appUser = accountService.loadUserByUsername(username);
        return new User(appUser.getUsername(), appUser.getPassword(), getAuthorities(appUser));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Vendor user) {
        return user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toList());
    }
}
