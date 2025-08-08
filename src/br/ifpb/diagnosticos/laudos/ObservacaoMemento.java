package br.ifpb.diagnosticos.laudos;

/**
 * Memento para armazenar estado das observações
 */
public class ObservacaoMemento {
    private final String estado;
    
    public ObservacaoMemento(String estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        return estado;
    }
}
