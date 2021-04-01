//package com.br.oferta.api.util.mail;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Mailer {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @EventListener
//    private void teste(ApplicationReadyEvent event) {
//        this.enviarEmail("bookofertasbr@gmail.com",
//                Arrays.asList("ofertasbv@gmail.com", "projetogdados@gmail.com"), "Teste de envio", "Olá, samos a BookOfertas...");
//        System.out.println("Finalizando envio de email...");
//        event.getApplicationContext();
//    }
//
//    public void enviarEmail(String remetente, List<String> destinatarios, String asunto, String mensage) {
//        try {
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
//            helper.setFrom(remetente);
//
//            helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
//            helper.setSubject(asunto);
//            helper.setText(mensage, true);
//            mailSender.send(mimeMessage);
//        } catch (MessagingException ex) {
//            Logger.getLogger(Mailer.class.getName()).log(Level.SEVERE, null, ex);
//            throw new RuntimeException("Problema de envio de email!" + ex.getMessage());
//        }
//    }
//
//}
