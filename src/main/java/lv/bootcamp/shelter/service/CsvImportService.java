package lv.bootcamp.shelter.service;

import lombok.extern.slf4j.Slf4j;
import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.service.data.ImportResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CsvImportService {

    public ImportResult importAnimals(Path inputPath) {
        log.info("Starting import from {}", inputPath);

        List<Animal> allAnimals = new ArrayList<>();

        // TODO Step 1:
        List<String> lines = Files.readAllLines(inputPath, StandartCharsets.UTF-8);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        for(int i = 1; i < lines.size(); i++){
            String line = lines.get(i);
        }
        // 3) Skip malformed rows and log warnings.
        // 4) Allow blank age as unknown (null), but reject non-numeric age values.
        // 5) Parse intakeDate using DateTimeFormatter.
        // 6) Map each row to Animal object.

        return new ImportResult(allAnimals, 0);
    }
}
