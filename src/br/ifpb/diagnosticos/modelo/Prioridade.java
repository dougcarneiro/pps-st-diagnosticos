package br.ifpb.diagnosticos.modelo;

/**
 * Enum para definir as prioridades dos exames
 */
public enum Prioridade {
    URGENTE(1),
    POUCO_URGENTE(2),
    ROTINA(3);
    
    private final int nivel;
    
    Prioridade(int nivel) {
        this.nivel = nivel;
    }
    
    public int getNivel() {
        return nivel;
    }
}
