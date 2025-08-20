package br.ifpb.diagnosticos.validacao;

import java.util.Map;
import br.ifpb.diagnosticos.enums.TipoExame;

/**
 * Classe base abstrata para validadores
 */
public abstract class ValidadorBase implements Validador {
    protected Validador proximo;
    protected TipoExame tipoExame;

    public ValidadorBase(TipoExame tipoExame) {
        this.tipoExame = tipoExame;
    }
    
    @Override
    public Validador setProximo(Validador validador) {
        this.proximo = validador;
        return validador;
    }

    @Override
    public boolean validar(Map<String, Object> dados, TipoExame tipoExame) {
        if (tipoExame == this.tipoExame) {
            boolean resultado = processar(dados, tipoExame);
            if (!resultado) {
                return false;
            }
        }
        return validarProximo(dados, tipoExame);
    }
    
    protected boolean validarProximo(Map<String, Object> dados, TipoExame tipoExame) {
        if (proximo != null) {
            return proximo.validar(dados, tipoExame);
        }
        return true;
    }
    
    public abstract boolean processar(Map<String, Object> dados, TipoExame tipoExame);
}
