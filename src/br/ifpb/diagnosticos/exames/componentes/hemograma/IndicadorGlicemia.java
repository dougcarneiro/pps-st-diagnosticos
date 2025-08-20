package br.ifpb.diagnosticos.exames.componentes.hemograma;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.exames.componentes.IndicadorExame;
import br.ifpb.diagnosticos.enums.TipoExame;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Indicador para análise de glicemia
 */
public class IndicadorGlicemia extends IndicadorExame {
    private double valorGlicemia;
    
    public IndicadorGlicemia(Exame exameBase) {
        super(exameBase);
        this.tipoExame = TipoExame.HEMOGRAMA;
    }
    
    @Override
    protected void realizarAnaliseIndicador() {
        System.out.println("Analisando níveis de glicemia no sangue...");
        // Simular resultado da análise
        Random random = new Random();
        this.valorGlicemia = 70 + (random.nextDouble() * 160); // 70-230 mg/dL
    }
    
    @Override
    protected void adicionarDadosIndicador(Map<String, Object> dados) {
        if (dados != null) {

            Map<String, Object> glicemia = new HashMap<>();
            glicemia.put("valor", valorGlicemia);
            glicemia.put("unidade", getUnidadeMedida());
            glicemia.put("referencia", getValorReferencia());
            glicemia.put("status", avaliarResultado());

            dados.put("glicemia", glicemia);
        }
    }
    
    @Override
    public String getNomeIndicador() {
        return "Glicemia";
    }
    
    @Override
    public String getUnidadeMedida() {
        return "mg/dL";
    }
    
    @Override
    public String getValorReferencia() {
        return "70-99 mg/dL (jejum)";
    }
    
    private String avaliarResultado() {
        if (valorGlicemia < 70) {
            return "ABAIXO DO NORMAL (Hipoglicemia)";
        } else if (valorGlicemia <= 99) {
            return "NORMAL";
        } else if (valorGlicemia <= 125) {
            return "ALTERADO (Glicemia de jejum alterada)";
        } else {
            return "ACIMA DO NORMAL (Diabetes)";
        }
    }
}
