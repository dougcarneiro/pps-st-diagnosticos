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
    
    @Override
    protected void prepararPaciente() {
        System.out.println("Preparando paciente para ressonância magnética");
        System.out.println("Verificando contraindicações (marcapasso, implantes metálicos)");
        System.out.println("Orientando sobre a permanência imóvel durante o exame");
    }
    
    @Override
    protected void realizarProcedimento() {
        System.out.println("Realizando exame de ressonância magnética");
        System.out.println("Capturando imagens detalhadas através de campo magnético");
    }
}
