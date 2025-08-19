package br.ifpb.diagnosticos.exames;

import br.ifpb.diagnosticos.modelo.Paciente;
import br.ifpb.diagnosticos.financeiro.DescontoStrategy;
import br.ifpb.diagnosticos.utils.GeradorNumeroExame;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe abstrata base para todos os exames (Template Method)
 */
public abstract class Exame {
    protected int codigo;
    protected DescontoStrategy descontoStrategy;
    protected double valor;
    protected Paciente paciente;
    protected Map<String, Object> dados = new HashMap<>();
    
    public Exame(Paciente paciente, double valor) {
        this.paciente = paciente;
        this.valor = valor;
        this.codigo = GeradorNumeroExame.getInstance().gerarNumero();
    }
    
    public void setDescontoStrategy(DescontoStrategy descontoStrategy) {
        this.descontoStrategy = descontoStrategy;
    }
    
    public double aplicarDesconto(double valor) {
        if (descontoStrategy != null) {
            return descontoStrategy.calcularDesconto(valor);
        }
        return valor;
    }
    
    public final void realizarExame() {
        prepararPaciente();
        realizarProcedimento();
        finalizarExame();
    }
    
    protected abstract void prepararPaciente();
    protected abstract void realizarProcedimento();
    
    protected void finalizarExame() {
        System.out.println("Exame " + this.getClass().getSimpleName() + " finalizado para paciente: " + paciente.getNome());
    }
    
    public void setDados(Map<String, Object> dados) {
        this.dados = dados;
    }
    
    public Map<String, Object> getDados() {
        return dados;
    }
    
    // Getters
    public int getCodigo() {
        return codigo;
    }
    
    public double getValor() {
        return valor;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public String getNumeroExame() {
        return String.valueOf(codigo);
    }
}
