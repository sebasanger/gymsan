package com.sanger.gymsan.services;

import java.time.Instant;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.auth.AuthenticationResponse;
import com.sanger.gymsan.dto.auth.LoginRequestDto;
import com.sanger.gymsan.dto.auth.RefreshTokenRequestDto;
import com.sanger.gymsan.dto.auth.RefreshTokenResponse;
import com.sanger.gymsan.dto.user.ResendEmailVerificationDto;
import com.sanger.gymsan.dto.user.UserDtoConverter;
import com.sanger.gymsan.jwt.JwtProvider;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.repository.UserEntityRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

        private final RefreshTokenService refreshTokenService;
        private final VerificationTokenService verificationTokenService;
        private final UserEntityRepository userRepository;
        private final AuthenticationManager authenticationManager;
        private final UserDtoConverter userDtoConverter;
        private final JwtProvider jwtProvider;

        @Transactional(readOnly = true)
        public Usuario getCurrentUser() {
                org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                                .getContext().getAuthentication().getPrincipal();
                return userRepository.findByUsername(principal.getUsername()).orElseThrow(
                                () -> new UsernameNotFoundException("User not found - " + principal.getUsername()));
        }

        public RefreshTokenResponse refreshToken(RefreshTokenRequestDto refreshTokenRequest) {
                refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
                Usuario user = userRepository.findByEmail(refreshTokenRequest.getEmail())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                String token = jwtProvider.generateTokenWithEmail(user);
                return RefreshTokenResponse.builder().authenticationToken(token)
                                .refreshToken(refreshTokenRequest.getRefreshToken())
                                .user(userDtoConverter.convertUserEntityToGetUserDetailsDto(user))
                                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtDurationToken())).build();
        }

        public AuthenticationResponse login(LoginRequestDto loginRequest) {
                Usuario user = userRepository.findByEmail(loginRequest.getEmail())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                Authentication authenticate = authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                loginRequest.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                String token = jwtProvider.generateToken(authenticate);
                return AuthenticationResponse.builder().authenticationToken(token)
                                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtDurationToken()))
                                .user(userDtoConverter.convertUserEntityToGetUserDetailsDto(user)).build();
        }

        public boolean isLoggedIn() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
        }

        public void resendEmailVerification(ResendEmailVerificationDto resendEmailVerificationDto) {
                Usuario user = userRepository.findById(resendEmailVerificationDto.getId())
                                .orElseThrow(() -> new EntityNotFoundException("User not found"));
                if (!user.isEnabled()) {
                        verificationTokenService.sendEmailVerification(user);
                }

        }
}
