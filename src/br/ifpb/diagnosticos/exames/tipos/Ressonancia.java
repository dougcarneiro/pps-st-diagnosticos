package br.ifpb.diagnosticos.exames.tipos;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.modelo.Paciente;
import br.ifpb.diagnosticos.enums.TipoExame;

/**
 * Implementação concreta de exame de Ressonância Magnética
 */
public class Ressonancia extends Exame {
    
    public Ressonancia(Paciente paciente, double valor) {
        super(paciente, valor);
        this.tipoExame = TipoExame.RESSONANCIA;
        this.dados.put("regiao", "Joelho direito");
        this.dados.put("sequencias", "T1, T2, FLAIR");
        this.dados.put("contraste", false);
        this.dados.put("laudo_tecnico", "Estruturas ósseas preservadas");
        this.dados.put("impressao", "Exame normal");

    }
}
