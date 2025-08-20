package br.ifpb.diagnosticos.laudos;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.laudos.formatos.FormatoLaudo;
import br.ifpb.diagnosticos.validacao.Validador;
import br.ifpb.diagnosticos.notificacao.Observador;
import br.ifpb.diagnosticos.utils.FormatadorTexto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    protected String gerarDadosExame(StringBuilder dados) {
        for (Map.Entry<String, Object> entry : this.exame.getDados().entrySet()) {
            String chave = entry.getKey();
            Object valor = entry.getValue();
            
            // Se o valor for um Map, formatá-lo de forma legível com quebras de linha
            if (valor instanceof Map) {
                try {
                    Map<String, Object> mapaValor = (Map<String, Object>) valor;
                    
                    dados.append(FormatadorTexto.formatarTitleCase(chave)).append(":\n");
                    for (Map.Entry<String, Object> entryInterna : mapaValor.entrySet()) {
                        dados.append("  ").append(FormatadorTexto.formatarTitleCase(entryInterna.getKey()))
                             .append(": ").append(entryInterna.getValue()).append("\n");
                    }
                    dados.append("\n");
                } catch (ClassCastException e) {
                    // Em caso de erro no cast, usar toString padrão
                    dados.append(FormatadorTexto.formatarTitleCase(chave)).append(": ").append(valor).append("\n");
                }
            } else {
                // Para valores simples, imprimir diretamente
                dados.append(FormatadorTexto.formatarTitleCase(chave)).append(": ").append(valor).append("\n");
            }
        }
        return dados.toString();
    }
    
    // Getters e Setters
    public Observacao getObservacao() { return observacao; }
    public void setObservacao(Observacao observacao) { this.observacao = observacao; }
    public Exame getExame() { return exame; }
}
