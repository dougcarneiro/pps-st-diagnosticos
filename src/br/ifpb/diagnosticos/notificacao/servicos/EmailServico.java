package br.ifpb.diagnosticos.notificacao.servicos;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailServico {

    public static void enviarEmail(String nome, String email, String mensagem) {
        final String remetente = "douglas.carneiro@academico.ifpb.edu.br";
        final String senha = "usar senha de app";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("caio.soares@academico.ifpb.edu.br"));
            message.setSubject("Notificação ST Diagnósticos");
            message.setText("Olá " + nome + ",\n\n" + mensagem);

            Transport.send(message);
            System.out.println("📧 Email enviado para " + nome);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("📧 [Simulação] Email para " + nome + ": " + mensagem);
        }
    }
}