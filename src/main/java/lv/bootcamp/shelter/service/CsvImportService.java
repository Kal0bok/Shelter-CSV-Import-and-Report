package lv.bootcamp.shelter.service;

import lombok.extern.slf4j.Slf4j;
import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.service.data.ImportResult;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CsvImportService {

    public ImportResult importAnimals(Path inputPath) {
        log.info("Starting import from {}", inputPath);

        List<Animal> allAnimals = new ArrayList<>();
        int skippedRows = 0;

        try {
            // 1) Read intake.csv with UTF-8
            List<String> lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            // 2) Skip header row and 3) Skip malformed rows with warnings
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(",", -1);

                if (parts.length < 5) {
                    log.warn("Skipping malformed row {}: insufficient columns", i + 1);
                    skippedRows++;
                    continue;
                }

                try {
                    String name = parts[0].trim();
                    if (name.isEmpty()) throw new IllegalArgumentException("Name is required");

                    String species = parts[1].trim();
                    if (species.isEmpty()) throw new IllegalArgumentException("Species is required");

                    // 4) Allow blank age as unknown (null), but reject non-numeric age values
                    Integer age = null;
                    String ageStr = parts[2].trim();
                    if (!ageStr.isEmpty()) {
                        age = Integer.parseInt(ageStr);
                        if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
                    }

                    String vaccinatedStr = parts[3].trim();
                    if (!vaccinatedStr.equalsIgnoreCase("true") && !vaccinatedStr.equalsIgnoreCase("false")) {
                        throw new IllegalArgumentException("Invalid boolean");
                    }
                    boolean vaccinated = Boolean.parseBoolean(vaccinatedStr);

                    // 5) Parse intakeDate using DateTimeFormatter
                    String dateStr = parts[4].trim();
                    LocalDate intakeDate = LocalDate.parse(dateStr, dateFormatter);

                    // 6) Map each row to Animal object
                    Animal animal = new Animal(name, species, age, vaccinated, intakeDate);
                    allAnimals.add(animal);

                } catch (Exception e) {
                    log.warn("Skipping malformed row {}: {}", i + 1, e.getMessage());
                    skippedRows++;
                }
            }
        } catch (IOException e) {
            log.error("Error reading file: {}", e.getMessage());
        }

        return new ImportResult(allAnimals, skippedRows);
    }
}
