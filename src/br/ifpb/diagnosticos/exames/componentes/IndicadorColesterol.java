package br.ifpb.diagnosticos.exames.componentes;

import br.ifpb.diagnosticos.exames.Exame;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Indicador para análise de colesterol
 */
public class IndicadorColesterol extends IndicadorExame {
    private double valorColesterolTotal;
    private double valorLDL;
    private double valorHDL;
    
    public IndicadorColesterol(Exame exameBase) {
        super(exameBase);
    }
    
    @Override
    protected void realizarAnaliseIndicador() {
        System.out.println("Analisando níveis de colesterol no sangue...");
        // Simular resultado da análise
        Random random = new Random();
        this.valorColesterolTotal = 150 + (random.nextDouble() * 150); // 150-300 mg/dL
        this.valorLDL = 70 + (random.nextDouble() * 120); // 70-190 mg/dL
        this.valorHDL = 35 + (random.nextDouble() * 45); // 35-80 mg/dL
    }
    
    @Override
    protected void adicionarDadosIndicador(Map<String, Object> dados) {
        if (dados != null) {
            HashMap<String, Object> colesterol = new HashMap<>();
            colesterol.put("colesterol_total", valorColesterolTotal);
            colesterol.put("colesterol_ldl", valorLDL);
            colesterol.put("colesterol_hdl", valorHDL);
            colesterol.put("colesterol_unidade", getUnidadeMedida());
            colesterol.put("colesterol_referencia", getValorReferencia());
            colesterol.put("colesterol_status", avaliarResultado());
            dados.put("colesterol", colesterol);
        }
    }
    
    @Override
    public String getNomeIndicador() {
        return "Colesterol";
    }
    
    @Override
    public String getUnidadeMedida() {
        return "mg/dL";
    }
    
    @Override
    public String getValorReferencia() {
        return "Total: <200 mg/dL, LDL: <100 mg/dL, HDL: >40 mg/dL (H) / >50 mg/dL (M)";
    }
    
    private String avaliarResultado() {
        StringBuilder status = new StringBuilder();
        
        if (valorColesterolTotal < 200) {
            status.append("Total: NORMAL; ");
        } else if (valorColesterolTotal < 240) {
            status.append("Total: LIMÍTROFE; ");
        } else {
            status.append("Total: ALTO; ");
        }
        
        if (valorLDL < 100) {
            status.append("LDL: ÓTIMO; ");
        } else if (valorLDL < 130) {
            status.append("LDL: PRÓXIMO DO ÓTIMO; ");
        } else if (valorLDL < 160) {
            status.append("LDL: LIMÍTROFE; ");
        } else {
            status.append("LDL: ALTO; ");
        }
        
        if (valorHDL >= 60) {
            status.append("HDL: ALTO (PROTETOR)");
        } else if (valorHDL >= 40) {
            status.append("HDL: NORMAL");
        } else {
            status.append("HDL: BAIXO");
        }
        
        return status.toString();
    }
}
