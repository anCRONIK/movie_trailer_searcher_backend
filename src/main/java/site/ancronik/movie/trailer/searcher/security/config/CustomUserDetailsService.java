package site.ancronik.movie.trailer.searcher.security.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailsService implements UserDetailsService { //TODO change to JDBC or some other implementation

    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails> userDetailsList = populateUserDetails();

        for (UserDetails u : userDetailsList) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public List<UserDetails> populateUserDetails() {
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList
            .add(User.withUsername("user").password(passwordEncoder.encode("password")).roles("USER").build());
        userDetailsList
            .add(User.withUsername("admin").password(passwordEncoder.encode("password")).roles("USER", "ADMIN").build());

        return userDetailsList;
    }

}