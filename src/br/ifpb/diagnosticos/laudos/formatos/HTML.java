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
}
