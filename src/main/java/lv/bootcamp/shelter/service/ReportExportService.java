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
import java.util.Map;

public class ReportExportService {

    public void writeReport(Path outputPath, ShelterReportData reportData) {
        try {
            Files.createDirectories(outputPath.getParent());
        } catch (IOException ignored) {}

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            writer.write("=== SHELTER UPLOAD REPORT ===");
            writer.newLine();
            String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("Generated at: " + formattedDate);
            writer.newLine();
            writer.newLine();

            writer.write("--- Import Totals ---");
            writer.newLine();
            writer.write("Successfully imported: " + reportData.importResult().allAnimals().size());
            writer.newLine();
            writer.write("Skipped rows: " + reportData.importResult().skippedRows());
            writer.newLine();
            writer.newLine();

            writer.write("--- Unique Species ---");
            writer.newLine();
            writer.write(String.join(", ", reportData.uniqueSpecies()));
            writer.newLine();
            writer.newLine();

            writer.write("--- Vaccinated Per Species ---");
            writer.newLine();
            if (reportData.vaccinatedPerSpecies().isEmpty()) {
                writer.write("None");
                writer.newLine();
            } else {
                for (Map.Entry<Object, Object> entry : reportData.vaccinatedPerSpecies().entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue());
                    writer.newLine();
                }
            }
            writer.newLine();

            writer.write("--- Oldest Animal Per Species ---");
            writer.newLine();
            if (reportData.oldestAnimalPerSpecies().isEmpty()) {
                writer.write("None");
                writer.newLine();
            } else {
                for (Map.Entry<Object, Object> entry : reportData.oldestAnimalPerSpecies().entrySet()) {
                    Animal animal = (Animal) entry.getValue();
                    writer.write(entry.getKey() + ": " + animal.getName() + " (Age: " + animal.getAge() + ")");
                    writer.newLine();
                }
            }
            writer.newLine();

            writer.write("--- Animals Needing Vet Input ---");
            writer.newLine();
            if (reportData.animalsNeedingVetInput().isEmpty()) {
                writer.write("None");
                writer.newLine();
            } else {
                writer.write(String.join(", ", reportData.animalsNeedingVetInput()));
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Failed to write report file: " + e.getMessage());
        }
    }
}
