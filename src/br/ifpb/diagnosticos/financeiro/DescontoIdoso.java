package br.ifpb.diagnosticos.financeiro;

import br.ifpb.diagnosticos.utils.ConfiguracaoSistema;

/**
 * Implementação de desconto para idoso (configurável via sistema)
 */
public class DescontoIdoso implements DescontoStrategy {
    private final ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
    
    @Override
    public double calcularDesconto(double valor) {
        double percentual = config.getDescontoIdoso() / 100.0;
        return valor - (valor * percentual);
    }
}
