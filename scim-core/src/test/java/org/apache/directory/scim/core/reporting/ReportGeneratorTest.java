package org.apache.directory.scim.core.reporting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportGeneratorTest {

  private ReportGenerator reportGenerator;
  private final List<String> sampleData = Arrays.asList("data_record_1", "data_record_2");

  @TempDir
  Path tempDirectory;

  @BeforeEach
  void setUp() {
    reportGenerator = new ReportGenerator(tempDirectory);
  }

  @Test
  void stageReportData_forStandardCategory_shouldSucceedOnAllPlatforms() {
    assertDoesNotThrow(() -> {
      Path reportFile = reportGenerator.stageReportData("SALES", sampleData);
      assertTrue(Files.exists(reportFile), "Staging file for SALES should be created.");
      System.out.println("-> SUCCESS: Correctly staged report for 'SALES'.");
    }, "Staging a report for a standard category should not fail.");
  }

  @Test
  void stageReportData_forReservedCategoryName_shouldFailOnWindows() {
    String problematicCategoryId = "CON";

    assertDoesNotThrow(() -> {
      Path reportFile = reportGenerator.stageReportData(problematicCategoryId, sampleData);
      assertTrue(Files.exists(reportFile), "Staging file for " + problematicCategoryId + " should be created.");
      System.out.println("-> SUCCESS: Correctly staged report for '" + problematicCategoryId + "'. (Running on macOS or Linux).");
    }, "On Windows, this assertion will fail with an IOException: The filename, directory name, or volume label syntax is incorrect.");
  }
}
