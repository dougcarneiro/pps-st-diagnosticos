package br.ifpb.diagnosticos.sistema;

import br.ifpb.diagnosticos.modelo.Paciente;
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
        
        // Criar arquivo de exemplo se não existir
        CarregadorCSV.criarArquivoExemplo("pacientes.csv");
        
        // Carregar pacientes do arquivo CSV
        List<Paciente> pacientesCarregados = CarregadorCSV.carregarPacientes("pacientes.csv");
        
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
        
        Exame exame4 = laboratorio.solicitarExame(
            paciente1, "RESSONANCIA", Prioridade.URGENTE, 
            new DescontoConvenio(), 800.0
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
        Map<String, Object> dadosHemograma = new HashMap<>();
        dadosHemograma.put("hemoglobina", 14.5);
        dadosHemograma.put("hematocritos", 42.0);
        dadosHemograma.put("leucocitos", 7500);
        
        String laudoHemograma = laboratorio.gerarLaudo(exame1, "PDF", dadosHemograma);
        System.out.println(laudoHemograma);
        
        System.out.println("\n--- Laudo de Ressonância (HTML) ---");
        Map<String, Object> dadosRessonancia = new HashMap<>();
        dadosRessonancia.put("regiao", "Joelho direito");
        dadosRessonancia.put("sequencias", "T1, T2, FLAIR");
        dadosRessonancia.put("contraste", false);
        dadosRessonancia.put("laudo_tecnico", "Estruturas ósseas preservadas");
        dadosRessonancia.put("impressao", "Exame normal");
        
        String laudoRessonancia = laboratorio.gerarLaudo(exame2, "HTML", dadosRessonancia);
        System.out.println(laudoRessonancia);
        
        System.out.println("\n--- Laudo de Ultrassonografia (Texto) ---");
        Map<String, Object> dadosUltrassom = new HashMap<>();
        dadosUltrassom.put("orgao", "Abdomen total");
        dadosUltrassom.put("achados", "Fígado de dimensões normais");
        dadosUltrassom.put("ecogenicidade", "Homogênea");
        dadosUltrassom.put("medidas", "15,2 x 8,1 cm");
        
        String laudoUltrassom = laboratorio.gerarLaudo(exame3, "TEXTO", dadosUltrassom);
        System.out.println(laudoUltrassom);
        
        System.out.println("\n5. DEMONSTRAÇÃO DE VALIDAÇÃO (Chain of Responsibility)");
        System.out.println("=======================================================");
        
        // Testar validação com dados inválidos
        Map<String, Object> dadosInvalidos = new HashMap<>();
        dadosInvalidos.put("hemoglobina", 14.5); // Falta leucócitos
        
        System.out.println("\n--- Tentativa de laudo com dados incompletos ---");
        String laudoInvalido = laboratorio.gerarLaudo(exame1, "TEXTO", dadosInvalidos);
        System.out.println(laudoInvalido);
        
        System.out.println("\n6. DEMONSTRAÇÃO DE PADRÕES IMPLEMENTADOS");
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
        
        System.out.println("\n7. REQUISITOS FUNCIONAIS ATENDIDOS");
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
