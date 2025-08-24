package br.ifpb.diagnosticos.sistema;

import br.ifpb.diagnosticos.utils.ConfiguracaoSistema;
import br.ifpb.diagnosticos.utils.CarregadorCSV;
import br.ifpb.diagnosticos.modelo.Paciente;
import br.ifpb.diagnosticos.modelo.Medico;
import java.util.List;

/**
 * Demonstração do sistema de configuração e uso avançado do CarregadorCSV
 */
public class DemoConfiguracaoSistema {
    
    public static void main(String[] args) {
        System.out.println("=== DEMONSTRAÇÃO DO SISTEMA DE CONFIGURAÇÃO ===\n");
        
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        
        // Exibir configurações atuais
        System.out.println("📋 CONFIGURAÇÕES ATUAIS:");
        System.out.println("========================");
        System.out.println("Laboratório: " + config.getNomeLaboratorio());
        System.out.println("Telefone: " + config.getTelefoneLaboratorio());
        System.out.println("Desconto Convênio: " + config.getDescontoConvenio() + "%");
        System.out.println("Desconto Idoso: " + config.getDescontoIdoso() + "%");
        System.out.println("Caminho Pacientes: " + config.getCaminhoPacientes());
        System.out.println("Caminho Médicos: " + config.getCaminhoMedicos());
        System.out.println("Email Ativo: " + config.isNotificacaoEmailAtiva());
        System.out.println("SMS Ativo: " + config.isNotificacaoSmsAtiva());
        System.out.println("WhatsApp Ativo: " + config.isNotificacaoWhatsAppAtiva());
        
        // Modificar configurações
        System.out.println("\n🔧 MODIFICANDO CONFIGURAÇÕES:");
        System.out.println("=============================");
        config.set("desconto.convenio.percentual", "20");
        config.set("desconto.idoso.percentual", "12");
        config.set("laboratorio.nome", "LABORATÓRIO DEMO");
        config.salvarConfiguracoes();
        System.out.println(" Configurações modificadas e salvas!");
        
        // Exibir configurações modificadas
        System.out.println("\n📋 CONFIGURAÇÕES APÓS MODIFICAÇÃO:");
        System.out.println("===================================");
        System.out.println("Laboratório: " + config.getNomeLaboratorio());
        System.out.println("Desconto Convênio: " + config.getDescontoConvenio() + "%");
        System.out.println("Desconto Idoso: " + config.getDescontoIdoso() + "%");
        
        // Demonstrar carregamento usando métodos de conveniência
        System.out.println("\n📁 CARREGAMENTO DE DADOS COM CONFIGURAÇÃO:");
        System.out.println("==========================================");
        
        // Criar arquivos se necessário
        CarregadorCSV.criarArquivosExemploPadrao();
        
        // Carregar usando configurações padrão
        List<Paciente> pacientes = CarregadorCSV.carregarPacientesPadrao();
        List<Medico> medicos = CarregadorCSV.carregarMedicosPadrao();
        
        System.out.println("\n👥 Pacientes carregados (" + pacientes.size() + "):");
        for (int i = 0; i < Math.min(3, pacientes.size()); i++) {
            System.out.println("- " + pacientes.get(i));
        }
        
        System.out.println("\n👨‍⚕️ Médicos carregados (" + medicos.size() + "):");
        for (int i = 0; i < Math.min(3, medicos.size()); i++) {
            System.out.println("- " + medicos.get(i));
        }
        
        // Restaurar configurações originais
        System.out.println("\n🔄 RESTAURANDO CONFIGURAÇÕES ORIGINAIS:");
        System.out.println("=======================================");
        config.set("desconto.convenio.percentual", "15");
        config.set("desconto.idoso.percentual", "8");
        config.set("laboratorio.nome", "ST DIAGNÓSTICOS");
        config.salvarConfiguracoes();
        System.out.println(" Configurações originais restauradas!");
        
        System.out.println("\n🎯 DEMONSTRAÇÃO CONCLUÍDA COM SUCESSO!");
        System.out.println("=====================================");
        System.out.println("O sistema de configuração permite:");
        System.out.println("-  Configurar caminhos de arquivos CSV");
        System.out.println("-  Ajustar percentuais de desconto");
        System.out.println("-  Personalizar dados do laboratório");
        System.out.println("-  Ativar/desativar notificações");
        System.out.println("-  Persistir alterações em arquivo");
        System.out.println("-  Métodos de conveniência para carregamento");
    }
}
