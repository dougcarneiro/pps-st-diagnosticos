package br.ifpb.diagnosticos.financeiro;

/**
 * Interface Strategy para diferentes tipos de desconto
 */
public interface DescontoStrategy {
    double calcularDesconto(double valor);
}
