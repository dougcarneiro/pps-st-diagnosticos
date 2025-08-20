package br.ifpb.diagnosticos.validacao;

import java.util.Map;

/**
 * Validador específico para exames de Ultrassonografia
 */
public class ValidacaoUltrassonografia extends ValidadorBase {

    public ValidacaoUltrassonografia() {
        super(TipoExame.ULTRASSONOGRAFIA);
    }

    @Override
    public boolean processar(Map<String, Object> dados, TipoExame tipoExame) {
        System.out.println("Validando dados de Ultrassonografia...");
        
        // Validar se órgão está especificado
        if (!dados.containsKey("orgao")) {
            System.out.println("❌ Erro: Órgão a ser examinado deve ser especificado");
            return false;
        }
        
        String orgao = (String) dados.get("orgao");
        if (orgao == null || orgao.trim().isEmpty()) {
            System.out.println("❌ Erro: Nome do órgão não pode estar vazio");
            return false;
        }
        
        System.out.println("✅ Validação de Ultrassonografia passou");
        return validarProximo(dados, tipoExame);
    }
}
