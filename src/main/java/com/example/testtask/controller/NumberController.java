package com.example.testtask.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@RestController
@RequestMapping("api/v1/numbers")
public class NumberController {

    @PostMapping(value = "/find-max-n", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> postFindNMax(
            @RequestParam("file") MultipartFile file,
            @RequestParam("n") int n
    ) throws IOException {
        List<Integer> numbers = readXlsFile(file.getInputStream());
        int nMax = findNMax(numbers, n);
        return ResponseEntity.ok(nMax);
    }

    @GetMapping(value = "/test")
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok("ok");
    }

    private List<Integer> readXlsFile(InputStream inputStream) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        Workbook workbook = new HSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                numbers.add((int) cell.getNumericCellValue());
            }
        }
        workbook.close();
        return numbers;
    }

    /*
     В файле в столбик находятся целые числа
     Метод должен вернуть N-ное максимальное число из файла
     */

    private static Integer findNMax(List<Integer> numbers, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.naturalOrder());
        //создает приоритетную очередь, где эдементы расположенны по возрастанию

        for (Integer number : numbers) { //проходит по листу с номерами, полученными из файла
            queue.add(number); //добавляет каждый элемент в очередь
            if (queue.size() > n) { //когда размер очереди начинает превышать заданный элемент N
                queue.poll(); //удаляет первый элемент из головы очереди. Т.к. в голову попадают наименьшие элементы, а наибольшее скапливаются в хвосте, то после окончания всех итераций мы получим очередь из N наибольших элементов
            }
        }

        return queue.peek(); //возвращает первый элемент очереди
    }
}
