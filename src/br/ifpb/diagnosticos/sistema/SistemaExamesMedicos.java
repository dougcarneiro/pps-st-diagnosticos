package br.ifpb.diagnosticos.sistema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ifpb.diagnosticos.enums.Prioridade;
import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.financeiro.DescontoConvenio;
import br.ifpb.diagnosticos.financeiro.DescontoIdoso;
import br.ifpb.diagnosticos.laudos.Laudo;
import br.ifpb.diagnosticos.laudos.formatos.FormatoLaudo;
import br.ifpb.diagnosticos.laudos.formatos.Texto;
import br.ifpb.diagnosticos.modelo.Medico;
import br.ifpb.diagnosticos.modelo.Paciente;
import br.ifpb.diagnosticos.utils.CarregadorCSV;
import br.ifpb.diagnosticos.laudos.tipos.LaudoHemograma;
import br.ifpb.diagnosticos.laudos.tipos.LaudoRessonanciaMagnetica;
import br.ifpb.diagnosticos.laudos.tipos.LaudoUltrassonografia;

/**
 * Programa principal para demonstrar o sistema de exames médicos
 */
public class SistemaExamesMedicos {
    
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE EXAMES MÉDICOS - ST DIAGNÓSTICOS ===\n");
        
        // Criar facade do laboratório
        LaboratorioFacade laboratorio = new LaboratorioFacade();
        
        // Demonstrar carregamento de dados via CSV (Requisito R1)
        System.out.println("0. CARREGAMENTO DE DADOS");
        System.out.println("========================");
        
        // Criar arquivos de exemplo se não existirem
        CarregadorCSV.criarArquivoExemploPacientes("dados/pacientes.csv");
        CarregadorCSV.criarArquivoExemploMedicos("dados/medicos.csv");
        
        // Carregar dados dos arquivos CSV
        List<Paciente> pacientesCarregados = CarregadorCSV.carregarPacientes("dados/pacientes.csv");
        List<Medico> medicosCarregados = CarregadorCSV.carregarMedicos("dados/medicos.csv");
        
        // Exibir médicos carregados
        System.out.println("\nMédicos disponíveis:");
        for (Medico medico : medicosCarregados) {
            System.out.println("- " + medico);
        }
        
        // Configurar médicos no laboratório
        laboratorio.setMedicosDisponiveis(medicosCarregados);
        
        // Usar pacientes carregados + alguns criados manualmente
        Paciente paciente1 = pacientesCarregados.size() > 0 ? pacientesCarregados.get(0) 
                            : new Paciente("João Silva", "123.456.789-01", "Unimed", 45, "joao.silva@email.com");
        Paciente paciente2 = pacientesCarregados.size() > 1 ? pacientesCarregados.get(1) 
                            : new Paciente("Maria Santos", "987.654.321-02", "Bradesco Saúde", 72, "maria.santos@email.com");
        Paciente paciente3 = pacientesCarregados.size() > 2 ? pacientesCarregados.get(2) 
                            : new Paciente("Pedro Oliveira", "456.789.123-03", "Particular", 25, "pedro.oliveira@email.com");

        System.out.println("\n1. SOLICITAÇÃO DE EXAMES");
        System.out.println("========================");
        
        // Solicitar exames com diferentes prioridades e descontos
        Exame exame1 = laboratorio.solicitarExame(
            paciente1, medicosCarregados.get(0), "HEMOGRAMA", Prioridade.ROTINA, 
            new DescontoConvenio(), 80.0
        );
        
        Exame exame2 = laboratorio.solicitarExame(
            paciente2, medicosCarregados.get(1), "RESSONANCIA", Prioridade.URGENTE, 
            new DescontoIdoso(), 800.0
        );
        
        Exame exame3 = laboratorio.solicitarExame(
            paciente3, medicosCarregados.get(2), "ULTRASSONOGRAFIA", Prioridade.POUCO_URGENTE, 
            null, 150.0
        );
        
        // Outro exame para demonstrar fila
        laboratorio.solicitarExame(
            paciente1, medicosCarregados.get(0), "RESSONANCIA", Prioridade.URGENTE, 
            new DescontoConvenio(), 800.0
        );
        
        System.out.println("\n1.1. SOLICITAÇÃO DE EXAMES SANGUÍNEOS COM INDICADORES (Decorator Pattern)");
        System.out.println("=========================================================================");
        
        // Exame com apenas um indicador - Glicemia
        Exame exameGlicemia = laboratorio.solicitarExame(
            paciente1, medicosCarregados.get(0), "HEMOGRAMA", Prioridade.ROTINA, 
            new DescontoConvenio(), 60.0, new String[]{"GLICEMIA"}
        );
        
        // Exame com múltiplos indicadores - Colesterol + Creatinina
        Exame exameMultiplo1 = laboratorio.solicitarExame(
            paciente2, medicosCarregados.get(1), "HEMOGRAMA", Prioridade.POUCO_URGENTE, 
            new DescontoIdoso(), 120.0, new String[]{"COLESTEROL", "CREATININA"}
        );
        
        // Exame completo com todos os indicadores
        Exame exameCompleto = laboratorio.solicitarExame(
            paciente3, medicosCarregados.get(2), "HEMOGRAMA", Prioridade.URGENTE, 
            null, 180.0, new String[]{"GLICEMIA", "COLESTEROL", "CREATININA"}
        );
        
        System.out.println("\n2. FILA DE EXAMES (Priority Queue)");
        System.out.println("===================================");
        laboratorio.exibirFilaExames();
        
        System.out.println("\n3. PROCESSAMENTO DOS EXAMES (Template Method)");
        System.out.println("==============================================");
        
        // Processar todos os exames na fila
        while (laboratorio.temExamesNaFila()) {
            laboratorio.processarProximoExame();
            System.out.println();
        }
        
        System.out.println("\n4. GERAÇÃO DE LAUDOS (Bridge + Memento + Observer)");
        System.out.println("==================================================");
        
        // Gerar laudos com diferentes formatos
        Laudo laudoHemograma = laboratorio.gerarLaudo(exame1, "PDF");
        Laudo laudoRessonancia = laboratorio.gerarLaudo(exame2, "HTML");
        Laudo laudoUltrassom = laboratorio.gerarLaudo(exame3, "TEXTO");
        Laudo laudoGlicemia = laboratorio.gerarLaudo(exameGlicemia, "PDF");
        Laudo laudoMultiplo = laboratorio.gerarLaudo(exameMultiplo1, "HTML");
        Laudo laudoCompletoTxt = laboratorio.gerarLaudo(exameCompleto, "TEXTO");

        // Registrar observações para cada laudo
        laboratorio.adicionarObservacao(laudoHemograma, "Coleta realizada sem intercorrências.");
        laboratorio.adicionarObservacao(laudoHemograma, "Paciente em jejum adequado (8h).");
        laboratorio.adicionarObservacao(laudoRessonancia, "Paciente ansioso, exame tolerado.");
        laboratorio.adicionarObservacao(laudoRessonancia, "Sequências adicionais realizadas com contraste.");
        laboratorio.adicionarObservacao(laudoUltrassom, "Boa janela acústica, visualização clara das estruturas.");
        laboratorio.adicionarObservacao(laudoGlicemia, "Monitorar glicemia em 6 meses.");
        laboratorio.adicionarObservacao(laudoMultiplo, "Reavaliar perfil lipídico em 3 meses.");
        laboratorio.adicionarObservacao(laudoCompletoTxt, "Solicitar avaliação clínica detalhada se sintomas persistirem.");


        System.out.println("\n--- Laudo de Hemograma (PDF) ---");
        System.out.println(laboratorio.emitirLaudo(laudoHemograma));

        System.out.println("\n--- Laudo de Ressonância (HTML) ---");
        System.out.println(laboratorio.emitirLaudo(laudoRessonancia));

        System.out.println("\n--- Laudo de Ultrassonografia (Texto) ---");
        System.out.println(laboratorio.emitirLaudo(laudoUltrassom));
        
        System.out.println("\n--- Laudo de Exame Sanguíneo com Glicemia (PDF) ---");
        System.out.println(laboratorio.emitirLaudo(laudoGlicemia));

        System.out.println("\n--- Laudo de Exame Sanguíneo com Múltiplos Indicadores (HTML) ---");
        System.out.println(laboratorio.emitirLaudo(laudoMultiplo));

        System.out.println("\n--- Laudo de Exame Sanguíneo Completo (Texto) ---");
        System.out.println(laboratorio.emitirLaudo(laudoCompletoTxt));

        System.out.println("\n6. DEMONSTRAÇÃO DE HISTÓRICO DE OBSERVAÇÕES EM LAUDOS (Memento Pattern)");
        System.out.println("========================================================================");
        // Demonstrar o padrão Memento com histórico de observações nos laudos
        demonstrarHistoricoObservacoesEmLaudos(laboratorio.getExamesProcessados());
        
        System.out.println("\n5. DEMONSTRAÇÃO DE VALIDAÇÃO (Chain of Responsibility)");
        System.out.println("=======================================================");
        // Testar validação com dados realmente inválidos
        Map<String, Object> dadosInvalidos = new HashMap<>();
        
        System.out.println("\n--- Teste 1: Dados completamente vazios ---");
        System.out.println("DADOS ORIGINAIS: " + exame1.getDados());
        exame1.setDados(dadosInvalidos);
        System.out.println("DADOS VAZIOS: " + exame1.getDados());
        Laudo laudoVazio = laboratorio.gerarLaudo(exame1, "TEXTO");
        System.out.println("RESULTADO: " + laboratorio.emitirLaudo(laudoVazio));
        
        System.out.println("\n--- Teste 2: Dados com valores inválidos ---");
        Map<String, Object> dadosGlicemiaInvalida = new HashMap<>();
        // Simular como seria um indicador de glicemia real, mas com valor inválido
        Map<String, Object> glicemiaInvalida = new HashMap<>();
        glicemiaInvalida.put("valor", -50.0); // Valor negativo inválido
        glicemiaInvalida.put("unidade", "mg/dL");
        dadosGlicemiaInvalida.put("glicemia", glicemiaInvalida);
        
        exame1.setDados(dadosGlicemiaInvalida);
        System.out.println("DADOS COM GLICEMIA INVÁLIDA: " + exame1.getDados());
        Laudo laudoGlicemiaInvalida = laboratorio.gerarLaudo(exame1, "TEXTO");
        System.out.println("RESULTADO: " + laboratorio.emitirLaudo(laudoGlicemiaInvalida));

        Laudo laudoGlicemiaTexto = laboratorio.gerarLaudo(exame1, "TEXTO");
        String dadosGlicemiaTexto = laboratorio.emitirLaudo(laudoGlicemiaTexto);
        System.out.println("RESULTADO: " + dadosGlicemiaTexto);


        System.out.println("\n--- Teste 3: Dados com colesterol inválido ---");
        Map<String, Object> dadosColesterolInvalido = new HashMap<>();
        Map<String, Object> colesterolInvalido = new HashMap<>();
        colesterolInvalido.put("total", 1000.0); // Valor muito alto inválido
        dadosColesterolInvalido.put("colesterol", colesterolInvalido);
        
        exame1.setDados(dadosColesterolInvalido);
        System.out.println("DADOS COM COLESTEROL INVÁLIDO: " + exame1.getDados());
        Laudo laudoColesterolInvalido = laboratorio.gerarLaudo(exame1, "TEXTO");
        System.out.println("RESULTADO: " + laboratorio.emitirLaudo(laudoColesterolInvalido));

        System.out.println("\n=== SISTEMA EXECUTADO COM SUCESSO! ===");
    }
    
    /**
     * Demonstra o uso do padrão Memento com histórico de observações em laudos
     */
    private static void demonstrarHistoricoObservacoesEmLaudos(List<Exame> exames) {
        System.out.println("\n=== DEMONSTRAÇÃO: HISTÓRICO DE OBSERVAÇÕES EM LAUDOS (Memento Pattern) ===");

        if (exames.isEmpty()) {
            System.out.println("Nenhum exame processado para demonstração.");
            return;
        }
        
        // Usar diferentes exames para demonstrar laudos independentes
        FormatoLaudo formatoTexto = new Texto();
        
        // Criar laudos para diferentes pacientes
        System.out.println("\n--- Demonstração com Múltiplos Laudos ---");

        for (Exame exameDemo : exames) {
            System.out.println("exame: " + exameDemo);

            Laudo laudoDemo;
            switch (exameDemo.getTipoExame()) {
                case HEMOGRAMA:
                    laudoDemo = new LaudoHemograma(formatoTexto, exameDemo);
                    break;
                case ULTRASSONOGRAFIA:
                    laudoDemo = new LaudoUltrassonografia(formatoTexto, exameDemo);
                    break;
                case RESSONANCIA:
                    laudoDemo = new LaudoRessonanciaMagnetica(formatoTexto, exameDemo);
                    break;
                default:
                    laudoDemo = new LaudoHemograma(formatoTexto, exameDemo);
            }

            System.out.println("\n*** LAUDO " + (exameDemo.getCodigo()) + " - Paciente: " + exameDemo.getPaciente().getNome() + " ***");

            // Simular evolução específica para cada paciente
            if (exameDemo.getCodigo() % 2 == 0) {
                // Primeiro paciente - evolução positiva
                laudoDemo.adicionarObservacao("Paciente apresenta sintomas iniciais");
                laudoDemo.adicionarObservacao("Resultados mostram melhora significativa");
                laudoDemo.adicionarObservacao("Quadro clínico estabilizado - alta médica");
            } else {
                // Segundo paciente - evolução mais detalhada
                laudoDemo.adicionarObservacao("Paciente com histórico de complicações");
                laudoDemo.adicionarObservacao("Necessário acompanhamento rigoroso");
                laudoDemo.adicionarObservacao("Medicação ajustada conforme protocolo");
                laudoDemo.adicionarObservacao("Paciente apresenta melhora gradual");
                laudoDemo.adicionarObservacao("Resultados dentro da normalidade");
            }
            
            // Mostrar histórico do laudo
            laudoDemo.demonstrarHistoricoObservacoes();
            
            // Demonstrar navegação específica para este laudo
            if (laudoDemo.getHistoricoObservacao().size() > 2) {
                System.out.println("\n--- Navegação no Histórico do Laudo " + (exameDemo.getCodigo()) + " ---");
                int estadoMedio = laudoDemo.getHistoricoObservacao().size() / 2;
                laudoDemo.restaurarObservacao(estadoMedio);
                System.out.println("Estado médio restaurado: " + laudoDemo.getObservacao().getTexto());
                
                // Restaurar último estado
                laudoDemo.restaurarObservacao(laudoDemo.getHistoricoObservacao().size() - 1);
                System.out.println("Último estado restaurado: " + laudoDemo.getObservacao().getTexto());
            }
        }
    }
}
