package com.sanger.gymsan.services;

import java.util.Calendar;
import java.util.Date;

import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanger.gymsan.dto.auth.ValidateUserDto;
import com.sanger.gymsan.exceptions.PasswordNotMismatch;
import com.sanger.gymsan.exceptions.TokenExpiredException;
import com.sanger.gymsan.exceptions.TokenInvalidException;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.models.VerificationToken;
import com.sanger.gymsan.repository.UserEntityRepository;
import com.sanger.gymsan.repository.VerificationTokenRepository;
import com.sanger.gymsan.utils.mail.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationTokenService extends BaseService<VerificationToken, Long, VerificationTokenRepository> {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    private final EmailService emailService;

    private final UserEntityRepository userEntityRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${base.url.frontend}")
    private String urlFrontend;

    @Value("${redirect.path.active.user}")
    private String activateUserPath;

    /**
     * sirve para generar el mail que se le envia al usuario para activar su
     * cuenta y ponerle una contraseña
     *
     * @param newUser
     * @param urlRedirect
     * @return
     */
    public void sendEmailVerification(Usuario user) {
        String token = createVerificationTokenForUser(user);

        // TODO: Reactivar validacion por mail
        // emailService.sendMail(user.getEmail(), user.getFullName(),
        // "Bienvenido " + user.getFullName() + " sigue el siguiente link para activar
        // tu cuenta " + urlFrontend
        // + activateUserPath + token);
    }

    public String createVerificationTokenForUser(Usuario user) {
        String token = RandomString.make(45);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();

        verificationToken.setExpiryDate(dt);
        this.save(verificationToken);

        return token;
    }

    public boolean validateVerificationToken(ValidateUserDto validation) {
        final VerificationToken verificationToken = this.repository.findByToken(validation.getToken());
        if (verificationToken == null) {
            throw new TokenInvalidException();
        }
        final Usuario user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            this.repository.delete(verificationToken);
            throw new TokenExpiredException();

        } else if (validation.getPassword().equals(validation.getPassword2())) {
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(validation.getPassword()));
            userEntityRepository.save(user);
            this.repository.delete(verificationToken);
            return true;
        } else {
            throw new PasswordNotMismatch();
        }

    }
}
