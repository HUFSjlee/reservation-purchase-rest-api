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

    // 인증 코드 생성
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

    // 메일 내용 구성
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        createCode();
        String setFrom = "testtest12@gmail.com";
        String toEmail = email;
        String title = "회원가입을 위한 인증 코드입니다.";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);

        String msgOfEmail = "";
        msgOfEmail += "<div style='margin:20px;'>";
        msgOfEmail += "<h1> 회원가입을 위한 인증 코드입니다. </h1>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>아래 코드를 입력해주세요.<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<p>감사합니다.<p>";
        msgOfEmail += "<br>";
        msgOfEmail += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgOfEmail += "<h3 style='color:blue;'>회원가입 인증 코드</h3>";
        msgOfEmail += "<div style='font-size:130%'>";
        msgOfEmail += "CODE : <strong>";
        msgOfEmail += authNum + "</strong><div><br/> ";
        msgOfEmail += "</div>";

        message.setFrom(setFrom);
        message.setText(msgOfEmail, "utf-8", "html");

        return message;
    }

    // 메일 발송 + DB 저장
    public void sendEmail(String email) throws MessagingException, UnsupportedEncodingException {
        MimeMessage emailForm = createEmailForm(email);
        emailSender.send(emailForm);

        // 인증 코드 저장 (5분 유효)
        EmailAuth emailAuth = EmailAuth.builder()
                .email(email)
                .code(authNum)
                .verified(false)
                .expiresAt(LocalDate.from(LocalDateTime.now().plusMinutes(5)))
                .build();

        emailAuthRepository.save(emailAuth);
    }

    // 인증 코드 검증
    public void verifyCode(String email, String code) {
        EmailAuth emailAuth = emailAuthRepository.findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new IllegalArgumentException("인증 코드가 없습니다."));

        if (emailAuth.isVerified()) {
            throw new IllegalStateException("이미 인증된 이메일입니다.");
        }

        if (emailAuth.getExpiresAt().isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
            throw new IllegalStateException("인증 코드가 만료되었습니다.");
        }

        if (!emailAuth.getCode().equals(code)) {
            throw new IllegalArgumentException("인증 코드가 일치하지 않습니다.");
        }

        // 인증 완료 처리
        emailAuth.verity();
        emailAuthRepository.save(emailAuth);
    }

    // 가입 전 이메일 인증 여부 확인
    public void validateVerifiedEmail(String email) {
        EmailAuth emailAuth = emailAuthRepository.findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 인증이 필요합니다."));

        if (!emailAuth.isVerified()) {
            throw new IllegalStateException("이메일 인증이 완료되지 않았습니다.");
        }
    }
}

