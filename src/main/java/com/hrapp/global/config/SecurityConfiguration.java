
package com.hrapp.global.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.hrapp.global.security.AuthoritiesConstants;
import com.hrapp.global.security.jwt.JWTConfigurer;
import com.hrapp.global.security.jwt.JWTFilter;
import com.hrapp.global.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.CompositeAccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;



import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final TokenProvider tokenProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(withDefaults()).csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(new BasicAuthenticationEntryPoint())

						.accessDeniedHandler(new CompositeAccessDeniedHandler()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/v1/authenticate", "/api/v1/register", "/api/v1/activate",
								"/api/v1/account/reset-password/init", "/api/v1/account/reset-password/finish")
						.permitAll().requestMatchers("/api/v1/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
						.requestMatchers("/api/v1/**").authenticated()
						.requestMatchers("/management/health", "/management/health/**", "/management/info",
								"/management/prometheus")
						.permitAll().requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll().anyRequest().authenticated())
				.addFilterBefore(new JWTFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
				.httpBasic(withDefaults());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JWTConfigurer securityConfigurerAdapter() {
		return new JWTConfigurer(tokenProvider);
	}
}
