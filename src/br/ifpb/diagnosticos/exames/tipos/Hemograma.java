package br.ifpb.diagnosticos.exames.tipos;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.modelo.Paciente;
import br.ifpb.diagnosticos.enums.TipoExame;

/**
 * Implementação concreta de exame de Hemograma
 */
public class Hemograma extends Exame {

    public Hemograma(Paciente paciente, double valor) {
        super(paciente, valor);
        this.tipoExame = TipoExame.HEMOGRAMA;
        this.dados.put("hemoglobina", 14.5);
        this.dados.put("hematocritos", 42.0);
        this.dados.put("leucocitos", 8000);
    }
}
