package lv.bootcamp.shelter.service;

import lv.bootcamp.shelter.service.data.ShelterReportData;
import lv.bootcamp.shelter.model.Animal;

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
        try(BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)){

            writer.write("=== SHELTER UPLOAD REPORT ===");
            writer.newLine();
            String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("Generated at: " + formattedDate);
            writer.newLine();
            writer.newLine();
        }

        throw new UnsupportedOperationException("TODO: implement report export");
    }
}
