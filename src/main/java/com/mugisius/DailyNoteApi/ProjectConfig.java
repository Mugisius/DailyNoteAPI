package com.mugisius.DailyNoteApi;

import com.mugisius.DailyNoteApi.data.SecurityUser;
import com.mugisius.DailyNoteApi.data.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager UserDetailsManager(PasswordEncoder passwordEncoder) {

        User user = new User();
        user.setUsername("Admin");
        user.setPassword(passwordEncoder.encode("Admin"));
        user.setRole("Admin");

        return new InMemoryUserDetailsManager(new SecurityUser(user));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());

        http.csrf(
                c -> c.disable()
        );

        http.authorizeHttpRequests(
                c -> c.requestMatchers("/api").hasRole("User")
                        .anyRequest().hasRole("Admin")
        );

        return http.build();
    }
}

