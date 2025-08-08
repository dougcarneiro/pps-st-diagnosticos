package br.ifpb.diagnosticos.laudos.tipos;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.laudos.Laudo;
import br.ifpb.diagnosticos.laudos.formatos.FormatoLaudo;

/**
 * Laudo específico para exames de Ultrassonografia
 */
public class LaudoUltrassonografia extends Laudo {
    
    public LaudoUltrassonografia(FormatoLaudo formato, Exame exame) {
        super(formato, exame);
    }
    
    @Override
    protected String gerarDadosExame() {
        StringBuilder dados = new StringBuilder();
        dados.append("TIPO: ULTRASSONOGRAFIA\n");
        dados.append("Preço: R$ ").append(exame.getValor()).append("\n");
        
        if (exame.getDados() != null) {
            if (exame.getDados().containsKey("orgao")) {
                String orgao = (String) exame.getDados().get("orgao");
                dados.append("Órgão examinado: ").append(orgao).append("\n");
            }
            
            if (exame.getDados().containsKey("achados")) {
                String achados = (String) exame.getDados().get("achados");
                dados.append("Achados: ").append(achados).append("\n");
            }
            
            if (exame.getDados().containsKey("ecogenicidade")) {
                String ecogenicidade = (String) exame.getDados().get("ecogenicidade");
                dados.append("Ecogenicidade: ").append(ecogenicidade).append("\n");
            }
            
            if (exame.getDados().containsKey("medidas")) {
                String medidas = (String) exame.getDados().get("medidas");
                dados.append("Medidas: ").append(medidas).append("\n");
            }
        }
        
        dados.append("\n");
        return dados.toString();
    }
}
