package br.ifpb.diagnosticos.financeiro;

/**
 * Implementação de desconto para convênio (15%)
 */
public class DescontoConvenio implements DescontoStrategy {
    private static final double PERCENTUAL_DESCONTO = 0.15;
    
    @Override
    public double calcularDesconto(double valor) {
        return valor - (valor * PERCENTUAL_DESCONTO);
    }
}
