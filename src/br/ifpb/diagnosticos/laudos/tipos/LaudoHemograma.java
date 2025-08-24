package br.ifpb.diagnosticos.laudos.tipos;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.laudos.Laudo;
import br.ifpb.diagnosticos.laudos.formatos.FormatoLaudo;
import java.util.Map;

/**
 * Laudo específico para exames sanguíneos com indicadores
 */
public class LaudoHemograma extends Laudo {
    
    public LaudoHemograma(FormatoLaudo formato, Exame exame) {
        super(formato, exame);
    }
    
    @Override
    protected String gerarDadosExame() {
        StringBuilder dados = new StringBuilder();
        dados.append("TIPO: HEMOGRAMA / EXAME SANGUÍNEO\n");
        dados.append("Preço: R$ ").append(exame.getValor()).append("\n\n");
        
        if (exame.getDados() != null) {
            Map<String, Object> dadosExame = exame.getDados();
            
            // Análises do hemograma tradicional (se presentes)
            if (dadosExame.containsKey("hemoglobina")) {
                dados.append("=== HEMOGRAMA TRADICIONAL ===\n");
                
                Double hemoglobina = (Double) dadosExame.get("hemoglobina");
                dados.append("Hemoglobina: ").append(hemoglobina).append(" g/dL\n");
                dados.append("Referência: 12,0 - 16,0 g/dL (mulheres), 14,0 - 18,0 g/dL (homens)\n");
                
                if (hemoglobina < 12.0) {
                    dados.append("[!] ABAIXO DO NORMAL\n");
                } else if (hemoglobina > 18.0) {
                    dados.append("[!] ACIMA DO NORMAL\n");
                } else {
                    dados.append("[OK] DENTRO DO NORMAL\n");
                }
                dados.append("\n");
                dadosExame.remove("hemoglobina");
            }
            
            if (dadosExame.containsKey("hematocritos")) {
                Double hematocritos = (Double) dadosExame.get("hematocritos");
                dados.append("Hematócritos: ").append(hematocritos).append("%\n");
                dados.append("Referência: 36,0 - 46,0% (mulheres), 41,0 - 50,0% (homens)\n");
                
                if (hematocritos < 36.0) {
                    dados.append("[!] ABAIXO DO NORMAL\n");
                } else if (hematocritos > 50.0) {
                    dados.append("[!] ACIMA DO NORMAL\n");
                } else {
                    dados.append("[OK] DENTRO DO NORMAL\n");
                }
                dados.append("\n");
                dadosExame.remove("hematocritos");
            }
            
            if (dadosExame.containsKey("leucocitos")) {
                Integer leucocitos = (Integer) dadosExame.get("leucocitos");
                dados.append("Leucócitos: ").append(leucocitos).append(" /mm³\n");
                dados.append("Referência: 4.000 - 11.000 /mm³\n");
                
                if (leucocitos < 4000) {
                    dados.append("[!] ABAIXO DO NORMAL\n");
                } else if (leucocitos > 11000) {
                    dados.append("[!] ACIMA DO NORMAL\n");
                } else {
                    dados.append("[OK] DENTRO DO NORMAL\n");
                }
                dados.append("\n");
                dadosExame.remove("leucocitos");
            }
        }
        return super.gerarDadosExame(dados);
    }
}
