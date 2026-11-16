package lv.bootcamp.shelter.service;

import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.service.data.ShelterReportData;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReportExportService {

    public void writeReport(Path outputPath, ShelterReportData reportData) {
        List<Animal> animals = reportData.importResult().allAnimals();
        int skippedRows = reportData.importResult().skippedRows();

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {

            writer.write("SHELTER UPLOAD REPORT");
            writer.newLine();
            String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("Generated at: " + formattedDate);
            writer.newLine();
            writer.newLine();

            writer.write("Import Totals");
            writer.newLine();
            writer.write("Successfully imported: " + animals.size());
            writer.newLine();
            writer.write("Skipped rows: " + skippedRows);
            writer.newLine();
            writer.newLine();

            List<String> uniqueSpecies = animals.stream()
                    .map(Animal::getSpecies)
                    .distinct()
                    .sorted()
                    .toList();

            writer.write("Unique Species");
            writer.newLine();
            writer.write(String.join(", ", uniqueSpecies));
            writer.newLine();
            writer.newLine();

            writer.write("Oldest Animal Per Species");
            writer.newLine();

            Map<String, Optional<Animal>> oldestPerSpecies = animals.stream()
                    .filter(a -> a.getAge() != null)
                    .collect(Collectors.groupingBy(
                            Animal::getSpecies,
                            Collectors.maxBy(Comparator.comparing(Animal::getAge))
                    ));

            if (oldestPerSpecies.isEmpty()) {
                writer.write("None");
                writer.newLine();
            } else {
                for (Map.Entry<String, Optional<Animal>> entry : oldestPerSpecies.entrySet()) {
                    if (entry.getValue().isPresent()) {
                        Animal animal = entry.getValue().get();
                        writer.write(entry.getKey() + ": " + animal.getName() + " (Age: " + animal.getAge() + ")");
                        writer.newLine();
                    }
                }
            }
            writer.newLine();

            List<String> needingVet = animals.stream()
                    .filter(a -> a.getAge() == null)
                    .map(a -> a.getName() + "(" + a.getSpecies() + ")")
                    .toList();

            writer.write("Animals Needing Vet Input");
            writer.newLine();
            if (needingVet.isEmpty()) {
                writer.write("None");
                writer.newLine();
            } else {
                writer.write(String.join(", ", needingVet));
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Failed to write report file: " + e.getMessage());
        }
    }
}
