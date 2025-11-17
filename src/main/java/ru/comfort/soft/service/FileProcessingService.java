package ru.comfort.soft.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.comfort.soft.utils.HeapSelectionUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileProcessingService {

    public int findNthMinimum(String filePath, int n) throws Exception {
        List<Integer> numbers = readNumbersFromExcel(filePath);

        if (n <= 0 || n > numbers.size()) {
            throw new IllegalArgumentException("N must be between 1 and " + numbers.size());
        }

        return HeapSelectionUtils.findNthMinimum(numbers, n);
    }

    private List<Integer> readNumbersFromExcel(String filePath) throws Exception {
        List<Integer> numbers = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case NUMERIC -> numbers.add((int) cell.getNumericCellValue());
                        case STRING -> {
                            try {
                                numbers.add(Integer.parseInt(cell.getStringCellValue()));
                            } catch (NumberFormatException e) {
                            }
                        }
                        default -> {
                        }
                    }
                }
            }
        }

        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("No valid numbers found in the file");
        }

        return numbers;
    }
}