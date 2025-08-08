package br.ifpb.diagnosticos.exames.criadores;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.exames.tipos.Hemograma;
import br.ifpb.diagnosticos.modelo.Paciente;

/**
 * Criador concreto para exames de Hemograma
 */
public class CriadorHemograma extends CriadorExame {
    
    @Override
    public Exame criarExame(Paciente paciente, double valor) {
        return new Hemograma(paciente, valor);
    }
}
