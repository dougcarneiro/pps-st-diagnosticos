package br.ifpb.diagnosticos.notificacao;

/**
 * Interface Observer para notificações
 */
public interface Observador {
    void atualizar(String nome, String mensagem);
}
