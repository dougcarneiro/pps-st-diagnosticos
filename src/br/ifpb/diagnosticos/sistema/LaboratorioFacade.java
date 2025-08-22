package br.ifpb.diagnosticos.sistema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ifpb.diagnosticos.enums.Prioridade;
import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.exames.componentes.hemograma.IndicadorColesterol;
import br.ifpb.diagnosticos.exames.componentes.hemograma.IndicadorCreatinina;
import br.ifpb.diagnosticos.exames.componentes.hemograma.IndicadorGlicemia;
import br.ifpb.diagnosticos.exames.criadores.CriadorExame;
import br.ifpb.diagnosticos.exames.criadores.CriadorHemograma;
import br.ifpb.diagnosticos.exames.criadores.CriadorRessonanciaMagnetica;
import br.ifpb.diagnosticos.exames.criadores.CriadorUltrassonografia;
import br.ifpb.diagnosticos.financeiro.DescontoStrategy;
import br.ifpb.diagnosticos.gestao.FilaPrioridadeExames;
import br.ifpb.diagnosticos.laudos.Laudo;
import br.ifpb.diagnosticos.laudos.formatos.FormatoLaudo;
import br.ifpb.diagnosticos.laudos.formatos.HTML;
import br.ifpb.diagnosticos.laudos.formatos.PDF;
import br.ifpb.diagnosticos.laudos.formatos.Texto;
import br.ifpb.diagnosticos.laudos.tipos.LaudoHemograma;
import br.ifpb.diagnosticos.laudos.tipos.LaudoRessonanciaMagnetica;
import br.ifpb.diagnosticos.laudos.tipos.LaudoUltrassonografia;
import br.ifpb.diagnosticos.modelo.Medico;
import br.ifpb.diagnosticos.modelo.Paciente;
import br.ifpb.diagnosticos.notificacao.EmailNotificador;
import br.ifpb.diagnosticos.notificacao.SmsNotificador;
import br.ifpb.diagnosticos.validacao.ValidacaoColesterol;
import br.ifpb.diagnosticos.validacao.ValidacaoCreatinina;
import br.ifpb.diagnosticos.validacao.ValidacaoGlicemia;
import br.ifpb.diagnosticos.validacao.ValidacaoHemograma;
import br.ifpb.diagnosticos.validacao.ValidacaoRessonancia;
import br.ifpb.diagnosticos.validacao.ValidacaoUltrassonografia;
import br.ifpb.diagnosticos.validacao.Validador;

/**
 * Facade para simplificar a interface do sistema de laboratório
 */
public class LaboratorioFacade {
    private FilaPrioridadeExames filaExames;
    private Map<String, CriadorExame> criadores;
    private List<Exame> examesProcessados; // Lista para armazenar exames processados
    private List<Medico> medicosDisponiveis; // Lista de médicos disponíveis
    
    public LaboratorioFacade() {
        this.filaExames = new FilaPrioridadeExames();
        this.criadores = new HashMap<>();
        this.examesProcessados = new ArrayList<>();
        this.medicosDisponiveis = new ArrayList<>();
        
        // Registrar criadores de exame
        criadores.put("HEMOGRAMA", new CriadorHemograma());
        criadores.put("ULTRASSONOGRAFIA", new CriadorUltrassonografia());
        criadores.put("RESSONANCIA", new CriadorRessonanciaMagnetica());
    }
    
    /**
     * Define a lista de médicos disponíveis no laboratório
     */
    public void setMedicosDisponiveis(List<Medico> medicos) {
        this.medicosDisponiveis = medicos;
    }
    
    /**
     * Seleciona um médico baseado no tipo de exame
     */
    private Medico selecionarMedicoParaExame(String tipoExame) {
        if (medicosDisponiveis.isEmpty()) {
            return null;
        }
        
        // Lógica para selecionar médico baseado no tipo de exame
        for (Medico medico : medicosDisponiveis) {
            switch (tipoExame.toUpperCase()) {
                case "HEMOGRAMA":
                    if (medico.getEspecialidade().contains("Hematologista") || 
                        medico.getEspecialidade().contains("Clínico Geral")) {
                        return medico;
                    }
                    break;
                case "RESSONANCIA":
                    if (medico.getEspecialidade().contains("Radiologista")) {
                        return medico;
                    }
                    break;
                case "ULTRASSONOGRAFIA":
                    if (medico.getEspecialidade().contains("Ultrassonografista") ||
                        medico.getEspecialidade().contains("Radiologista")) {
                        return medico;
                    }
                    break;
            }
        }
        
        // Se não encontrar especialista, retorna o primeiro médico disponível
        return medicosDisponiveis.get(0);
    }
    
    public Exame solicitarExame(Paciente paciente, String tipoExame, 
                               Prioridade prioridade, DescontoStrategy desconto, double valor) {
        return solicitarExame(paciente, tipoExame, prioridade, desconto, valor, null);
    }
    
    public Exame solicitarExame(Paciente paciente, String tipoExame, 
                               Prioridade prioridade, DescontoStrategy desconto, double valor, String[] indicadores) {
        
        Exame exame;
        
        // Verificar se é um exame sanguíneo com indicadores
        if (indicadores != null && indicadores.length > 0) {
            // Criar exame base hemograma
            CriadorExame criador = criadores.get("HEMOGRAMA");
            if (criador == null) {
                System.out.println("Criador de hemograma não encontrado");
                return null;
            }
            
            Exame exameBase = criador.criarExame(paciente, valor);
            
            // Aplicar decorators baseado nos indicadores solicitados
            exame = exameBase;
            for (String indicador : indicadores) {
                switch (indicador.toUpperCase()) {
                    case "GLICEMIA":
                        exame = new IndicadorGlicemia(exame);
                        break;
                    case "COLESTEROL":
                        exame = new IndicadorColesterol(exame);
                        break;
                    case "CREATININA":
                        exame = new IndicadorCreatinina(exame);
                        break;
                    default:
                        System.out.println("Indicador não reconhecido: " + indicador);
                        break;
                }
            }
            
            String listaIndicadores = String.join(", ", indicadores);
            System.out.println("Hemograma com indicadores (" + listaIndicadores + ") solicitado para " + paciente.getNome());
            
        } else {
            // Criar exame normal usando Factory Method
            CriadorExame criador = criadores.get(tipoExame.toUpperCase());
            if (criador == null) {
                System.out.println("Tipo de exame não suportado: " + tipoExame);
                return null;
            }
            
            exame = criador.criarExame(paciente, valor);
            System.out.println("Exame " + tipoExame + " solicitado para " + paciente.getNome());
        }
        
        // Associar médico solicitante
        String tipoExameParaMedico = (indicadores != null && indicadores.length > 0) ? "HEMOGRAMA" : tipoExame;
        Medico medicoSolicitante = selecionarMedicoParaExame(tipoExameParaMedico);
        if (medicoSolicitante != null) {
            exame.setMedicoSolicitante(medicoSolicitante);
        }
        
        // Aplicar desconto se fornecido
        if (desconto != null) {
            exame.setDescontoStrategy(desconto);
            double valorComDesconto = exame.aplicarDesconto(valor);
            System.out.println("Valor original: R$ " + valor + " - Valor com desconto: R$ " + valorComDesconto);
        }
        
        // Adicionar à fila de prioridade
        filaExames.adicionarExame(exame, prioridade);
        
        return exame;
    }
    
    public void processarProximoExame() {
        Exame exame = filaExames.proximoExame();
        if (exame != null) {
            System.out.println("Processando exame...");
            // Adicionar à lista de exames processados
            examesProcessados.add(exame);
        } else {
            System.out.println("Não há exames na fila");
        }
    }
    
    public String gerarLaudo(Exame exame, String formato) {
        
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
        switch (exame.getTipoExame()) {
            case HEMOGRAMA:
                // Todos os exames sanguíneos (hemograma base ou com indicadores) usam o mesmo tipo de laudo
                laudo = new LaudoHemograma(formatoLaudo, exame);
                break;
            case ULTRASSONOGRAFIA:
                laudo = new LaudoUltrassonografia(formatoLaudo, exame);
                break;
            case RESSONANCIA:
                laudo = new LaudoRessonanciaMagnetica(formatoLaudo, exame);
                break;
            default:
                System.out.println("Tipo de laudo não suportado");
                return null;
        }
        
        // Configurar notificações (Observer)
        laudo.adicionarObservador(new EmailNotificador(exame.getPaciente().getEmail()));
        laudo.adicionarObservador(new SmsNotificador("(11) 99999-9999"));

        // Configurar validação (Chain of Responsibility)
        Validador validador = criarCadeiaValidacao();
        
        // Validar dados antes de gerar laudo
        if (validador != null && !validador.validar(exame.getDados(), exame.getTipoExame())) {
            return "Erro na validação dos dados do exame";
        }
        
        return laudo.gerarLaudo();
    }

    private Validador criarCadeiaValidacao() {
        // Validações dos tipos de exame
        Validador handlerHemograma = new ValidacaoHemograma();
        Validador handlerUltrasom = new ValidacaoUltrassonografia();
        Validador handlerRessonancia = new ValidacaoRessonancia();
        
        // Validações específicas dos indicadores
        Validador handlerGlicemia = new ValidacaoGlicemia();
        Validador handlerColesterol = new ValidacaoColesterol();
        Validador handlerCreatinina = new ValidacaoCreatinina();
        
        // Configurar cadeia: tipos de exame -> indicadores específicos
        handlerHemograma
            .setProximo(handlerGlicemia)
            .setProximo(handlerColesterol)
            .setProximo(handlerCreatinina)
            .setProximo(handlerUltrasom)
            .setProximo(handlerRessonancia);

        return handlerHemograma;
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
    
    public List<Exame> getExamesProcessados() {
        return new ArrayList<>(examesProcessados);
    }
}
