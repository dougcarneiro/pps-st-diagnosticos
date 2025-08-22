package br.ifpb.diagnosticos.notificacao;

/**
 * Implementação de notificação por SMS (Observer Pattern)
 */
public class SmsNotificador implements Observador {
    private String numeroTelefone;

    public SmsNotificador(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    @Override
    public void atualizar(String nome, String mensagem) {
        System.out.println("📱 SMS enviado para " + numeroTelefone + ": " + mensagem);
    }
    
    public String getNumeroTelefone() {
        return numeroTelefone;
    }
}
