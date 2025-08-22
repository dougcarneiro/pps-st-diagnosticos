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
        EmailServico.enviarEmail(nome, enderecoEmail, mensagem);
        System.out.println("📧 Email enviado para " + enderecoEmail + ": " + mensagem);
    }
    
    public String getEnderecoEmail() {
        return enderecoEmail;
    }
}
