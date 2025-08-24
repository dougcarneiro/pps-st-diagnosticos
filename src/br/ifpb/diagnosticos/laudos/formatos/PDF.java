package br.ifpb.diagnosticos.laudos.formatos;

/**
 * Implementação para formato PDF (Bridge Pattern)
 */
public class PDF implements FormatoLaudo {

    private static final String DIRETORIO_DESTINO = "output/";

    @Override
    public String formatar(String conteudo) {
        return "=== FORMATO PDF ===\n" + 
               conteudo + 
               "\n=== FIM PDF ===";
    }

    /**
     * Gera um arquivo PDF com as informações do exame, laudo e paciente.
     * @param conteudo Texto a ser inserido no PDF
     * @param nomeExame Nome do exame
     * @param nomePaciente Nome do paciente
     * @return Caminho do arquivo gerado
     */
    public String gerarPDF(String conteudo, String nomeExame, String nomePaciente) {
        String nomeArquivo = nomeExame + "_" + nomePaciente + ".pdf";
        String caminho = DIRETORIO_DESTINO + nomeArquivo;
        try {
            org.apache.pdfbox.pdmodel.PDDocument document = new org.apache.pdfbox.pdmodel.PDDocument();
            org.apache.pdfbox.pdmodel.PDPage page = new org.apache.pdfbox.pdmodel.PDPage();
            document.addPage(page);
            org.apache.pdfbox.pdmodel.PDPageContentStream contentStream = new org.apache.pdfbox.pdmodel.PDPageContentStream(document, page);
            contentStream.beginText();
            org.apache.pdfbox.pdmodel.font.PDType1Font font = new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD);
            contentStream.setFont(font, 14);
            contentStream.setLeading(16f);
            contentStream.newLineAtOffset(50, 700);
            String[] linhas = conteudo.split("\n");
            for (String linha : linhas) {
                if (linha == null) linha = "";
                // Remover CR (carriage return) que causa IllegalArgumentException em PDFBox
                String limpa = linha.replace("\r", "");
                // Evitar caracteres de controle não imprimíveis
                limpa = limpa.replaceAll("[\u0000-\u001F]", "");
                contentStream.showText(limpa);
                contentStream.newLine();
            }
            contentStream.endText();
            contentStream.close();
            document.save(caminho);
            document.close();
            return caminho;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
