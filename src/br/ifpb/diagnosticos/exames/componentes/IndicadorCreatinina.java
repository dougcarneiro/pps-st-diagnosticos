package br.ifpb.diagnosticos.exames.componentes;

import br.ifpb.diagnosticos.exames.Exame;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Indicador para análise de creatinina
 */
public class IndicadorCreatinina extends IndicadorExame {
    private double valorCreatinina;
    
    public IndicadorCreatinina(Exame exameBase) {
        super(exameBase);
    }
    
    @Override
    protected void realizarAnaliseIndicador() {
        System.out.println("Analisando níveis de creatinina no sangue...");
        // Simular resultado da análise
        Random random = new Random();
        this.valorCreatinina = 0.5 + (random.nextDouble() * 2.0); // 0.5-2.5 mg/dL
    }
    
    @Override
    protected void adicionarDadosIndicador(Map<String, Object> dados) {
        if (dados != null) {
            HashMap<String, Object> creatinina = new HashMap<>();
            creatinina.put("valor", valorCreatinina);
            creatinina.put("unidade", getUnidadeMedida());
            creatinina.put("referencia", getValorReferencia());
            creatinina.put("status", avaliarResultado());
            dados.put("creatinina", creatinina);
        }
    }
    
    @Override
    public String getNomeIndicador() {
        return "Creatinina";
    }
    
    @Override
    public String getUnidadeMedida() {
        return "mg/dL";
    }
    
    @Override
    public String getValorReferencia() {
        return "0.6-1.2 mg/dL (mulheres), 0.7-1.3 mg/dL (homens)";
    }
    
    private String avaliarResultado() {
        // Considerando valores gerais (seria melhor considerar sexo do paciente)
        if (valorCreatinina < 0.6) {
            return "ABAIXO DO NORMAL";
        } else if (valorCreatinina <= 1.3) {
            return "NORMAL";
        } else if (valorCreatinina <= 2.0) {
            return "LIGEIRAMENTE ELEVADO";
        } else {
            return "ELEVADO (possível disfunção renal)";
        }
    }
}
