package br.ifpb.diagnosticos.validacao;

import java.util.Map;

/**
 * Validador específico para exames de Hemograma
 */
public class ValidacaoHemograma extends ValidadorBase {
    
    @Override
    public boolean validar(Map<String, Object> dados) {
        System.out.println("Validando dados do hemograma...");
        
        // Validar campos obrigatórios
        if (!dados.containsKey("hemoglobina")) {
            System.out.println("❌ Erro: Campo 'hemoglobina' é obrigatório para hemograma");
            return false;
        }
        
        if (!dados.containsKey("leucocitos")) {
            System.out.println("❌ Erro: Campo 'leucocitos' é obrigatório para hemograma");
            return false;
        }
        
        if (!dados.containsKey("hematocritos")) {
            System.out.println("❌ Erro: Campo 'hematocritos' é obrigatório para hemograma");
            return false;
        }
        
        // Validações específicas para hemograma
        Double hemoglobina = (Double) dados.get("hemoglobina");
        if (hemoglobina < 8.0 || hemoglobina > 18.0) {
            System.out.println("⚠️ ALERTA: Valor de hemoglobina fora do range crítico (8.0-18.0)!");
        }
        
        Integer leucocitos = (Integer) dados.get("leucocitos");
        if (leucocitos < 4000 || leucocitos > 11000) {
            System.out.println("⚠️ ALERTA: Contagem de leucócitos fora do range normal (4000-11000)!");
        }
        
        Double hematocritos = (Double) dados.get("hematocritos");
        if (hematocritos < 36.0 || hematocritos > 50.0) {
            System.out.println("⚠️ ALERTA: Valor de hematócritos fora do range normal (36.0-50.0)!");
        }
        
        System.out.println("✅ Validação do hemograma concluída com sucesso.");
        return validarProximo(dados);
    }
}
