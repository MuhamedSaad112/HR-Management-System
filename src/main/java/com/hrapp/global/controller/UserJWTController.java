package com.hrapp.global.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hrapp.global.controller.VM.LoginVM;
import com.hrapp.global.security.jwt.JWTFilter;
import com.hrapp.global.security.jwt.TokenProvider;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
public class UserJWTController {

	private final TokenProvider tokenProvider;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	@PostMapping("/authenticate")
	public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginVM.getUsername(), loginVM.getPassword());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
	}

	static class JWTToken {

		private String idToken;

		JWTToken(String idToken) {
			this.idToken = idToken;
		}

		@JsonProperty("id_token")
		String getIdToken() {
			return idToken;
		}

		void setIdToken(String idToken) {
			this.idToken = idToken;
		}
	}

}
