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
        // TODO Step 4:
        // 1) Write upload-report.txt in required format.
        // 2) Include generated date, imported/skipped totals.
        // 3) Include unique species and per-species breakdown.
        // 4) Include oldest animal per species.
        // 5) Include animalsNeedingVetInput as name(species), name2(species2).
        // 6) Use UTF-8 and try-with-resources.

        throw new UnsupportedOperationException("TODO: implement report export");
    }
}
