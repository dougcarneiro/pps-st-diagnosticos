package br.ifpb.diagnosticos.validacao;

import java.util.Map;

/**
 * Validação específica para exames sanguíneos com indicadores
 */
public class ValidacaoHemograma extends ValidadorBase {
    
    @Override
    public boolean validar(Map<String, Object> dados) {
        boolean validado = true;
        
        // Validar dados básicos
        if (dados == null || dados.isEmpty()) {
            System.out.println("❌ Erro: Nenhum dado fornecido para o exame sanguíneo");
            return false;
        }
        
        // Verificar se pelo menos um indicador está presente
        boolean temIndicador = dados.containsKey("glicose") || 
                              dados.containsKey("colesterol_total") || 
                              dados.containsKey("creatinina") ||
                              dados.containsKey("hemoglobina") ||
                              dados.containsKey("hematocritos") ||
                              dados.containsKey("leucocitos");
        
        if (!temIndicador) {
            System.out.println("❌ Erro: Nenhum indicador válido encontrado no exame sanguíneo");
            validado = false;
        }
        
        // Validar valores de glicose se presentes
        if (dados.containsKey("glicose")) {
            Object glicoseObj = dados.get("glicose");
            if (!(glicoseObj instanceof Number)) {
                System.out.println("❌ Erro: Valor de glicose deve ser numérico");
                validado = false;
            } else {
                double glicose = ((Number) glicoseObj).doubleValue();
                if (glicose < 0 || glicose > 500) {
                    System.out.println("❌ Erro: Valor de glicose fora do range válido (0-500 mg/dL)");
                    validado = false;
                }
            }
        }
        
        // Validar valores de colesterol se presentes
        if (dados.containsKey("colesterol_total")) {
            Object colesterolObj = dados.get("colesterol_total");
            if (!(colesterolObj instanceof Number)) {
                System.out.println("❌ Erro: Valor de colesterol total deve ser numérico");
                validado = false;
            } else {
                double colesterol = ((Number) colesterolObj).doubleValue();
                if (colesterol < 0 || colesterol > 600) {
                    System.out.println("❌ Erro: Valor de colesterol total fora do range válido (0-600 mg/dL)");
                    validado = false;
                }
            }
        }
        
        // Validar valores de creatinina se presentes
        if (dados.containsKey("creatinina")) {
            Object creatininaObj = dados.get("creatinina");
            if (!(creatininaObj instanceof Number)) {
                System.out.println("❌ Erro: Valor de creatinina deve ser numérico");
                validado = false;
            } else {
                double creatinina = ((Number) creatininaObj).doubleValue();
                if (creatinina < 0 || creatinina > 10) {
                    System.out.println("❌ Erro: Valor de creatinina fora do range válido (0-10 mg/dL)");
                    validado = false;
                }
            }
        }
        
        if (validado) {
            System.out.println("✅ Dados do exame sanguíneo validados com sucesso");
            return validarProximo(dados);
        }
        
        return false;
    }
}
