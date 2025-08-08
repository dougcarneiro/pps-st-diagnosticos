package br.ifpb.diagnosticos.modelo;

import br.ifpb.diagnosticos.exames.Exame;
import br.ifpb.diagnosticos.laudos.Laudo;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um paciente do laboratório
 */
public class Paciente {
    private String nome;
    private String cpf;
    private String convenio;
    private int idade;
    private List<Exame> exames;
    private List<Laudo> laudos;
    
    public Paciente(String nome, String cpf, String convenio, int idade) {
        this.nome = nome;
        this.cpf = cpf;
        this.convenio = convenio;
        this.idade = idade;
        this.exames = new ArrayList<>();
        this.laudos = new ArrayList<>();
    }
    
    public void adicionarExame(Exame exame) {
        this.exames.add(exame);
    }
    
    public void adicionarLaudo(Laudo laudo) {
        this.laudos.add(laudo);
    }
    
    // Getters
    public String getNome() {
        return nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public String getConvenio() {
        return convenio;
    }
    
    public int getIdade() {
        return idade;
    }
    
    public List<Exame> getExames() {
        return exames;
    }
    
    public List<Laudo> getLaudos() {
        return laudos;
    }
}
