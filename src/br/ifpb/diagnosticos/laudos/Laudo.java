package br.ifpb.diagnosticos.laudos;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.laudos.formatos.FormatoLaudo;
import br.ifpb.diagnosticos.validacao.Validador;
import br.ifpb.diagnosticos.notificacao.Observador;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe abstrata para laudos (Bridge pattern)
 */
public abstract class Laudo {
    protected FormatoLaudo formato;
    protected Exame exame;
    protected Observacao observacao;
    protected Validador validadorInicial;
    protected List<Observador> observadores;
    
    public Laudo(FormatoLaudo formato, Exame exame) {
        this.formato = formato;
        this.exame = exame;
        this.observacao = new Observacao("");
        this.observadores = new ArrayList<>();
    }
    
    // Observer Pattern - adicionar observador
    public void adicionarObservador(Observador observador) {
        observadores.add(observador);
    }
    
    // Observer Pattern - remover observador
    public void removerObservador(Observador observador) {
        observadores.remove(observador);
    }
    
    // Observer Pattern - notificar observadores
    protected void notificarObservadores(String evento) {
        for (Observador observador : observadores) {
            observador.atualizar(evento);
        }
    }
    
    // Chain of Responsibility - configurar cadeia de validação
    public void configurarValidacao(Validador validador) {
        this.validadorInicial = validador;
    }
    
    // Template method para geração do laudo
    public final String gerarLaudo() {
        StringBuilder conteudo = new StringBuilder();
        
        // Passo 1: Cabeçalho
        conteudo.append(gerarCabecalho());
        
        // Passo 2: Dados do paciente
        conteudo.append(gerarDadosPaciente());
        
        // Passo 3: Dados do exame (implementado pelas subclasses)
        conteudo.append(gerarDadosExame());
        
        // Passo 4: Observações
        conteudo.append(gerarObservacoes());
        
        // Passo 5: Rodapé
        conteudo.append(gerarRodape());
        
        // Aplicar formato
        String laudoFormatado = formato.formatar(conteudo.toString());
        
        // Notificar observadores
        notificarObservadores("Laudo gerado para paciente: " + exame.getPaciente().getNome());
        
        return laudoFormatado;
    }
    
    protected String gerarCabecalho() {
        return "=== LABORATÓRIO ST DIAGNÓSTICOS ===\n" +
               "Data: " + new Date() + "\n" +
               "Número do Exame: " + exame.getNumeroExame() + "\n\n";
    }
    
    protected String gerarDadosPaciente() {
        return "DADOS DO PACIENTE:\n" +
               "Nome: " + exame.getPaciente().getNome() + "\n" +
               "Idade: " + exame.getPaciente().getIdade() + "\n" +
               "Convênio: " + exame.getPaciente().getConvenio() + "\n\n";
    }
    
    protected String gerarObservacoes() {
        return "OBSERVAÇÕES:\n" + observacao.getTexto() + "\n\n";
    }
    
    protected String gerarRodape() {
        return "--- Fim do Laudo ---\n";
    }
    
    // Método abstrato - cada tipo de laudo implementa
    protected abstract String gerarDadosExame();
    
    // Getters e Setters
    public Observacao getObservacao() { return observacao; }
    public void setObservacao(Observacao observacao) { this.observacao = observacao; }
    public Exame getExame() { return exame; }
}
