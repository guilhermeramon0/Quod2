package br.com.fiap.quodson.service;

import br.com.fiap.quodson.dto.DocumentResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@Service
public class DocumentService {

    public DocumentResponseDTO validarDocumento(MultipartFile file) {
        // Validação simples de tipo e presença
        if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
            return new DocumentResponseDTO(false, "Arquivo inválido");
        }

        // Simulação de validação antifraude
        boolean fraudeDetectada = new Random().nextBoolean();

        return new DocumentResponseDTO(!fraudeDetectada,
                fraudeDetectada ? "Fraude detectada" : "Documento válido");
    }
}
