package br.ifpb.diagnosticos.laudos;

import java.util.ArrayList;
import java.util.List;

/**
 * Caretaker para gerenciar o histórico de observações (Memento Pattern)
 */
public class HistoricoObservacao {
    private List<ObservacaoMemento> historico = new ArrayList<>();
    
    public void salvar(ObservacaoMemento memento) {
        historico.add(memento);
    }
    
    public ObservacaoMemento restaurar(int indice) {
        if (indice >= 0 && indice < historico.size()) {
            return historico.get(indice);
        }
        return null;
    }
    
    public List<ObservacaoMemento> getHistorico() {
        return new ArrayList<>(historico);
    }
    
    public int getQuantidadeEstados() {
        return historico.size();
    }
}
