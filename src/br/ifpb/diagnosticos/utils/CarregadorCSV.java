package br.ifpb.diagnosticos.utils;

import br.ifpb.diagnosticos.modelo.Paciente;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilitário para carregar dados de arquivos CSV (Requisito R1)
 */
public class CarregadorCSV {
    
    public static List<Paciente> carregarPacientes(String caminhoArquivo) {
        List<Paciente> pacientes = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;
            
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // Pular cabeçalho
                    continue;
                }
                
                String[] dados = linha.split(",");
                if (dados.length >= 4) {
                    String nome = dados[0].trim();
                    String cpf = dados[1].trim();
                    int idade = Integer.parseInt(dados[2].trim());
                    String convenio = dados[3].trim();
                    
                    Paciente paciente = new Paciente(nome, cpf, convenio, idade);
                    pacientes.add(paciente);
                }
            }
            
            System.out.println("Carregados " + pacientes.size() + " pacientes do arquivo CSV");
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter idade: " + e.getMessage());
        }
        
        return pacientes;
    }
    
    public static Map<String, Object> carregarDadosExame(String caminhoArquivo, String tipoExame) {
        Map<String, Object> dados = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;
            
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // Pular cabeçalho
                    continue;
                }
                
                String[] partes = linha.split(",");
                if (partes.length >= 2) {
                    String chave = partes[0].trim();
                    String valor = partes[1].trim();
                    
                    // Tentar converter para tipos apropriados
                    if (valor.matches("-?\\\\d+")) {
                        dados.put(chave, Integer.parseInt(valor));
                    } else if (valor.matches("-?\\\\d+\\\\.\\\\d+")) {
                        dados.put(chave, Double.parseDouble(valor));
                    } else if (valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("false")) {
                        dados.put(chave, Boolean.parseBoolean(valor));
                    } else {
                        dados.put(chave, valor);
                    }
                }
            }
            
            System.out.println("Carregados dados de exame " + tipoExame + " do arquivo CSV");
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo de dados do exame: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter valores numéricos: " + e.getMessage());
        }
        
        return dados;
    }
    
    public static void criarArquivoExemplo(String caminhoArquivo) {
        String conteudoExemplo = "nome,cpf,idade,convenio\n" +
                               "João Silva,123.456.789-01,45,Unimed\n" +
                               "Maria Santos,987.654.321-00,32,SulAmérica\n" +
                               "Carlos Oliveira,456.789.123-45,67,Particular\n" +
                               "Ana Costa,789.123.456-78,28,Bradesco Saúde\n";
        
        try {
            java.nio.file.Files.write(java.nio.file.Paths.get(caminhoArquivo), 
                                    conteudoExemplo.getBytes());
            System.out.println("Arquivo de exemplo criado: " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao criar arquivo de exemplo: " + e.getMessage());
        }
    }
}
