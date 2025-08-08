package br.ifpb.diagnosticos.laudos;

/**
 * Classe principal para gerenciar observações usando Memento
 */
public class Observacao {
    private String texto;
    
    public Observacao(String texto) {
        this.texto = texto;
    }
    
    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public String getTexto() {
        return texto;
    }
    
    public ObservacaoMemento criarMemento() {
        return new ObservacaoMemento(this.texto);
    }
    
    public void restaurar(ObservacaoMemento memento) {
        this.texto = memento.getEstado();
    }
}
