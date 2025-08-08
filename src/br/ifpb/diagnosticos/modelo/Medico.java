package br.ifpb.diagnosticos.modelo;

/**
 * Representa um médico do laboratório
 */
public class Medico {
    private String nome;
    private String crm;
    private String especialidade;
    
    public Medico(String nome, String crm, String especialidade) {
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
    }
    
    // Getters
    public String getNome() {
        return nome;
    }
    
    public String getCrm() {
        return crm;
    }
    
    public String getEspecialidade() {
        return especialidade;
    }
    
    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setCrm(String crm) {
        this.crm = crm;
    }
    
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    
    @Override
    public String toString() {
        return String.format("Dr(a). %s (CRM: %s) - %s", nome, crm, especialidade);
    }
}
