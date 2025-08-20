package br.ifpb.diagnosticos.validacao;

import java.util.Map;

/**
 * Validador específico para exames de Ressonância Magnética
 */
public class ValidacaoRessonancia extends ValidadorBase {

    public ValidacaoRessonancia() {
        super(TipoExame.RESSONANCIA);
    }

    @Override
    public boolean processar(Map<String, Object> dados, TipoExame tipoExame) {
        System.out.println("Validando dados de Ressonância Magnética...");
        
        // Validar se região está especificada
        if (!dados.containsKey("regiao")) {
            System.out.println("❌ Erro: Região a ser examinada deve ser especificada");
            return false;
        }
        
        String regiao = (String) dados.get("regiao");
        if (regiao == null || regiao.trim().isEmpty()) {
            System.out.println("❌ Erro: Nome da região não pode estar vazio");
            return false;
        }
        
        // Validar se sequências estão especificadas
        if (!dados.containsKey("sequencias")) {
            System.out.println("❌ Erro: Sequências de RM devem ser especificadas");
            return false;
        }
        
        String sequencias = (String) dados.get("sequencias");
        if (sequencias == null || sequencias.trim().isEmpty()) {
            System.out.println("❌ Erro: Sequências não podem estar vazias");
            return false;
        }
        
        System.out.println("✅ Validação de Ressonância Magnética passou");
        return validarProximo(dados, tipoExame);
    }
}
