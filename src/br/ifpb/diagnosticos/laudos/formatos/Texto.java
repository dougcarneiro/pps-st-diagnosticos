package br.ifpb.diagnosticos.laudos.formatos;

/**
 * Implementação para formato Texto simples (Bridge Pattern)
 */
public class Texto implements FormatoLaudo {
    @Override
    public String formatar(String conteudo) {
        return conteudo;
    }
}
