package com.robo.RideWithUs.Configirations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
@Configuration
public class UserConfig {

	
	    @Bean
	    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

	        var user = User.withUsername("ajay")
	                .password(encoder.encode("1234"))
	                .roles("USER")
	                .build();

	        return new InMemoryUserDetailsManager(user);
	    
	}

}
