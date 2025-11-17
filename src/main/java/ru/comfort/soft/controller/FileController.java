package ru.comfort.soft.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.comfort.soft.service.FileProcessingService;

@RestController
@RequestMapping("/api")
public class FileController {

    private final FileProcessingService fileProcessingService;

    @Autowired
    public FileController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

    @Operation(summary = "Find Nth minimum number from Excel file")
    @GetMapping("/find-nth-min")
    public ResponseEntity<Object> findNthMinimum(
            @Parameter(description = "Path to local Excel file") @RequestParam String filePath,
            @Parameter(description = "N-th minimum number to find") @RequestParam int n) {
        try {
            int result = fileProcessingService.findNthMinimum(filePath, n);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}