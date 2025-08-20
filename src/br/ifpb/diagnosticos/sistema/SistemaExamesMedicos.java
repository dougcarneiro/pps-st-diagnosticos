package br.ifpb.diagnosticos.sistema;

import br.ifpb.diagnosticos.modelo.Paciente;
import br.ifpb.diagnosticos.modelo.Medico;
import br.ifpb.diagnosticos.modelo.Prioridade;
import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.financeiro.*;
import br.ifpb.diagnosticos.utils.CarregadorCSV;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        
        // Usar pacientes carregados + alguns criados manualmente
        Paciente paciente1 = pacientesCarregados.size() > 0 ? pacientesCarregados.get(0) 
                            : new Paciente("João Silva", "123.456.789-01", "Unimed", 45);
        Paciente paciente2 = pacientesCarregados.size() > 1 ? pacientesCarregados.get(1) 
                            : new Paciente("Maria Santos", "987.654.321-02", "Bradesco Saúde", 72);
        Paciente paciente3 = pacientesCarregados.size() > 2 ? pacientesCarregados.get(2) 
                            : new Paciente("Pedro Oliveira", "456.789.123-03", "Particular", 25);
        
        System.out.println("\n1. SOLICITAÇÃO DE EXAMES");
        System.out.println("========================");
        
        // Solicitar exames com diferentes prioridades e descontos
        Exame exame1 = laboratorio.solicitarExame(
            paciente1, "HEMOGRAMA", Prioridade.ROTINA, 
            new DescontoConvenio(), 80.0
        );
        
        Exame exame2 = laboratorio.solicitarExame(
            paciente2, "RESSONANCIA", Prioridade.URGENTE, 
            new DescontoIdoso(), 800.0
        );
        
        Exame exame3 = laboratorio.solicitarExame(
            paciente3, "ULTRASSONOGRAFIA", Prioridade.POUCO_URGENTE, 
            null, 150.0
        );
        
        // Outro exame para demonstrar fila
        laboratorio.solicitarExame(
            paciente1, "RESSONANCIA", Prioridade.URGENTE, 
            new DescontoConvenio(), 800.0
        );
        
        System.out.println("\n1.1. SOLICITAÇÃO DE EXAMES SANGUÍNEOS COM INDICADORES (Decorator Pattern)");
        System.out.println("=========================================================================");
        
        // Exame com apenas um indicador - Glicemia
        Exame exameGlicemia = laboratorio.solicitarExame(
            paciente1, "HEMOGRAMA", Prioridade.ROTINA, 
            new DescontoConvenio(), 60.0, new String[]{"GLICEMIA"}
        );
        
        // Exame com múltiplos indicadores - Colesterol + Creatinina
        Exame exameMultiplo1 = laboratorio.solicitarExame(
            paciente2, "HEMOGRAMA", Prioridade.POUCO_URGENTE, 
            new DescontoIdoso(), 120.0, new String[]{"COLESTEROL", "CREATININA"}
        );
        
        // Exame completo com todos os indicadores
        Exame exameCompleto = laboratorio.solicitarExame(
            paciente3, "HEMOGRAMA", Prioridade.URGENTE, 
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
        System.out.println("\n--- Laudo de Hemograma (PDF) ---");
        String laudoHemograma = laboratorio.gerarLaudo(exame1, "PDF");
        System.out.println(laudoHemograma);
        
        System.out.println("\n--- Laudo de Ressonância (HTML) ---");
        String laudoRessonancia = laboratorio.gerarLaudo(exame2, "HTML");
        System.out.println(laudoRessonancia);
        
        System.out.println("\n--- Laudo de Ultrassonografia (Texto) ---");
        String laudoUltrassom = laboratorio.gerarLaudo(exame3, "TEXTO");
        System.out.println(laudoUltrassom);
        
        System.out.println("\n--- Laudo de Exame Sanguíneo com Glicemia (PDF) ---");
        String laudoGlicemia = laboratorio.gerarLaudo(exameGlicemia, "PDF");
        System.out.println(laudoGlicemia);
        
        System.out.println("\n--- Laudo de Exame Sanguíneo com Múltiplos Indicadores (HTML) ---");
        String laudoMultiplo = laboratorio.gerarLaudo(exameMultiplo1, "HTML");
        System.out.println(laudoMultiplo);
        
        System.out.println("\n--- Laudo de Exame Sanguíneo Completo (Texto) ---");
        String laudoCompletoTxt = laboratorio.gerarLaudo(exameCompleto, "TEXTO");
        System.out.println(laudoCompletoTxt);
        
        System.out.println("\n5. DEMONSTRAÇÃO DE VALIDAÇÃO (Chain of Responsibility)");
        System.out.println("=======================================================");
        
        // Testar validação com dados realmente inválidos
        Map<String, Object> dadosInvalidos = new HashMap<>();
        
        System.out.println("\n--- Teste 1: Dados completamente vazios ---");
        System.out.println("DADOS ORIGINAIS: " + exame1.getDados());
        exame1.setDados(dadosInvalidos);
        System.out.println("DADOS VAZIOS: " + exame1.getDados());
        String laudoVazio = laboratorio.gerarLaudo(exame1, "TEXTO");
        System.out.println("RESULTADO: " + laudoVazio);
        
        System.out.println("\n--- Teste 2: Dados com valores inválidos ---");
        Map<String, Object> dadosGlicemiaInvalida = new HashMap<>();
        // Simular como seria um indicador de glicemia real, mas com valor inválido
        HashMap<String, Object> glicemiaInvalida = new HashMap<>();
        glicemiaInvalida.put("valor", -50.0); // Valor negativo inválido
        glicemiaInvalida.put("unidade", "mg/dL");
        dadosGlicemiaInvalida.put("glicemia", glicemiaInvalida);
        
        exame1.setDados(dadosGlicemiaInvalida);
        System.out.println("DADOS COM GLICEMIA INVÁLIDA: " + exame1.getDados());
        String laudoGlicemiaInvalida = laboratorio.gerarLaudo(exame1, "TEXTO");
        System.out.println("RESULTADO: " + laudoGlicemiaInvalida);
        
        System.out.println("\n--- Teste 3: Dados com colesterol inválido ---");
        Map<String, Object> dadosColesterolInvalido = new HashMap<>();
        HashMap<String, Object> colesterolInvalido = new HashMap<>();
        colesterolInvalido.put("total", 1000.0); // Valor muito alto inválido
        dadosColesterolInvalido.put("colesterol", colesterolInvalido);
        
        exame1.setDados(dadosColesterolInvalido);
        System.out.println("DADOS COM COLESTEROL INVÁLIDO: " + exame1.getDados());
        String laudoColesterolInvalido = laboratorio.gerarLaudo(exame1, "TEXTO");
        System.out.println("RESULTADO: " + laudoColesterolInvalido);
        
        System.out.println("\n6. DEMONSTRAÇÃO DE HISTÓRICO DE OBSERVAÇÕES (Memento Pattern)");
        System.out.println("==============================================================");
        
        // Demonstrar o padrão Memento com histórico de observações
        laboratorio.demonstrarHistoricoObservacoes();
        
        
        System.out.println("\n8. DEMONSTRAÇÃO DE PADRÕES IMPLEMENTADOS");
        System.out.println("=========================================");
        System.out.println("✅ Singleton: GeradorNumeroExame");
        System.out.println("✅ Strategy: Descontos (Convênio 15%, Idoso 8%)");
        System.out.println("✅ Factory Method: Criadores de Exame");
        System.out.println("✅ Template Method: Execução de Exames");
        System.out.println("✅ Chain of Responsibility: Validação de Dados");
        System.out.println("✅ Bridge: Laudos e Formatos (PDF, HTML, Texto)");
        System.out.println("✅ Observer: Notificações (Email, SMS, WhatsApp)");
        System.out.println("✅ Memento: Histórico de Observações");
        System.out.println("✅ Facade: Interface Simplificada do Laboratório");
        System.out.println("✅ Priority Queue: Fila de Exames por Prioridade");
        System.out.println("✅ Decorator: Indicadores em Exames Sanguíneos (Glicemia, Colesterol, Creatinina)");
        
        System.out.println("\n9. REQUISITOS FUNCIONAIS ATENDIDOS");
        System.out.println("===================================");
        System.out.println("✅ R1: Carregar dados de pacientes via CSV");
        System.out.println("✅ R2: Sistema de prioridades (Urgente > Pouco Urgente > Rotina)");
        System.out.println("✅ R3: Diferentes tipos de exame (Hemograma, Ultrassom, RM)");
        System.out.println("✅ R4: Múltiplos formatos de laudo (PDF, HTML, Texto)");
        System.out.println("✅ R5: Sistema de descontos (Convênio, Idoso)");
        System.out.println("✅ R6: Validação de dados por tipo de exame");
        System.out.println("✅ R7: Notificações automáticas multi-canal");
        System.out.println("✅ R8: Histórico de modificações em observações");
        System.out.println("✅ R9: Interface simplificada (Facade)");
        
        System.out.println("\n=== SISTEMA EXECUTADO COM SUCESSO! ===");
    }
}
