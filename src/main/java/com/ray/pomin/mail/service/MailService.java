package com.ray.pomin.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    private final StringRedisTemplate mailRedisTemplate;

    @Value("${spring.mail.username}")
    private String from;

    public void sendCode(String email) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        try {
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("인증 코드 입니다");
            helper.setText("인증 코드 : " + createCode(email));
        } catch (MessagingException e) {
            throw new IllegalArgumentException("");
        }

        mailSender.send(message);
    }

    private String createCode(String email) {
        Random random = new Random();
        String code = String.format("%04d", random.nextInt(10000));

        ValueOperations<String, String> valueOperations = mailRedisTemplate.opsForValue();
        valueOperations.set(email, code);
        mailRedisTemplate.expire(email, Duration.ofMinutes(5L));

        return code;
    }

    public boolean checkCode(String email, String receiveCode) {
        return checkExpireTime(email) && checkIsCodeEqual(email, receiveCode);
    }

    private boolean checkExpireTime(String email) {
        Long expireTime = mailRedisTemplate.getExpire(email);

        return expireTime != null && expireTime > 0;
    }

    private boolean checkIsCodeEqual(String email, String receiveCode) {
        return receiveCode.equals(mailRedisTemplate.opsForValue().get(email));
    }

    public void saveResult(String email, String result) {
        ValueOperations<String, String> valueOperations = mailRedisTemplate.opsForValue();
        valueOperations.set(email, result);
    }

    public void checkAuthentication(String email) {
        ValueOperations<String, String> valueOperations = mailRedisTemplate.opsForValue();
        String result = valueOperations.get(email);

        if (result == null || !result.equals(String.valueOf(true))) {
            throw new IllegalArgumentException("");
        }

        valueOperations.getOperations().delete(email);
    }

}
