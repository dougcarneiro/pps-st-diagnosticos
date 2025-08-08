package br.ifpb.diagnosticos.gestao;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.modelo.Prioridade;
import java.util.*;

/**
 * Fila de prioridade para exames
 */
public class FilaPrioridadeExames {
    private List<ExameComPrioridade> fila;
    
    public FilaPrioridadeExames() {
        this.fila = new ArrayList<>();
    }
    
    public void adicionarExame(Exame exame, Prioridade prioridade) {
        ExameComPrioridade exameComPrioridade = new ExameComPrioridade(exame, prioridade);
        
        switch (prioridade) {
            case URGENTE:
                // Adiciona no início da fila
                fila.add(0, exameComPrioridade);
                break;
                
            case POUCO_URGENTE:
                // Adiciona após o último exame urgente
                int posicao = encontrarUltimaPosicaoUrgente();
                fila.add(posicao, exameComPrioridade);
                break;
                
            case ROTINA:
                // Adiciona no final da fila
                fila.add(exameComPrioridade);
                break;
        }
        
        System.out.println("Exame adicionado à fila com prioridade: " + prioridade);
    }
    
    public Exame proximoExame() {
        if (!fila.isEmpty()) {
            ExameComPrioridade proximoExameComPrioridade = fila.remove(0);
            System.out.println("Próximo exame: " + proximoExameComPrioridade.getExame().getClass().getSimpleName() + 
                             " (Prioridade: " + proximoExameComPrioridade.getPrioridade() + ")");
            return proximoExameComPrioridade.getExame();
        }
        return null;
    }
    
    public boolean temExames() {
        return !fila.isEmpty();
    }
    
    public int getTamanho() {
        return fila.size();
    }
    
    public List<ExameComPrioridade> getFila() {
        return new ArrayList<>(fila);
    }
    
    private int encontrarUltimaPosicaoUrgente() {
        for (int i = 0; i < fila.size(); i++) {
            if (fila.get(i).getPrioridade() != Prioridade.URGENTE) {
                return i;
            }
        }
        return fila.size();
    }
    
    public void exibirFila() {
        System.out.println("\n=== FILA DE EXAMES ===");
        for (int i = 0; i < fila.size(); i++) {
            ExameComPrioridade item = fila.get(i);
            System.out.println((i + 1) + ". " + item.getExame().getClass().getSimpleName() + 
                             " - Paciente: " + item.getExame().getPaciente().getNome() + 
                             " - Prioridade: " + item.getPrioridade());
        }
        System.out.println("==================\n");
    }
    
    // Classe interna para encapsular exame com prioridade
    public static class ExameComPrioridade {
        private Exame exame;
        private Prioridade prioridade;
        
        public ExameComPrioridade(Exame exame, Prioridade prioridade) {
            this.exame = exame;
            this.prioridade = prioridade;
        }
        
        public Exame getExame() {
            return exame;
        }
        
        public Prioridade getPrioridade() {
            return prioridade;
        }
    }
}
