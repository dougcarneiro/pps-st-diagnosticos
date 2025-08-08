package br.ifpb.diagnosticos.exames.criadores;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.modelo.Paciente;

/**
 * Classe abstrata para criadores de exame (Factory Method)
 */
public abstract class CriadorExame {
    public abstract Exame criarExame(Paciente paciente, double valor);
}
