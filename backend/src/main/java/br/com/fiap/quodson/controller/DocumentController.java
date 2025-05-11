package br.com.fiap.quodson.controller;

import br.com.fiap.quodson.dto.DocumentResponseDTO;
import br.com.fiap.quodson.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documentos")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping(value = "/validar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponseDTO> validarDocumento(@RequestPart MultipartFile file) {
        return ResponseEntity.ok(documentService.validarDocumento(file));
    }
}
