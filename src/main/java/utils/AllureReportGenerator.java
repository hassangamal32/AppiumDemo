package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AllureReportGenerator {
    private static final String ALLURE_RESULTS_DIR = "test-output/allure-results";
    private static final String ALLURE_REPORT_DIR = "test-output/allure-report";

    /**
     * Generates Allure report automatically after test execution
     */
    public static void generateAllureReport() {
        System.out.println("=== Starting Allure Report Generation ===");

        // Check if allure results directory exists and has files
        File resultsDir = new File(ALLURE_RESULTS_DIR);
        if (!resultsDir.exists() || !hasJsonFiles(resultsDir)) {
            System.out.println("No Allure results found in: " + resultsDir.getAbsolutePath());
            System.out.println("Please run tests first to generate results.");
            return;
        }

        System.out.println("Found Allure results in: " + resultsDir.getAbsolutePath());

        // Check if allure commandline tool is available
        if (isAllureCommandlineAvailable()) {
            System.out.println("Allure commandline tool is available");
            generateWithAllureCLI();
        } else {
            System.out.println("Allure commandline tool is not available");
            generateWithMaven();
        }
    }

    /**
     * Checks if directory contains JSON files (Allure results)
     */
    private static boolean hasJsonFiles(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
            return files != null && files.length > 0;
        }
        return false;
    }

    /**
     * Generates report using Allure CLI
     */
    private static void generateWithAllureCLI() {
        System.out.println("Generating report with Allure CLI...");

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("allure", "generate", ALLURE_RESULTS_DIR, "-o", ALLURE_REPORT_DIR, "--clean");

            // Redirect error stream to standard output
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Read and print the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("ALLURE: " + line);
            }

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("✓ Allure report generated successfully!");
                printReportLocation();
                openAllureReportInBrowser();
            } else {
                System.out.println("✗ Failed to generate Allure report. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error generating Allure report: " + e.getMessage());
        }
    }

    /**
     * Generates report using Maven plugin
     */
    private static void generateWithMaven() {
        System.out.println("Generating report with Maven plugin...");

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("mvn", "allure:report");
            processBuilder.directory(new File(System.getProperty("user.dir")));

            // Redirect error stream to standard output
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Read and print the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("MAVEN: " + line);
            }

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("✓ Allure report generated successfully with Maven!");
                printReportLocation();
            } else {
                System.out.println("✗ Failed to generate Allure report with Maven. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error generating Allure report with Maven: " + e.getMessage());
        }
    }

    /**
     * Prints the location of the generated report
     */
    private static void printReportLocation() {
        File reportDir = new File(ALLURE_REPORT_DIR);
        File indexHtml = new File(reportDir, "index.html");

        if (indexHtml.exists()) {
            System.out.println("Report available at: " + indexHtml.getAbsolutePath());
            System.out.println("Open this file in your browser to view the report.");
        } else {
            System.out.println("Report directory exists but index.html not found: " + reportDir.getAbsolutePath());
            System.out.println("Contents of report directory:");
            if (reportDir.exists() && reportDir.isDirectory()) {
                File[] files = reportDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        System.out.println("  - " + file.getName() + (file.isDirectory() ? " (dir)" : ""));
                    }
                }
            }
        }
    }

    /**
     * Opens the Allure report in the default web browser
     */
    private static void openAllureReportInBrowser() {
        File indexHtml = new File(ALLURE_REPORT_DIR, "index.html");

        if (!indexHtml.exists()) {
            System.out.println("Cannot open report: index.html not found");
            return;
        }

        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                Runtime.getRuntime().exec("cmd /c start \"\" \"" + indexHtml.getAbsolutePath() + "\"");
            } else if (os.contains("mac")) {
                // Mac
                Runtime.getRuntime().exec("open \"" + indexHtml.getAbsolutePath() + "\"");
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux
                Runtime.getRuntime().exec("xdg-open \"" + indexHtml.getAbsolutePath() + "\"");
            }
            System.out.println("Opening Allure report in browser...");
        } catch (IOException e) {
            System.out.println("Could not open report in browser: " + e.getMessage());
        }
    }

    /**
     * Checks if Allure commandline tool is available in the system
     */
    private static boolean isAllureCommandlineAvailable() {
        try {
            Process process = Runtime.getRuntime().exec("allure --version");
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    /**
     * Main method for manual report generation
     */
    public static void main(String[] args) {
        System.out.println("=== Manual Allure Report Generation ===");
        generateAllureReport();
    }
}