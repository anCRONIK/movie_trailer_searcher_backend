package site.ancronik.movie.trailer.searcher.security.config;

import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailsService implements UserDetailsService { //TODO change to JDBC or some other implementation

    private static final String KEY_USER_PASSWORD = "user.password";
    private static final String KEY_ADMIN_PASSWORD = "admin.password";
    private static final String DEFAULT_PASSWORD = "password";

    private final PasswordEncoder passwordEncoder;
    private final Environment environment;

    public CustomUserDetailsService(PasswordEncoder passwordEncoder, Environment environment) {
        this.passwordEncoder = passwordEncoder;
        this.environment = environment;
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
            .add(User.withUsername("user").password(passwordEncoder.encode(environment.getProperty(KEY_USER_PASSWORD, DEFAULT_PASSWORD))).roles("USER").build());
        userDetailsList
            .add(User.withUsername("admin").password(passwordEncoder.encode(environment.getProperty(KEY_ADMIN_PASSWORD, DEFAULT_PASSWORD))).roles("USER", "ADMIN").build());

        return userDetailsList;
    }

}