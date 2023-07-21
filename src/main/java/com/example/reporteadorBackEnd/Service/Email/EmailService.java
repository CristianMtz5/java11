package com.example.reporteadorBackEnd.Service.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Controller.Email.EmailDetails;

import java.io.File;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Service
@Transactional
public class EmailService{
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendEmail(EmailDetails emailDto){
        // MimeMessage message = javaMailSender.createMimeMessage();
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            
            email.setFrom(sender);
            email.setTo(emailDto.getTo());
            email.setSubject(emailDto.getSubject());
            email.setText(emailDto.getBody());

            javaMailSender.send(email);
            return "Se envio";
        } catch (Exception e) {
            return e.toString(); 
        }
    }

    public String sendEmailconArchivo(EmailDetails emailDto){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(emailDto.getTo());
            mimeMessageHelper.setSubject(emailDto.getSubject());
            mimeMessageHelper.setText(emailDto.getBody());

            for(int i=0; i<emailDto.getAttachment().size(); i++){
                FileSystemResource file = new FileSystemResource(new File(emailDto.getAttachment().get(i)));
                mimeMessageHelper.addAttachment(file.getFilename(), file);
            }

            javaMailSender.send(mimeMessage);
            return "Se envio";
        } catch (Exception e) {
            return "Error al enviar archivo " + e.toString();
        }
    }
}
