package br.ifpb.diagnosticos.validacao;

import java.util.Map;
import br.ifpb.diagnosticos.enums.TipoExame;

/**
 * Validação específica para exames sanguíneos com indicadores
 */
public class ValidacaoHemograma extends ValidadorBase {

    public ValidacaoHemograma() {
        super(TipoExame.HEMOGRAMA);
    }

    @Override
    public boolean processar(Map<String, Object> dados, TipoExame tipoExame) {
        boolean validado = true;
        
        // Validar dados básicos
        if (dados == null || dados.isEmpty()) {
            System.out.println("Erro: Nenhum dado fornecido para o exame sanguíneo");
            return false;
        }
        
        // Verificar se pelo menos um indicador está presente
        boolean temIndicador = dados.containsKey("glicemia") || 
                              dados.containsKey("colesterol") || 
                              dados.containsKey("creatinina") ||
                              dados.containsKey("hemoglobina") ||
                              dados.containsKey("hematocritos") ||
                              dados.containsKey("leucocitos");
        
        if (!temIndicador) {
            System.out.println("Erro: Nenhum indicador válido encontrado no exame sanguíneo");
            validado = false;
        }
        
        if (validado) {
            System.out.println("✅ Dados do exame sanguíneo validados com sucesso");
            return validarProximo(dados, tipoExame);
        }

        return validarProximo(dados, tipoExame);
    }
}
