package com.sanger.gymsan.services;

import java.util.Calendar;
import java.util.Date;

import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanger.gymsan.dto.auth.ResetUserPasswordDto;
import com.sanger.gymsan.exceptions.PasswordNotMismatch;
import com.sanger.gymsan.exceptions.TokenExpiredException;
import com.sanger.gymsan.exceptions.TokenInvalidException;
import com.sanger.gymsan.exceptions.UserNotFoundException;
import com.sanger.gymsan.models.PasswordResetToken;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.repository.PasswordResetTokenRepository;
import com.sanger.gymsan.repository.UserEntityRepository;
import com.sanger.gymsan.utils.mail.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResetPasswordTokenService extends BaseService<PasswordResetToken, Long, PasswordResetTokenRepository> {

    private final EmailService emailService;

    private final UserEntityRepository userEntityRepository;

    private final PasswordEncoder passwordEncoder;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Value("${base.url.frontend}")
    private String urlFrontend;

    @Value("${redirect.path.recover.password}")
    private String recoverPasswordPath;

    /**
     * sirve para generar el mail que se le envia al usuario resetear la
     * contraseña
     *
     * @param email
     * @param urlRedirect
     * @return
     */
    public void sendEmailResetToken(String email) {
        Usuario user = userEntityRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        String token = createVerificationTokenForUser(user);
        System.out.println(urlFrontend + recoverPasswordPath + token);
        // TODO: Activar esto nuevamente
        // emailService.sendMail(user.getEmail(), user.getFullName(), "Bienvenido " +
        // user.getFullName()
        // + " , sigue este link para resetear to contraseña " + urlFrontend +
        // recoverPasswordPath + token);
    }

    public String createVerificationTokenForUser(Usuario user) {
        String token = RandomString.make(45);
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();

        passwordResetToken.setExpiryDate(dt);
        this.save(passwordResetToken);

        return token;
    }

    public boolean validateVerificationToken(ResetUserPasswordDto resetPasswordTokenDto) {
        final PasswordResetToken verificationToken = this.repository.findByToken(resetPasswordTokenDto.getToken());
        if (verificationToken == null) {
            throw new TokenInvalidException();
        }
        final Usuario user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            this.repository.delete(verificationToken);
            throw new TokenExpiredException();
        } else if (resetPasswordTokenDto.getPassword().equals(resetPasswordTokenDto.getPassword2())) {
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(resetPasswordTokenDto.getPassword()));
            userEntityRepository.save(user);
            this.repository.delete(verificationToken);
            return true;
        } else {
            throw new PasswordNotMismatch();
        }

    }

}
