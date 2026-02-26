package com.sns.platform.api.module.user.domain.service;

import com.sns.platform.api.module.post.domain.entity.EmailAuth;
import com.sns.platform.api.module.user.infrastructure.EmailAuthRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    private final EmailAuthRepository emailAuthRepository;

    private String authNum;

    // ?몄쬆 肄붾뱶 ?앹꽦
    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int idx = random.nextInt(3);

            switch (idx) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }

    // 硫붿씪 ?댁슜 援ъ꽦
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        createCode();
        String setFrom = "testtest12@gmail.com";
        String toEmail = email;
        String title = "?뚯썝 媛?낆쓣 ?꾪븳 ?몄쬆 肄붾뱶?낅땲??";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);

        String msgOfEmail = "";
        msgOfEmail += "<div style='margin:20px;'>";
        msgOfEmail += "<h1> ?뚯썝 媛?낆쓣 ?꾪븳 肄붾뱶?낅땲?? </h1>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>?꾨옒 肄붾뱶瑜??낅젰?댁＜?몄슂.<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>媛먯궗?⑸땲??<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgOfEmail += "<h3 style='color:blue;'>?뚯썝媛???몄쬆 肄붾뱶</h3>";
        msgOfEmail += "<div style='font-size:130%'>";
        msgOfEmail += "CODE : <strong>";
        msgOfEmail += authNum + "</strong><div><br/> ";
        msgOfEmail += "</div>";

        message.setFrom(setFrom);
        message.setText(msgOfEmail, "utf-8", "html");

        return message;
    }

    // 硫붿씪 諛쒖넚 + DB ???
    public void sendEmail(String email) throws MessagingException, UnsupportedEncodingException {
        MimeMessage emailForm = createEmailForm(email);
        emailSender.send(emailForm);

        // ?몄쬆 肄붾뱶 ???(5遺??좏슚)
        EmailAuth emailAuth = EmailAuth.builder()
                .email(email)
                .code(authNum)
                .verified(false)
                .expiresAt(LocalDate.from(LocalDateTime.now().plusMinutes(5)))
                .build();

        emailAuthRepository.save(emailAuth);
    }

    // ?몄쬆 肄붾뱶 寃利?
    public void verifyCode(String email, String code) {
        EmailAuth emailAuth = emailAuthRepository.findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new IllegalArgumentException("?몄쬆 肄붾뱶媛 ?놁뒿?덈떎."));

        if (emailAuth.isVerified()) {
            throw new IllegalStateException("?대? ?몄쬆???대찓?쇱엯?덈떎.");
        }

        if (emailAuth.getExpiresAt().isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
            throw new IllegalStateException("?몄쬆 肄붾뱶媛 留뚮즺?섏뿀?듬땲??");
        }

        if (!emailAuth.getCode().equals(code)) {
            throw new IllegalArgumentException("?몄쬆 肄붾뱶媛 ?쇱튂?섏? ?딆뒿?덈떎.");
        }

        // ?몄쬆 ?꾨즺 泥섎━
        emailAuth.verity();
        emailAuthRepository.save(emailAuth);
    }

    // 媛?????몄쬆 ?щ? ?뺤씤
    public void validateVerifiedEmail(String email) {
        EmailAuth emailAuth = emailAuthRepository.findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new IllegalArgumentException("?대찓???몄쬆???꾩슂?⑸땲??"));

        if (!emailAuth.isVerified()) {
            throw new IllegalStateException("?대찓???몄쬆???꾨즺?섏? ?딆븯?듬땲??");
        }
    }
}

