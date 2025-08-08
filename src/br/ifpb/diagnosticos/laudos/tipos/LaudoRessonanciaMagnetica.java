package br.ifpb.diagnosticos.laudos.tipos;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.laudos.Laudo;
import br.ifpb.diagnosticos.laudos.formatos.FormatoLaudo;

/**
 * Laudo específico para exames de Ressonância Magnética
 */
public class LaudoRessonanciaMagnetica extends Laudo {
    
    public LaudoRessonanciaMagnetica(FormatoLaudo formato, Exame exame) {
        super(formato, exame);
    }
    
    @Override
    protected String gerarDadosExame() {
        StringBuilder dados = new StringBuilder();
        dados.append("TIPO: RESSONÂNCIA MAGNÉTICA\n");
        dados.append("Preço: R$ ").append(exame.getValor()).append("\n");
        
        if (exame.getDados() != null) {
            if (exame.getDados().containsKey("regiao")) {
                String regiao = (String) exame.getDados().get("regiao");
                dados.append("Região examinada: ").append(regiao).append("\n");
            }
            
            if (exame.getDados().containsKey("sequencias")) {
                String sequencias = (String) exame.getDados().get("sequencias");
                dados.append("Sequências utilizadas: ").append(sequencias).append("\n");
            }
            
            if (exame.getDados().containsKey("contraste")) {
                Boolean contraste = (Boolean) exame.getDados().get("contraste");
                dados.append("Uso de contraste: ").append(contraste ? "Sim" : "Não").append("\n");
            }
            
            if (exame.getDados().containsKey("laudo_tecnico")) {
                String laudoTecnico = (String) exame.getDados().get("laudo_tecnico");
                dados.append("Laudo técnico: ").append(laudoTecnico).append("\n");
            }
            
            if (exame.getDados().containsKey("impressao")) {
                String impressao = (String) exame.getDados().get("impressao");
                dados.append("Impressão diagnóstica: ").append(impressao).append("\n");
            }
        }
        
        dados.append("\n");
        return dados.toString();
    }
}
