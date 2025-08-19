package br.ifpb.diagnosticos.validacao;

import java.util.HashMap;
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
                              dados.containsKey("colesterol") || 
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
            if (!(glicoseObj instanceof HashMap)) {
                System.out.println("❌ Erro: Dados de glicose devem estar em formato HashMap");
                validado = false;
            } else {
                @SuppressWarnings("unchecked")
                HashMap<String, Object> glicoseMap = (HashMap<String, Object>) glicoseObj;
                if (!glicoseMap.containsKey("valor")) {
                    System.out.println("❌ Erro: Valor de glicose não encontrado");
                    validado = false;
                } else {
                    Object valorObj = glicoseMap.get("valor");
                    if (!(valorObj instanceof Number)) {
                        System.out.println("❌ Erro: Valor de glicose deve ser numérico");
                        validado = false;
                    } else {
                        double glicose = ((Number) valorObj).doubleValue();
                        if (glicose < 0 || glicose > 500) {
                            System.out.println("❌ Erro: Valor de glicose fora do range válido (0-500 mg/dL)");
                            validado = false;
                        }
                    }
                }
            }
        }
        
        // Validar valores de colesterol se presentes
        if (dados.containsKey("colesterol")) {
            Object colesterolObj = dados.get("colesterol");
            if (!(colesterolObj instanceof HashMap)) {
                System.out.println("❌ Erro: Dados de colesterol devem estar em formato HashMap");
                validado = false;
            } else {
                @SuppressWarnings("unchecked")
                HashMap<String, Object> colesterolMap = (HashMap<String, Object>) colesterolObj;
                if (!colesterolMap.containsKey("colesterol_total")) {
                    System.out.println("❌ Erro: Valor de colesterol total não encontrado");
                    validado = false;
                } else {
                    Object valorObj = colesterolMap.get("colesterol_total");
                    if (!(valorObj instanceof Number)) {
                        System.out.println("❌ Erro: Valor de colesterol total deve ser numérico");
                        validado = false;
                    } else {
                        double colesterol = ((Number) valorObj).doubleValue();
                        if (colesterol < 0 || colesterol > 600) {
                            System.out.println("❌ Erro: Valor de colesterol total fora do range válido (0-600 mg/dL)");
                            validado = false;
                        }
                    }
                }
            }
        }
        
        // Validar valores de creatinina se presentes
        if (dados.containsKey("creatinina")) {
            Object creatininaObj = dados.get("creatinina");
            if (!(creatininaObj instanceof HashMap)) {
                System.out.println("❌ Erro: Dados de creatinina devem estar em formato HashMap");
                validado = false;
            } else {
                @SuppressWarnings("unchecked")
                HashMap<String, Object> creatininaMap = (HashMap<String, Object>) creatininaObj;
                if (!creatininaMap.containsKey("valor")) {
                    System.out.println("❌ Erro: Valor de creatinina não encontrado");
                    validado = false;
                } else {
                    Object valorObj = creatininaMap.get("valor");
                    if (!(valorObj instanceof Number)) {
                        System.out.println("❌ Erro: Valor de creatinina deve ser numérico");
                        validado = false;
                    } else {
                        double creatinina = ((Number) valorObj).doubleValue();
                        if (creatinina < 0 || creatinina > 10) {
                            System.out.println("❌ Erro: Valor de creatinina fora do range válido (0-10 mg/dL)");
                            validado = false;
                        }
                    }
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
