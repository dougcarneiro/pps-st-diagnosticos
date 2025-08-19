package br.ifpb.diagnosticos.exames.componentes;

import br.ifpb.diagnosticos.exames.Exame;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Indicador para análise de glicose
 */
public class IndicadorGlicose extends IndicadorExame {
    private double valorGlicose;
    
    public IndicadorGlicose(Exame exameBase) {
        super(exameBase);
    }
    
    @Override
    protected void realizarAnaliseIndicador() {
        System.out.println("Analisando níveis de glicose no sangue...");
        // Simular resultado da análise
        Random random = new Random();
        this.valorGlicose = 70 + (random.nextDouble() * 160); // 70-230 mg/dL
    }
    
    @Override
    protected void adicionarDadosIndicador(Map<String, Object> dados) {
        if (dados != null) {

            HashMap<String, Object> glicose = new HashMap<>();
            glicose.put("valor", valorGlicose);
            glicose.put("unidade", getUnidadeMedida());
            glicose.put("referencia", getValorReferencia());
            glicose.put("status", avaliarResultado());

            dados.put("glicose", glicose);
        }
    }
    
    @Override
    public String getNomeIndicador() {
        return "Glicose";
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
        if (valorGlicose < 70) {
            return "ABAIXO DO NORMAL (Hipoglicemia)";
        } else if (valorGlicose <= 99) {
            return "NORMAL";
        } else if (valorGlicose <= 125) {
            return "ALTERADO (Glicemia de jejum alterada)";
        } else {
            return "ACIMA DO NORMAL (Diabetes)";
        }
    }
}
