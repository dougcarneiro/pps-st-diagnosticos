package br.ifpb.diagnosticos.utils;

/**
 * Padrão Singleton para gerar números sequenciais únicos para exames
 */
public class GeradorNumeroExame {
    private static GeradorNumeroExame instance;
    private int numeroAtual;
    
    private GeradorNumeroExame() {
        this.numeroAtual = 0;
    }
    
    public static GeradorNumeroExame getInstance() {
        if (instance == null) {
            instance = new GeradorNumeroExame();
        }
        return instance;
    }
    
    public int gerarNumero() {
        return ++numeroAtual;
    }
}
