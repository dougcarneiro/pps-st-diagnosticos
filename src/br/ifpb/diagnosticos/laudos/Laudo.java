package br.ifpb.diagnosticos.laudos;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.laudos.formatos.FormatoLaudo;
import br.ifpb.diagnosticos.validacao.Validador;
import br.ifpb.diagnosticos.notificacao.Observador;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

            // Verificar se o valor é um Map (indicador estruturado)
            if (valor instanceof Map) {
                try {
                    Map<String, Object> detalhes = (Map<String, Object>) valor;
                    
                    // Verificar se tem a estrutura esperada de um indicador
                    if (detalhes.containsKey("valor")) {
                        dados.append("=== ").append(chave.toUpperCase()).append(" ===\n");
                        
                        Object valorIndicador = detalhes.get("valor");
                        if (valorIndicador instanceof Number) {
                            // Formatar números com 2 casas decimais
                            double valorNum = ((Number) valorIndicador).doubleValue();
                            dados.append("Valor: ").append(String.format("%.2f", valorNum));
                        } else {
                            dados.append("Valor: ").append(valorIndicador);
                        }
                        
                        // Adicionar unidade se disponível
                        if (detalhes.containsKey("unidade")) {
                            dados.append(" ").append(detalhes.get("unidade"));
                        }
                        dados.append("\n");
                        
                        // Adicionar referência se disponível
                        if (detalhes.containsKey("referencia")) {
                            dados.append("Referência: ").append(detalhes.get("referencia")).append("\n");
                        }
                        
                        // Adicionar status se disponível
                        if (detalhes.containsKey("status")) {
                            dados.append("Status: ").append(detalhes.get("status")).append("\n");
                        }
                        
                        dados.append("\n");
                    }
                } catch (ClassCastException e) {
                    // Se não conseguir fazer cast para Map, ignorar silenciosamente
                    System.err.println("Aviso: Não foi possível processar indicador: " + chave);
                }
            } else {
                // Para dados simples (não estruturados), exibir diretamente
                dados.append(chave).append(": ").append(valor).append("\n");
            }
        }
        return dados.toString();
    }

    
    // Getters e Setters
    public Observacao getObservacao() { return observacao; }
    public void setObservacao(Observacao observacao) { this.observacao = observacao; }
    public Exame getExame() { return exame; }
}
