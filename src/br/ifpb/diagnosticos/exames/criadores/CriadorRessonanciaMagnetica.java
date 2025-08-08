package br.ifpb.diagnosticos.exames.criadores;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.exames.tipos.Ressonancia;
import br.ifpb.diagnosticos.modelo.Paciente;

/**
 * Criador concreto para exames de Ressonância Magnética
 */
public class CriadorRessonanciaMagnetica extends CriadorExame {
    
    @Override
    public Exame criarExame(Paciente paciente, double valor) {
        return new Ressonancia(paciente, valor);
    }
}
