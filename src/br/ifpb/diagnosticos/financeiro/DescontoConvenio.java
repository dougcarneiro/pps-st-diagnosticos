package br.ifpb.diagnosticos.financeiro;

import br.ifpb.diagnosticos.utils.ConfiguracaoSistema;

/**
 * Implementação de desconto para convênio (configurável via sistema)
 */
public class DescontoConvenio implements DescontoStrategy {
    private final ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
    
    @Override
    public double calcularDesconto(double valor) {
        double percentual = config.getDescontoConvenio() / 100.0;
        return valor - (valor * percentual);
    }
}
