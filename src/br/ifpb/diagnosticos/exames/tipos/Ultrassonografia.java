package br.ifpb.diagnosticos.exames.tipos;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.modelo.Paciente;

/**
 * Implementação concreta de exame de Ultrassonografia
 */
public class Ultrassonografia extends Exame {
    
    public Ultrassonografia(Paciente paciente, double valor) {
        super(paciente, valor);
    }
    
    @Override
    protected void prepararPaciente() {
        System.out.println("Preparando paciente para ultrassonografia");
        System.out.println("Aplicando gel condutor na região a ser examinada");
    }
    
    @Override
    protected void realizarProcedimento() {
        System.out.println("Realizando exame de ultrassonografia");
        System.out.println("Capturando imagens em tempo real dos órgãos internos");
    }
}
