package com.demo.mailsending.sampler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MainController {

    @Autowired
    MailService mailService;

    @GetMapping("/send")
    public void sendMail(@RequestParam(name = "name", defaultValue = "Stranger") String name ){
        mailService.sendEmail("Hello " + name);
        System.out.println("Done");
    }

    @GetMapping("/sendTemplate")
    public void sendTemplateMail(@RequestParam(name = "name", defaultValue = "Stranger") String name )
            throws MessagingException {
        mailService.sendEmailTemplate();
        System.out.println("Done template");
    }
}
