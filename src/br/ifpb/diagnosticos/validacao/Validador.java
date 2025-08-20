package br.ifpb.diagnosticos.validacao;

import java.util.Map;

import br.ifpb.diagnosticos.validacao.ValidadorBase.TipoExame;

/**
 * Interface para validadores (Chain of Responsibility)
 */
public interface Validador {
    boolean validar(Map<String, Object> dados, TipoExame tipoExame);
    boolean processar(Map<String, Object> dados, TipoExame tipoExame);
    Validador setProximo(Validador validador);
}
