package br.ifpb.diagnosticos.enums;

/**
 * Enum para definir as prioridades dos exames
 */
public enum Prioridade {
    EMERGENCIA(1),
    MUITO_URGENTE(2),
    URGENTE(3),
    POUCO_URGENTE(4),
    ROTINA(5);
    
    private final int nivel;
    
    Prioridade(int nivel) {
        this.nivel = nivel;
    }
    
    public int getNivel() {
        return nivel;
    }
}
