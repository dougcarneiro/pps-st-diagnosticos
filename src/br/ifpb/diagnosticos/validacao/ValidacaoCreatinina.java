package br.ifpb.diagnosticos.validacao;

import java.util.HashMap;
import java.util.Map;
import br.ifpb.diagnosticos.enums.TipoExame;

/**
 * Validação específica para creatinina
 */
public class ValidacaoCreatinina extends ValidadorBase {

    public ValidacaoCreatinina() {
        super(TipoExame.HEMOGRAMA);
    }

    @Override
    public boolean processar(Map<String, Object> dados, TipoExame tipoExame) {
        // Validar valores de creatinina se presentes
        if (dados.containsKey("creatinina")) {
            Object creatininaObj = dados.get("creatinina");
            if (!(creatininaObj instanceof HashMap)) {
                System.out.println("❌ Erro: Dados de creatinina devem estar em formato HashMap");
                return false;
            } else {
                HashMap<String, Object> creatininaMap = (HashMap<String, Object>) creatininaObj;
                if (!creatininaMap.containsKey("valor")) {
                    System.out.println("❌ Erro: Valor de creatinina não encontrado");
                    return false;
                } else {
                    Object valorObj = creatininaMap.get("valor");
                    if (!(valorObj instanceof Number)) {
                        System.out.println("❌ Erro: Valor de creatinina deve ser numérico");
                        return false;
                    } else {
                        double creatinina = ((Number) valorObj).doubleValue();
                        if (creatinina < 0 || creatinina > 10) {
                            System.out.println("❌ Erro: Valor de creatinina fora do range válido (0-10 mg/dL)");
                            return false;
                        }
                    }
                }
            }
        }
        
        return validarProximo(dados, tipoExame);
    }
}