package br.ifpb.diagnosticos.utils;

/**
 * Classe utilitária para formatação de texto
 */
public class FormatadorTexto {
    
    /**
     * Converte uma string para Title Case (primeira letra maiúscula)
     * Também substitui underscores por espaços para melhor legibilidade
     * 
     * @param texto o texto a ser formatado
     * @return texto formatado em Title Case
     */
    public static String formatarTitleCase(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        
        // Substituir underscores por espaços
        String textoFormatado = texto.replace("_", " ");
        
        // Converter para title case
        StringBuilder resultado = new StringBuilder();
        boolean proximaMaiuscula = true;
        
        for (char c : textoFormatado.toCharArray()) {
            if (Character.isWhitespace(c)) {
                proximaMaiuscula = true;
                resultado.append(c);
            } else if (proximaMaiuscula) {
                resultado.append(Character.toUpperCase(c));
                proximaMaiuscula = false;
            } else {
                resultado.append(Character.toLowerCase(c));
            }
        }
        
        return resultado.toString();
    }
}
