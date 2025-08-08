package br.ifpb.diagnosticos.laudos.tipos;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.laudos.Laudo;
import br.ifpb.diagnosticos.laudos.formatos.FormatoLaudo;

/**
 * Laudo específico para exames de Hemograma
 */
public class LaudoHemograma extends Laudo {
    
    public LaudoHemograma(FormatoLaudo formato, Exame exame) {
        super(formato, exame);
    }
    
    @Override
    protected String gerarDadosExame() {
        StringBuilder dados = new StringBuilder();
        dados.append("TIPO: HEMOGRAMA COMPLETO\n");
        dados.append("Preço: R$ ").append(exame.getValor()).append("\n");
        
        if (exame.getDados() != null) {
            if (exame.getDados().containsKey("hemoglobina")) {
                Double hemoglobina = (Double) exame.getDados().get("hemoglobina");
                dados.append("Hemoglobina: ").append(hemoglobina).append(" g/dL\n");
                dados.append("Valor de referência: 12,0 - 16,0 g/dL (mulheres), 14,0 - 18,0 g/dL (homens)\n");
                
                if (hemoglobina < 12.0) {
                    dados.append("⚠️ ABAIXO DO NORMAL\n");
                } else if (hemoglobina > 18.0) {
                    dados.append("⚠️ ACIMA DO NORMAL\n");
                } else {
                    dados.append("✅ DENTRO DO NORMAL\n");
                }
            }
            
            if (exame.getDados().containsKey("hematocritos")) {
                Double hematocritos = (Double) exame.getDados().get("hematocritos");
                dados.append("Hematócritos: ").append(hematocritos).append("%\n");
                dados.append("Valor de referência: 36,0 - 46,0% (mulheres), 41,0 - 50,0% (homens)\n");
                
                if (hematocritos < 36.0) {
                    dados.append("⚠️ ABAIXO DO NORMAL\n");
                } else if (hematocritos > 50.0) {
                    dados.append("⚠️ ACIMA DO NORMAL\n");
                } else {
                    dados.append("✅ DENTRO DO NORMAL\n");
                }
            }
            
            if (exame.getDados().containsKey("leucocitos")) {
                Integer leucocitos = (Integer) exame.getDados().get("leucocitos");
                dados.append("Leucócitos: ").append(leucocitos).append(" /mm³\n");
                dados.append("Valor de referência: 4.000 - 11.000 /mm³\n");
                
                if (leucocitos < 4000) {
                    dados.append("⚠️ ABAIXO DO NORMAL\n");
                } else if (leucocitos > 11000) {
                    dados.append("⚠️ ACIMA DO NORMAL\n");
                } else {
                    dados.append("✅ DENTRO DO NORMAL\n");
                }
            }
        }
        
        dados.append("\n");
        return dados.toString();
    }
}
