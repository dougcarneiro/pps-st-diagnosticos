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

    public void atualizar(String nome, String mensagem, String caminhoAnexo) {
        EmailServico.enviarEmail(nome, enderecoEmail, mensagem, caminhoAnexo);
        System.out.println("📧 Email enviado para " + enderecoEmail + ": " + mensagem + (caminhoAnexo != null ? " [Arquivo Anexado]" : ""));
    }
    
    public String getEnderecoEmail() {
        return enderecoEmail;
    }
}
