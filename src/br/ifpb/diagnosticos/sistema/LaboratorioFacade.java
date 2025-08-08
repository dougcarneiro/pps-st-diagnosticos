package br.ifpb.diagnosticos.sistema;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.exames.criadores.*;
import br.ifpb.diagnosticos.gestao.FilaPrioridadeExames;
import br.ifpb.diagnosticos.modelo.Paciente;
import br.ifpb.diagnosticos.modelo.Prioridade;
import br.ifpb.diagnosticos.financeiro.DescontoStrategy;
import br.ifpb.diagnosticos.laudos.Laudo;
import br.ifpb.diagnosticos.laudos.tipos.*;
import br.ifpb.diagnosticos.laudos.formatos.*;
import br.ifpb.diagnosticos.notificacao.*;
import br.ifpb.diagnosticos.validacao.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Facade para simplificar a interface do sistema de laboratório
 */
public class LaboratorioFacade {
    private FilaPrioridadeExames filaExames;
    private Map<String, CriadorExame> criadores;
    
    public LaboratorioFacade() {
        this.filaExames = new FilaPrioridadeExames();
        this.criadores = new HashMap<>();
        
        // Registrar criadores de exame
        criadores.put("HEMOGRAMA", new CriadorHemograma());
        criadores.put("ULTRASSONOGRAFIA", new CriadorUltrassonografia());
        criadores.put("RESSONANCIA", new CriadorRessonanciaMagnetica());
    }
    
    public Exame solicitarExame(Paciente paciente, String tipoExame, 
                               Prioridade prioridade, DescontoStrategy desconto, double valor) {
        
        // Criar o exame usando Factory Method
        CriadorExame criador = criadores.get(tipoExame.toUpperCase());
        if (criador == null) {
            System.out.println("Tipo de exame não suportado: " + tipoExame);
            return null;
        }
        
        Exame exame = criador.criarExame(paciente, valor);
        
        // Aplicar desconto se fornecido
        if (desconto != null) {
            exame.setDescontoStrategy(desconto);
            double valorComDesconto = exame.aplicarDesconto(valor);
            System.out.println("Valor original: R$ " + valor + " - Valor com desconto: R$ " + valorComDesconto);
        }
        
        // Adicionar à fila de prioridade
        filaExames.adicionarExame(exame, prioridade);
        
        System.out.println("Exame " + tipoExame + " solicitado para " + paciente.getNome());
        return exame;
    }
    
    public void processarProximoExame() {
        Exame exame = filaExames.proximoExame();
        if (exame != null) {
            System.out.println("Processando exame...");
            exame.realizarExame();
        } else {
            System.out.println("Não há exames na fila");
        }
    }
    
    public String gerarLaudo(Exame exame, String formato, Map<String, Object> dados) {
        // Configurar dados do exame
        exame.setDados(dados);
        
        // Escolher formato
        FormatoLaudo formatoLaudo;
        switch (formato.toUpperCase()) {
            case "PDF":
                formatoLaudo = new PDF();
                break;
            case "HTML":
                formatoLaudo = new HTML();
                break;
            case "TEXTO":
            default:
                formatoLaudo = new Texto();
                break;
        }
        
        // Criar laudo específico baseado no tipo de exame
        Laudo laudo;
        String tipoExame = exame.getClass().getSimpleName();
        switch (tipoExame) {
            case "Hemograma":
                laudo = new LaudoHemograma(formatoLaudo, exame);
                break;
            case "Ultrassonografia":
                laudo = new LaudoUltrassonografia(formatoLaudo, exame);
                break;
            case "Ressonancia":
                laudo = new LaudoRessonanciaMagnetica(formatoLaudo, exame);
                break;
            default:
                System.out.println("Tipo de laudo não suportado");
                return null;
        }
        
        // Configurar notificações (Observer)
        laudo.adicionarObservador(new EmailNotificador(exame.getPaciente().getNome() + "@email.com"));
        laudo.adicionarObservador(new SmsNotificador("(11) 99999-9999"));
        
        // Configurar validação (Chain of Responsibility)
        Validador validador = criarCadeiaValidacao(tipoExame);
        laudo.configurarValidacao(validador);
        
        // Validar dados antes de gerar laudo
        if (validador != null && !validador.validar(dados)) {
            return "Erro na validação dos dados do exame";
        }
        
        return laudo.gerarLaudo();
    }
    
    private Validador criarCadeiaValidacao(String tipoExame) {
        switch (tipoExame) {
            case "Hemograma":
                return new ValidacaoHemograma();
            case "Ultrassonografia":
                return new ValidacaoUltrassonografia();
            case "Ressonancia":
                return new ValidacaoRessonancia();
            default:
                return null;
        }
    }
    
    public void exibirFilaExames() {
        filaExames.exibirFila();
    }
    
    public boolean temExamesNaFila() {
        return filaExames.temExames();
    }
    
    public int getTamanhoFila() {
        return filaExames.getTamanho();
    }
}
