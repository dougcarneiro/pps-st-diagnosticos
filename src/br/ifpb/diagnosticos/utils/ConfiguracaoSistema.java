package br.ifpb.diagnosticos.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Gerenciador de configurações do sistema
 * Permite configurar caminhos de arquivos, parâmetros de notificação, etc.
 */
public class ConfiguracaoSistema {
    private static final String ARQUIVO_CONFIG = "config/sistema.properties";
    private static ConfiguracaoSistema instancia;
    private Properties propriedades;
    
    private ConfiguracaoSistema() {
        propriedades = new Properties();
        carregarConfiguracoes();
    }
    
    public static synchronized ConfiguracaoSistema getInstance() {
        if (instancia == null) {
            instancia = new ConfiguracaoSistema();
        }
        return instancia;
    }
    
    private void carregarConfiguracoes() {
        try (FileInputStream fis = new FileInputStream(ARQUIVO_CONFIG)) {
            propriedades.load(fis);
        } catch (IOException e) {
            // Se não conseguir carregar, criar configurações padrão
            criarConfiguracoesPadrao();
        }
    }
    
    private void criarConfiguracoesPadrao() {
        // Caminhos de arquivos CSV
        propriedades.setProperty("csv.pacientes.caminho", "dados/pacientes.csv");
        propriedades.setProperty("csv.medicos.caminho", "dados/medicos.csv");
        
        // Configurações de desconto
        propriedades.setProperty("desconto.convenio.percentual", "15");
        propriedades.setProperty("desconto.idoso.percentual", "8");
        
        // Configurações de email
        propriedades.setProperty("email.remetente", "douglas.carneiro@academico.ifpb.edu.br");
        propriedades.setProperty("email.senha", "usar senha de app");
        propriedades.setProperty("email.destinoDev", "caio.soares@academico.ifpb.edu.br");
        
        // Configurações de notificação
        propriedades.setProperty("notificacao.email.ativo", "dev");
        propriedades.setProperty("notificacao.sms.ativo", "true");
        propriedades.setProperty("notificacao.whatsapp.ativo", "true");
        
        // Configurações do laboratório
        propriedades.setProperty("laboratorio.nome", "ST DIAGNÓSTICOS");
        propriedades.setProperty("laboratorio.telefone", "(11) 99999-9999");
        
        salvarConfiguracoes();
    }
    
    public void salvarConfiguracoes() {
        try {
            // Criar diretório config se não existir
            java.io.File configDir = new java.io.File("config");
            configDir.mkdirs();
            
            try (FileOutputStream fos = new FileOutputStream(ARQUIVO_CONFIG)) {
                propriedades.store(fos, "Configurações do Sistema de Exames Médicos");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar configurações: " + e.getMessage());
        }
    }
    
    public String get(String chave) {
        return propriedades.getProperty(chave);
    }
    
    public String get(String chave, String valorPadrao) {
        return propriedades.getProperty(chave, valorPadrao);
    }
    
    public int getInt(String chave, int valorPadrao) {
        try {
            return Integer.parseInt(propriedades.getProperty(chave, String.valueOf(valorPadrao)));
        } catch (NumberFormatException e) {
            return valorPadrao;
        }
    }
    
    public boolean getBoolean(String chave, boolean valorPadrao) {
        return Boolean.parseBoolean(propriedades.getProperty(chave, String.valueOf(valorPadrao)));
    }
    
    public void set(String chave, String valor) {
        propriedades.setProperty(chave, valor);
    }
    
    // Métodos de conveniência para configurações específicas
    public String getCaminhoPacientes() {
        return get("csv.pacientes.caminho", "dados/pacientes.csv");
    }
    
    public String getCaminhoMedicos() {
        return get("csv.medicos.caminho", "dados/medicos.csv");
    }
    
    public int getDescontoConvenio() {
        return getInt("desconto.convenio.percentual", 15);
    }
    
    public int getDescontoIdoso() {
        return getInt("desconto.idoso.percentual", 8);
    }
    
    public String getEmailRemetente() {
        return get("email.remetente", "douglas.carneiro@academico.ifpb.edu.br");
    }
    
    public String getEmailSenha() {
        return get("email.senha", "usar senha de app");
    }
    
    public String getEmailDestinoDev() {
        return get("email.destinoDev", "caio.soares@academico.ifpb.edu.br");
    }
    
    public String getNomeLaboratorio() {
        return get("laboratorio.nome", "ST DIAGNÓSTICOS");
    }
    
    public String getTelefoneLaboratorio() {
        return get("laboratorio.telefone", "(11) 99999-9999");
    }
    
    public boolean isNotificacaoEmailAtiva() {
        String valor = get("notificacao.email.ativo", "dev");
        return !valor.equals("false");
    }
    
    public boolean isNotificacaoEmailDev() {
        String valor = get("notificacao.email.ativo", "dev");
        return valor.equals("dev");
    }
    
    public boolean isNotificacaoSmsAtiva() {
        return getBoolean("notificacao.sms.ativo", true);
    }
    
    public boolean isNotificacaoWhatsAppAtiva() {
        return getBoolean("notificacao.whatsapp.ativo", true);
    }

    public boolean isNotificacaoEmailAtivo() {
        return getBoolean("notificacao.email.ativo", true);
    }
}
