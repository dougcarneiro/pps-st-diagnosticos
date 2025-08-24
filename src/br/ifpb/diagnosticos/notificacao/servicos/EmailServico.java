package br.ifpb.diagnosticos.notificacao.servicos;

import br.ifpb.diagnosticos.utils.ConfiguracaoSistema;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailServico {

    public static void enviarEmail(String nome, String email, String mensagem, String caminhoPdf) {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        final String remetente = config.getEmailRemetente();
        final String senha = config.getEmailSenha();

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

            // Decide o destinatário baseado na configuração de dev
            String destinatario;
            if (config.isNotificacaoEmailDev()) {
                destinatario = config.getEmailDestinoDev(); // Email configurado para dev
            } else {
                destinatario = email; // Email real do paciente
            }
            
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Notificação ST Diagnósticos");

            // Corpo do email
            MimeBodyPart textoBodyPart = new MimeBodyPart();
            textoBodyPart.setText("Olá " + nome + ",\n\n" + mensagem);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textoBodyPart);

            // Anexo PDF
            if (caminhoPdf != null && !caminhoPdf.isEmpty()) {
                try {
                    MimeBodyPart anexoBodyPart = new MimeBodyPart();
                    anexoBodyPart.attachFile(caminhoPdf);
                    multipart.addBodyPart(anexoBodyPart);
                } catch (java.io.IOException ioe) {
                    ioe.printStackTrace();
                    System.out.println("Erro ao anexar PDF: " + caminhoPdf);
                }
            }

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("📧 Email enviado para " + nome + " (" + destinatario + ")");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("📧 [Simulação] Email para " + nome + " (" + email + "): " + mensagem);
        }
    }
}