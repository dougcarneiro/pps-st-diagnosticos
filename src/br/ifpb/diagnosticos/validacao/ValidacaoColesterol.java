package br.ifpb.diagnosticos.validacao;

import java.util.HashMap;
import java.util.Map;
import br.ifpb.diagnosticos.enums.TipoExame;

/**
 * Validação específica para colesterol
 */
public class ValidacaoColesterol extends ValidadorBase {

    public ValidacaoColesterol() {
        super(TipoExame.HEMOGRAMA);
    }

    @Override
    public boolean processar(Map<String, Object> dados, TipoExame tipoExame) {
        // Validar valores de colesterol se presentes
        if (dados.containsKey("colesterol")) {
            Object colesterolObj = dados.get("colesterol");
            if (!(colesterolObj instanceof HashMap)) {
                System.out.println("❌ Erro: Dados de colesterol devem estar em formato HashMap");
                return false;
            } else {
                HashMap<String, Object> colesterolMap = (HashMap<String, Object>) colesterolObj;
                if (!colesterolMap.containsKey("total")) {
                    System.out.println("❌ Erro: Valor de colesterol total não encontrado");
                    return false;
                } else {
                    Object valorObj = colesterolMap.get("total");
                    if (!(valorObj instanceof Number)) {
                        System.out.println("❌ Erro: Valor de colesterol total deve ser numérico");
                        return false;
                    } else {
                        double colesterol = ((Number) valorObj).doubleValue();
                        if (colesterol < 0 || colesterol > 600) {
                            System.out.println("❌ Erro: Valor de colesterol total fora do range válido (0-600 mg/dL)");
                            return false;
                        }
                    }
                }
            }
        }

        return validarProximo(dados, tipoExame);
    }
}
