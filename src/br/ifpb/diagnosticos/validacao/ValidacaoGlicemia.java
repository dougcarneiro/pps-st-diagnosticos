package br.ifpb.diagnosticos.validacao;

import java.util.HashMap;
import java.util.Map;
import br.ifpb.diagnosticos.enums.TipoExame;

/**
 * Validação específica para glicemia
 */
public class ValidacaoGlicemia extends ValidadorBase {

    public ValidacaoGlicemia() {
        super(TipoExame.HEMOGRAMA);
    }

    @Override
    public boolean processar(Map<String, Object> dados, TipoExame tipoExame) {
        // Validar valores de glicemia se presentes
        if (dados.containsKey("glicemia")) {
            Object glicemiaObj = dados.get("glicemia");
            if (!(glicemiaObj instanceof HashMap)) {
                System.out.println("❌ Erro: Dados de glicemia devem estar em formato HashMap");
                return false;
            } else {
                Map<String, Object> glicemiaMap = (Map<String, Object>) glicemiaObj;
                if (!glicemiaMap.containsKey("valor")) {
                    System.out.println("❌ Erro: Valor de glicemia não encontrado");
                    return false;
                } else {
                    Object valorObj = glicemiaMap.get("valor");
                    if (!(valorObj instanceof Number)) {
                        System.out.println("❌ Erro: Valor de glicemia deve ser numérico");
                        return false;
                    } else {
                        double glicemia = ((Number) valorObj).doubleValue();
                        if (glicemia < 0 || glicemia > 500) {
                            System.out.println("❌ Erro: Valor de glicemia fora do range válido (0-500 mg/dL)");
                            return false;
                        }
                    }
                }
            }
        }
        return validarProximo(dados, tipoExame);
    }
}
