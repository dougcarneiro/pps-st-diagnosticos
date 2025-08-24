package br.ifpb.diagnosticos.notificacao;

import br.ifpb.diagnosticos.notificacao.servicos.EmailServico;

/**
 * Implementação de notificação por email (Observer Pattern)
 */
public class EmailNotificador implements Observador {
    private String enderecoEmail;
    
    public EmailNotificador(String enderecoEmail) {
        this.enderecoEmail = enderecoEmail;
    }
    
    @Override
    public void atualizar(String nome, String mensagem) {
        atualizar(nome, mensagem, null);
    }

    // Novo método para enviar com PDF
    public void atualizar(String nome, String mensagem, String caminhoPdf) {
        EmailServico.enviarEmail(nome, enderecoEmail, mensagem, caminhoPdf);
        System.out.println("📧 Email enviado para " + enderecoEmail + ": " + mensagem + (caminhoPdf != null ? " [PDF anexado]" : ""));
    }
    
    public String getEnderecoEmail() {
        return enderecoEmail;
    }
}
