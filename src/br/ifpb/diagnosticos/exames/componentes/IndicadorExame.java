package br.ifpb.diagnosticos.exames.componentes;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.financeiro.DescontoStrategy;
import java.util.Map;
import java.util.HashMap;

/**
 * Classe abstrata base para indicadores de exame (Decorator Pattern)
 */
public abstract class IndicadorExame extends Exame {
    protected Exame exameBase;
    private boolean exameBaseExecutado = false;
    
    public IndicadorExame(Exame exameBase) {
        super(exameBase.getPaciente(), exameBase.getValor());
        this.exameBase = exameBase;
        this.codigo = exameBase.getCodigo();
    }
    
    @Override
    public void setDescontoStrategy(DescontoStrategy descontoStrategy) {
        this.descontoStrategy = descontoStrategy;
        exameBase.setDescontoStrategy(descontoStrategy);
    }
    
    @Override
    public double aplicarDesconto(double valor) {
        return exameBase.aplicarDesconto(valor);
    }
    
    @Override
    protected void prepararPaciente() {
        if (!exameBaseExecutado) {
            exameBase.realizarExame();
            exameBaseExecutado = true;
        }
    }
    
    @Override
    protected void realizarProcedimento() {
        realizarAnaliseIndicador();
    }
    
    @Override
    protected void finalizarExame() {
        System.out.println("Análise do indicador " + getNomeIndicador() + " concluída");
    }
    
    @Override
    public void setDados(Map<String, Object> dados) {
        exameBase.setDados(dados);
    }
    
    @Override
    public Map<String, Object> getDados() {
        Map<String, Object> dados = exameBase.getDados();
        if (dados == null) {
            dados = new HashMap<>();
            exameBase.setDados(dados);
        }
        adicionarDadosIndicador(dados);
        return dados;
    }
    
    protected abstract void realizarAnaliseIndicador();
    protected abstract void adicionarDadosIndicador(Map<String, Object> dados);
    public abstract String getNomeIndicador();
    public abstract String getUnidadeMedida();
    public abstract String getValorReferencia();
}
