package br.ifpb.diagnosticos.notificacao;

/**
 * Implementação de notificação por email (Observer Pattern)
 */
public class EmailNotificador implements Observador {
    private String enderecoEmail;
    
    public EmailNotificador(String enderecoEmail) {
        this.enderecoEmail = enderecoEmail;
    }
    
    @Override
    public void atualizar(String mensagem) {
        System.out.println("📧 Email enviado para " + enderecoEmail + ": " + mensagem);
    }
    
    public String getEnderecoEmail() {
        return enderecoEmail;
    }
}
