package edu.project3;

import edu.project3.reports.LogReport;
import edu.project3.view.Adoc;
import edu.project3.view.MarkDown;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import static edu.project3.streams.LogStreams.createLogReport;

public class LogAnalyzerEntry {
    private static final String PATH_OPTION = "path";
    private static final String PATH_ARGUMENT = PATH_OPTION;
    private static final String PATH_DESCRIPTION =
        "Specify path to logs as URL or a path to local log(s) (with wildcard support)."
            + " Malformed URLs and incorrect paths are ignored";
    private static final String FORMAT_OPTION = "format";
    private static final String FORMAT_ARGUMENT = "file format";
    private static final String FORMAT_DESCRIPTION =
        "Choose between markdown and adoc output";
    private static final String START_DATE_OPTION = "from";
    private static final String START_DATE_ARGUMENT = "starting date";
    private static final String START_DATE_DESCRIPTION = "Specify starting timestamp for logs analysis in ISO 8601";
    private static final String END_DATE_OPTION = "to";
    private static final String END_DATE_ARGUMENT = "ending date";
    private static final String END_DATE_DESCRIPTION = "Specify ending timestamp for logs analysis in ISO 8601";
    private static final String HELP_OPTION = "h";
    private static final String HELP_LONG = "help";
    private static final String HELP_DESCRIPTION = "Show help";
    private static final String CMD_SYNTAX_APP_CALL = "java -jar <path-to-log-analyzer.jar>";
    private static final String OPTION_PARSING_FAILED = "Failed to parse options";
    private static final String DATE_PARSING_FAILED = "Failed to parse date option";
    private static final String ADOC = "adoc";
    public static final Path SUBFOLDER = Path.of(System.getProperty("user.dir")).resolve("NGINX_log_reports");
    public static final String REPORT_FILE_MD = "report.md";
    public static final String REPORT_FILE_ADOC = "report.adoc";
    public static final String CREATING_FOLDER_EXCEPTION = "Couldn't create a directory for the report";
    public static final String CREATING_FILE_EXCEPTION = "Couldn't create a file for the report";
    public static final String WRITING_FILE_EXCEPTION = "Couldn't write to file";

    private LogAnalyzerEntry() {
        throw new IllegalStateException();
    }

    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) {
        Options options = new Options();
        Option pathOption =
            Option.builder(null).longOpt(PATH_OPTION).argName(PATH_ARGUMENT).hasArgs().desc(PATH_DESCRIPTION).required()
                .build();
        Option startDateOption = Option.builder(null).longOpt(START_DATE_OPTION).argName(START_DATE_ARGUMENT).hasArg()
            .desc(START_DATE_DESCRIPTION).required(false).build();
        Option endDateOption =
            Option.builder(null).longOpt(END_DATE_OPTION).argName(END_DATE_ARGUMENT).hasArg().desc(END_DATE_DESCRIPTION)
                .required(false).build();
        Option helpOption =
            Option.builder(HELP_OPTION).longOpt(HELP_LONG).hasArg(false).desc(HELP_DESCRIPTION).required(false).build();
        Option fileFormatOption =
            Option.builder(null).longOpt(FORMAT_OPTION).argName(FORMAT_ARGUMENT).hasArg().desc(FORMAT_DESCRIPTION)
                .required(false).build();
        options.addOption(pathOption).addOption(startDateOption).addOption(endDateOption).addOption(helpOption)
            .addOption(fileFormatOption);
        HelpFormatter helpPrinter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            helpPrinter.printHelp(CMD_SYNTAX_APP_CALL, options);
            throw new RuntimeException(OPTION_PARSING_FAILED, e);
        }
        if (cmd.hasOption(helpOption)) {
            helpPrinter.printHelp(CMD_SYNTAX_APP_CALL, options);
            return;
        }
        String[] paths = cmd.getOptionValues(pathOption);
        OffsetDateTime startDate = OffsetDateTime.MIN;
        OffsetDateTime endDate = OffsetDateTime.MAX;
        boolean markdown = true;
        if (cmd.hasOption(startDateOption)) {
            try {
                startDate = OffsetDateTime.parse(cmd.getOptionValue(startDateOption));
            } catch (DateTimeParseException e) {
                helpPrinter.printHelp(CMD_SYNTAX_APP_CALL, options);
                throw new RuntimeException(DATE_PARSING_FAILED, e);
            }
        }
        if (cmd.hasOption(endDateOption)) {
            try {
                endDate = OffsetDateTime.parse(cmd.getOptionValue(endDateOption));
            } catch (DateTimeParseException e) {
                helpPrinter.printHelp(CMD_SYNTAX_APP_CALL, options);
                throw new RuntimeException(DATE_PARSING_FAILED);
            }
        }
        if (cmd.hasOption(fileFormatOption)) {
            String fileFormat = cmd.getOptionValue(fileFormatOption);
            if (fileFormat.equals(ADOC)) {
                markdown = false;
            }
        }
        LogReport report = createLogReport(paths, startDate, endDate);
        String reportString = markdown ? MarkDown.formatReport(report) : Adoc.formatReport(report);
        try {
            Files.createDirectories(SUBFOLDER);
        } catch (IOException e) {
            throw new RuntimeException(CREATING_FOLDER_EXCEPTION, e);
        }
        Path file = SUBFOLDER.resolve(markdown ? REPORT_FILE_MD : REPORT_FILE_ADOC);
        if (!Files.exists(file)) {
            try {
                Files.createFile(file);
            } catch (IOException e) {
                throw new RuntimeException(CREATING_FILE_EXCEPTION, e);
            }
        }
        try {
            Files.writeString(file, reportString);
        } catch (IOException e) {
            throw new RuntimeException(WRITING_FILE_EXCEPTION, e);
        }
    }
}
