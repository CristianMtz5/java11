package com.example.reporteadorBackEnd.Controller.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.reporteadorBackEnd.Service.Email.EmailService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Email")
public class EmailController {
    
    @Autowired
    EmailService emailService;
    
    @PostMapping("/enviar")
    public String enviar(@RequestBody EmailDetails emailDto){
        String status = emailService.sendEmail(emailDto);
        return status;
    }

    @PostMapping("/enviar/conArchivo")
    public String enviarConArchivo(@RequestBody EmailDetails emailDto){
        String status = emailService.sendEmailconArchivo(emailDto);
        return status;
    }
}
