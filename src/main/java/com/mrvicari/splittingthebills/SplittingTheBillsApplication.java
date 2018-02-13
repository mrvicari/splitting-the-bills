package com.mrvicari.splittingthebills;

import com.mrvicari.splittingthebills.config.CustomUserDetails;
import com.mrvicari.splittingthebills.repository.TenantRepository;
import com.mrvicari.splittingthebills.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SplittingTheBillsApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SplittingTheBillsApplication.class, args);
	}

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, TenantRepository tenantRepository,
									  TenantService tenantService) throws Exception
	{
		builder.userDetailsService(userDetailsService(tenantRepository)).passwordEncoder(passwordEncoder);
	}

	private UserDetailsService userDetailsService(final TenantRepository tenantRepository)
	{
		return email -> new CustomUserDetails(tenantRepository.findByEmail(email));
	}
}
