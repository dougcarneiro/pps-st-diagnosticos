package br.ifpb.diagnosticos.laudos.formatos;

/**
 * Implementação para formato PDF (Bridge Pattern)
 */
public class PDF implements FormatoLaudo {
    @Override
    public String formatar(String conteudo) {
        return "=== FORMATO PDF ===\n" + 
               conteudo + 
               "\n=== FIM PDF ===";
    }
}
