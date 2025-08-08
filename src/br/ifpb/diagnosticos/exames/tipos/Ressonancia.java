package br.ifpb.diagnosticos.exames.tipos;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.modelo.Paciente;

/**
 * Implementação concreta de exame de Ressonância Magnética
 */
public class Ressonancia extends Exame {
    
    public Ressonancia(Paciente paciente, double valor) {
        super(paciente, valor);
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
