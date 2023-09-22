package com.ray.pomin.mail.controller;

import com.ray.pomin.mail.controller.dto.EmailRequest;
import com.ray.pomin.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail/code")
    public void sendAccessCode(@RequestBody EmailRequest request) {
        mailService.sendCode(request.email());
    }

    @GetMapping("/mail/code/auth")
    public boolean checkAccessCode(@RequestParam String email, @RequestParam String code) {
        return mailService.checkCode(email, code);
    }

}
