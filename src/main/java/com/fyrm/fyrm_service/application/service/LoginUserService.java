package com.fyrm.fyrm_service.application.service;

import com.fyrm.fyrm_service.application.port.in.command.LoginUserCommand;
import com.fyrm.fyrm_service.application.port.in.usecasse.LoginUserUseCase;
import com.fyrm.fyrm_service.domain.exception.LoginFailedException;
import com.fyrm.fyrm_service.generatedapi.dtos.JwtLoginResponseDto;
import com.fyrm.fyrm_service.infrastructure.hexagonal_support.UseCase;
import com.fyrm.fyrm_service.infrastructure.spring.security.jwt.JwtUtils;
import com.fyrm.fyrm_service.infrastructure.spring.security.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@UseCase
@RequiredArgsConstructor
public class LoginUserService implements LoginUserUseCase {

  private static final String TOKEN_TYPE = "Bearer";
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  @Override
  public JwtLoginResponseDto login(LoginUserCommand loginUserCommand) {
    UsernamePasswordAuthenticationToken loginInformation = new UsernamePasswordAuthenticationToken(
        loginUserCommand.getUsername(),
        loginUserCommand.getPassword()
    );

    Authentication authentication = authenticationManager.authenticate(loginInformation);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwtToken = jwtUtils.generateJwtToken(authentication);

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

    if (!userDetails.getEnabled()) {
      throw new LoginFailedException("Account is not enabled!");
    }

    String role = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .findFirst()
        .orElseThrow(() -> new LoginFailedException("Something went wrong while getting the user role!"));

    return new JwtLoginResponseDto()
        .userId(userDetails.getId())
        .token(jwtToken)
        .email(userDetails.getEmail())
        .username(userDetails.getUsername())
        .role(role)
        .tokenType(TOKEN_TYPE);
  }
}
