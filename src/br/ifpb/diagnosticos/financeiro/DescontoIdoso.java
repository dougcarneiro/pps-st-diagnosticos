package br.ifpb.diagnosticos.financeiro;

/**
 * Implementação de desconto para idoso (8%)
 */
public class DescontoIdoso implements DescontoStrategy {
    private static final double PERCENTUAL_DESCONTO = 0.08;
    
    @Override
    public double calcularDesconto(double valor) {
        return valor - (valor * PERCENTUAL_DESCONTO);
    }
}
