package com.demo.mailsending.sampler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    void sendEmail(String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("taleporgia@gmail.com");
        msg.setSubject("Testing from Spring Boot");
        msg.setText(text);
        mailSender.send(msg);
        System.out.println("Mail sent");
    }

    void sendEmailTemplate() throws MessagingException {
//        boilerplate
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

//        access resources in classpath
        ClassPathResource img = new ClassPathResource("images/javabydeveloper-email.PNG");
//        add resource as attachment in mail
        helper.addAttachment("template-cover.png", img);

//        simple dynamic properties, could be any object, access through thymeleaf
        Map<String, Object> props = new HashMap<>();
        props.put("name", "Developer!");
        props.put("location", "United States");
        props.put("sign", "Java Developer");

//        add props to context
        Context context = new Context();
        context.setVariables(props);
//        file name to parse without html
        String html = templateEngine.process("email-template", context);

//        obvious
        helper.setTo("taleporgia@gmail.com");
        helper.setText(html, true);
        helper.setSubject("Mail template");
//        first argument is cid, acces it in html like this:
//        <img id="img" src="cid:img" />
        helper.addInline("img", img);
        mailSender.send(message);
    }
}
