package br.ifpb.diagnosticos.validacao;

import java.util.Map;

/**
 * Classe base abstrata para validadores
 */
public abstract class ValidadorBase implements Validador {
    protected Validador proximo;
    
    @Override
    public void setProximo(Validador validador) {
        this.proximo = validador;
    }
    
    protected boolean validarProximo(Map<String, Object> dados) {
        if (proximo != null) {
            return proximo.validar(dados);
        }
        return true;
    }
}
