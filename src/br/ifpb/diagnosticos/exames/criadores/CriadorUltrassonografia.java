package br.ifpb.diagnosticos.exames.criadores;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.exames.tipos.Ultrassonografia;
import br.ifpb.diagnosticos.modelo.Paciente;

/**
 * Criador concreto para exames de Ultrassonografia
 */
public class CriadorUltrassonografia extends CriadorExame {
    
    @Override
    public Exame criarExame(Paciente paciente, double valor) {
        return new Ultrassonografia(paciente, valor);
    }
}
