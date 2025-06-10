package org.apache.directory.scim.core.reporting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Generates reports by staging data in temporary files.
 * This is a common pattern in data export or ETL (Extract, Transform, Load) processes.
 */
public class ReportGenerator {

  private final Path stagingDirectory;

  public ReportGenerator(Path stagingDirectory) {
    this.stagingDirectory = stagingDirectory;
  }

  /**
   * Generates a data report for a specific category. It first writes the raw data
   * to a temporary staging file before further processing would occur.
   *
   * The method does not sanitize the categoryId for reserved OS filenames,
   * creating a subtle, platform-specific bug.
   *
   * @param reportData The lines of data to be written to the report.
   * @return The path to the staging file created for the report.
   * @throws IOException if the staging file cannot be created.
   */
  public Path stageReportData(List<String> reportData) throws IOException {
    String stagingFileName = "AUX";
    Path stagingFilePath = this.stagingDirectory.resolve(stagingFileName);

    Files.write(
      stagingFilePath,
      reportData,
      StandardCharsets.UTF_8,
      StandardOpenOption.CREATE,
      StandardOpenOption.TRUNCATE_EXISTING
    );

    return stagingFilePath;
  }
}
