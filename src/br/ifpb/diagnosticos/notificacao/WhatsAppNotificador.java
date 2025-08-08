package br.ifpb.diagnosticos.notificacao;

/**
 * Implementação de notificação por WhatsApp (Observer Pattern)
 */
public class WhatsAppNotificador implements Observador {
    private String numeroWhatsApp;
    
    public WhatsAppNotificador(String numeroWhatsApp) {
        this.numeroWhatsApp = numeroWhatsApp;
    }
    
    @Override
    public void atualizar(String mensagem) {
        System.out.println("💬 WhatsApp enviado para " + numeroWhatsApp + ": " + mensagem);
    }
    
    public String getNumeroWhatsApp() {
        return numeroWhatsApp;
    }
}
