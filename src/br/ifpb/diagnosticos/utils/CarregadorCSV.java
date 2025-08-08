package br.ifpb.diagnosticos.utils;

import br.ifpb.diagnosticos.modelo.Paciente;
import br.ifpb.diagnosticos.modelo.Medico;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitário para carregar dados de pacientes e médicos a partir de arquivos CSV
 * Implementa o Requisito R1 - Carregamento de dados via arquivo
 * Agora integrado com sistema de configuração
 */
public class CarregadorCSV {
    
    private static final ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
    
    /**
     * Carrega pacientes de um arquivo CSV
     * @param caminhoArquivo caminho para o arquivo CSV de pacientes
     * @return lista de pacientes carregados
     */
    public static List<Paciente> carregarPacientes(String caminhoArquivo) {
        List<Paciente> pacientes = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;
            
            while ((linha = br.readLine()) != null) {
                // Pular cabeçalho
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                
                String[] dados = linha.split(",");
                if (dados.length >= 4) {
                    String nome = dados[0].trim();
                    String cpf = dados[1].trim();
                    String convenio = dados[2].trim();
                    int idade = Integer.parseInt(dados[3].trim());
                    
                    pacientes.add(new Paciente(nome, cpf, convenio, idade));
                }
            }
            
            System.out.println("Carregados " + pacientes.size() + " pacientes do arquivo " + caminhoArquivo);
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de pacientes: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter idade para número: " + e.getMessage());
        }
        
        return pacientes;
    }
    
    /**
     * Carrega médicos de um arquivo CSV
     * @param caminhoArquivo caminho para o arquivo CSV de médicos
     * @return lista de médicos carregados
     */
    public static List<Medico> carregarMedicos(String caminhoArquivo) {
        List<Medico> medicos = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;
            
            while ((linha = br.readLine()) != null) {
                // Pular cabeçalho
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                
                String[] dados = linha.split(",");
                if (dados.length >= 3) {
                    String nome = dados[0].trim();
                    String crm = dados[1].trim();
                    String especialidade = dados[2].trim();
                    
                    medicos.add(new Medico(nome, crm, especialidade));
                }
            }
            
            System.out.println("Carregados " + medicos.size() + " médicos do arquivo " + caminhoArquivo);
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de médicos: " + e.getMessage());
        }
        
        return medicos;
    }
    
    /**
     * Cria um arquivo de exemplo com pacientes se não existir
     * @param caminhoArquivo caminho onde criar o arquivo
     */
    public static void criarArquivoExemploPacientes(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        
        // Criar diretório se não existir
        arquivo.getParentFile().mkdirs();
        
        if (!arquivo.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo))) {
                writer.println("nome,cpf,convenio,idade");
                writer.println("João Silva,123.456.789-01,Unimed,45");
                writer.println("Maria Santos,987.654.321-02,Bradesco Saúde,72");
                writer.println("Pedro Oliveira,456.789.123-03,SUS,25");
                writer.println("Ana Costa,111.222.333-44,SulAmérica,34");
                writer.println("Carlos Mendes,555.666.777-88,NotreDame,58");
                
                System.out.println("Arquivo de exemplo de pacientes criado: " + caminhoArquivo);
                
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de exemplo de pacientes: " + e.getMessage());
            }
        }
    }
    
    /**
     * Cria um arquivo de exemplo com médicos se não existir
     * @param caminhoArquivo caminho onde criar o arquivo
     */
    public static void criarArquivoExemploMedicos(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        
        // Criar diretório se não existir
        arquivo.getParentFile().mkdirs();
        
        if (!arquivo.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(arquivo))) {
                writer.println("nome,crm,especialidade");
                writer.println("Dr. João Silva,12345,Clínico Geral");
                writer.println("Dr. Maria Santos,67890,Radiologista");
                writer.println("Dra. Ana Ferreira,11111,Hematologista");
                writer.println("Dr. Carlos Pereira,22222,Cardiologista");
                writer.println("Dra. Lucia Oliveira,33333,Ultrassonografista");
                
                System.out.println("Arquivo de exemplo de médicos criado: " + caminhoArquivo);
                
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de exemplo de médicos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Carrega pacientes usando o caminho configurado no sistema
     * @return lista de pacientes carregados
     */
    public static List<Paciente> carregarPacientesPadrao() {
        return carregarPacientes(config.getCaminhoPacientes());
    }
    
    /**
     * Carrega médicos usando o caminho configurado no sistema
     * @return lista de médicos carregados
     */
    public static List<Medico> carregarMedicosPadrao() {
        return carregarMedicos(config.getCaminhoMedicos());
    }
    
    /**
     * Cria arquivos de exemplo usando os caminhos configurados
     */
    public static void criarArquivosExemploPadrao() {
        criarArquivoExemploPacientes(config.getCaminhoPacientes());
        criarArquivoExemploMedicos(config.getCaminhoMedicos());
    }
}
