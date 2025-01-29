package fr.efrei.rag.web.rest;

import fr.efrei.rag.domain.Document;
import fr.efrei.rag.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class DocumentResource {

    private final DocumentService documentService;

    public DocumentResource(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/documents/create")
    public ResponseEntity<Document> createDocument(@RequestBody Document document) throws URISyntaxException {
        Document result = documentService.buildAndSave(document);
        return ResponseEntity
                .created(new URI("/documents/" + result.getId()))
                .body(result);
    }

    @GetMapping("/documents/findAll")
    public ResponseEntity<List<Document>> findAll() throws URISyntaxException {
        List<Document> result = documentService.findAll();
        return ResponseEntity
                .created(new URI("/documents/all"))
                .body(result);
    }

    @DeleteMapping("/documents/delete")
    public ResponseEntity<Document> deleteDocument(@RequestBody Long id) throws URISyntaxException {
        documentService.DeleteById(id);

        if (documentService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


}
