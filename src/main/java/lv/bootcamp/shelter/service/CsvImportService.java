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

        String[] parts = line.split(",", -1);
        if(parts.length < 5){
            log.warn("Skipping malformed row {}: insufficient columns", i + 1);
            skippedRows++;
            continue;
        }
        try {
            String name = parts[0].trim();
            if (name.isEmpty()) throw new IllegalArgumentException("Name is required");

            String species = parts[1].trim();
            if (species.isEmpty()) throw new IllegalArgumentException("Species is required");

            Integer age = null;
            String ageStr = parts[2].trim();
            if (!ageStr.isEmpty()) {
                age = Integer.parseInt(ageStr);
                if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
            }

            String vaccinatedStr = parts[3].trim();
            if (!vaccinatedStr.equalsIgnoreCase("true") && !vaccinatedStr.equalsIgnoreCase("false")) {
                throw new IllegalArgumentException("Invalid vaccinated value");
            }
            boolean vaccinated = Boolean.parseBoolean(vaccinatedStr);

            String dateStr = parts[4].trim();
            LocalDate intakeDate = LocalDate.parse(dateStr, dateFormatter);

            Animal animal = new Animal(name, species, age, vaccinated, intakeDate);
            allAnimals.add(animal);

        } catch (Exception e) {
            log.warn("Skipping malformed row {}: {}", i + 1, e.getMessage());
            skippedRows++;
        }
    }


        return new ImportResult(allAnimals, 0);
    }
}
