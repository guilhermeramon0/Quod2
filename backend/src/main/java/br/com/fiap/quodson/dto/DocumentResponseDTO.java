package br.com.fiap.quodson.dto;

public class DocumentResponseDTO {

    private boolean valido;
    private String mensagem;

    public DocumentResponseDTO(boolean valido, String mensagem) {
        this.valido = valido;
        this.mensagem = mensagem;
    }

    public boolean isValido() {
        return valido;
    }

    public String getMensagem() {
        return mensagem;
    }
}
