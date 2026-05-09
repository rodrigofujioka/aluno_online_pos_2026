package br.com.alunoonline.api.util;

public final class CpfUtils {

    // Classe utilitaria: nao deve ser instanciada.
    private CpfUtils() {
    }

    // Exibe apenas os ultimos 4 digitos para evitar exposicao de dado sensivel.
    public static String formatCpf(String cpf) {
        // Retorno padrao quando o CPF nao foi informado.
        if (cpf == null || cpf.isBlank()) {
            return "não informado";
        }

        // Remove pontuacoes e outros caracteres para trabalhar so com digitos.
        String cpfNumerico = cpf.replaceAll("\\D", "");

        // Cenarios curtos: ainda mascara para manter consistencia visual.
        if (cpfNumerico.length() <= 4) {
            return "***" + cpfNumerico;
        }

        // Cenario padrao: mascara tudo e mostra somente os 4 ultimos digitos.
        return "***" + cpfNumerico.substring(cpfNumerico.length() - 4);
    }
}

