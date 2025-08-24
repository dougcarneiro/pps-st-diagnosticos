package br.ifpb.diagnosticos.laudos.formatos;

/**
 * Implementação para formato HTML (Bridge Pattern)
 */
public class HTML implements FormatoLaudo {
    @Override
    public String formatar(String conteudo) {
        return "<html><head><title>Laudo Médico</title></head><body>" +
               "<pre>" + conteudo + "</pre>" +
               "</body></html>";
    }

    /**
     * Gera um arquivo HTML estilizado com as informações do exame, laudo e paciente.
     * @param conteudo Texto a ser inserido no HTML
     * @param nomeExame Nome do exame
     * @param nomePaciente Nome do paciente
     * @return Caminho do arquivo gerado
     */
    public String gerarHTML(String conteudo, String nomeExame, String nomePaciente) {
        String nomeArquivo = nomeExame + "_" + nomePaciente + ".html";
        String caminho = "docs/" + nomeArquivo;
        String html = "<html><head><title>Laudo Médico</title>"
            + "<style>body{font-family:sans-serif;} h1{color:#2a5d84;} table{border-collapse:collapse;width:100%;margin:20px 0;} th,td{border:1px solid #ccc;padding:8px;} th{background:#eaf3fa;} .section{margin-bottom:20px;}</style>"
            + "</head><body>"
            + "<h1>Laudo Médico</h1>"
            + "<div class='section'><strong>Exame:</strong> " + nomeExame + "<br><strong>Paciente:</strong> " + nomePaciente + "</div>"
            + "<div class='section'><table><tr><th>Informações</th></tr>";
        String[] linhas = conteudo.split("\n");
        for (String linha : linhas) {
            html += "<tr><td>" + linha.replace("<", "&lt;").replace(">", "&gt;") + "</td></tr>";
        }
        html += "</table></div></body></html>";
        try (java.io.FileWriter writer = new java.io.FileWriter(caminho)) {
            writer.write(html);
            return caminho;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
