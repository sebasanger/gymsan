package com.sanger.gymsan.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanger.gymsan.dto.auth.CreateResetPasswordTokenDto;
import com.sanger.gymsan.dto.auth.ResetUserPasswordDto;
import com.sanger.gymsan.services.ResetPasswordTokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/reset-password")
@RestController
@RequiredArgsConstructor
public class PasswordResetController {

    private final ResetPasswordTokenService resetPasswordTokenService;

    @PostMapping("")
    public ResponseEntity<Void> createResetTokenPassword(
            @RequestBody CreateResetPasswordTokenDto resetPasswordTokenDto) {
        resetPasswordTokenService.sendEmailResetToken(resetPasswordTokenDto.getEmail());
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/change-password")
    public ResponseEntity<Boolean> changePassword(@Valid @RequestBody ResetUserPasswordDto resetPasswordTokenDto) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(resetPasswordTokenService.validateVerificationToken(resetPasswordTokenDto));

    }
}
