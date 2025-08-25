package br.ifpb.diagnosticos.exames;

import br.ifpb.diagnosticos.modelo.Paciente;
import br.ifpb.diagnosticos.modelo.Medico;
import br.ifpb.diagnosticos.financeiro.DescontoStrategy;
import br.ifpb.diagnosticos.utils.GeradorNumeroExame;
import br.ifpb.diagnosticos.enums.TipoExame;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

/**
 * Classe abstrata base para todos os exames (Template Method)
 */
public abstract class Exame {
    protected int codigo;
    protected DescontoStrategy descontoStrategy;
    protected double valor;
    protected Paciente paciente;
    protected Medico medicoSolicitante;
    protected Date dataImplantacao;
    protected Map<String, Object> dados = new HashMap<>();
    protected TipoExame tipoExame;

    public Exame(Paciente paciente, double valor) {
        this.paciente = paciente;
        this.valor = valor;
        this.codigo = GeradorNumeroExame.getInstance().gerarNumero();
        this.dataImplantacao = new Date(); // Data de criação do exame
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

    public TipoExame getTipoExame() {
        return tipoExame;
    }
    
    public Medico getMedicoSolicitante() {
        return medicoSolicitante;
    }
    
    public void setMedicoSolicitante(Medico medicoSolicitante) {
        this.medicoSolicitante = medicoSolicitante;
    }
    
    public Date getDataImplantacao() {
        return dataImplantacao;
    }

    @Override
    public String toString() {
        StringBuilder dados = new StringBuilder();
        dados.append("Exame Código: ").append(codigo).append("\n");
        dados.append("Paciente: ").append(paciente.getNome()).append("\n");
        dados.append("Médico Solicitante: ").append(medicoSolicitante.getNome()).append("\n");
        dados.append("Data de Implantação: ").append(dataImplantacao).append("\n");
        dados.append("Tipo de Exame: ").append(tipoExame).append("\n");
        dados.append("Valor: R$ ").append(String.format("%.2f", valor)).append("\n");
    
        return dados.toString();
    }
}
