package br.ifpb.diagnosticos.exames.tipos;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.modelo.Paciente;
import br.ifpb.diagnosticos.enums.TipoExame;

/**
 * Implementação concreta de exame de Ultrassonografia
 */
public class Ultrassonografia extends Exame {
    
    public Ultrassonografia(Paciente paciente, double valor) {
        super(paciente, valor);
        this.tipoExame = TipoExame.ULTRASSONOGRAFIA;
        this.dados.put("orgao", "Abdomen total");
        this.dados.put("achados", "Fígado de dimensões normais");
        this.dados.put("ecogenicidade", "Homogênea");
        this.dados.put("medidas", "15,2 x 8,1 cm");
    }
}
