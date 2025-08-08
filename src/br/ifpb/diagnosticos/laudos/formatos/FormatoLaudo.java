package br.ifpb.diagnosticos.laudos.formatos;

/**
 * Interface para diferentes formatos de laudo (Bridge)
 */
public interface FormatoLaudo {
    String formatar(String conteudo);
}
