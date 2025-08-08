package br.ifpb.diagnosticos.validacao;

import java.util.Map;

/**
 * Interface para validadores (Chain of Responsibility)
 */
public interface Validador {
    boolean validar(Map<String, Object> dados);
    void setProximo(Validador validador);
}
