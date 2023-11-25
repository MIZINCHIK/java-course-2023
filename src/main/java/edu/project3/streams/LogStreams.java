package edu.project3.streams;

import edu.project3.logs.Log;
import edu.project3.reports.LogReport;
import edu.project3.reports.LogReportBuilder;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import static edu.project3.streams.PathUtils.isValidUrl;
import static edu.project3.streams.PathUtils.resolveGlob;
import static edu.project3.streams.StringStreams.getFileLineByLineStream;
import static edu.project3.streams.StringStreams.getResponseBodyLineByLineStream;

public class LogStreams {
    private LogStreams() {
        throw new IllegalStateException();
    }

    private static Stream<Log> parseLogsInStream(Stream<String> strings) {
        return strings.filter(Log::looksLikeLog)
            .map(Log::parseLog)
            .filter(Optional::isPresent)
            .map(Optional::get);
    }

    private static Stream<Log> createStreamFromPathsAndUrls(Set<String> files, Set<String> urls) {
        Stream<Log> result = Stream.empty();
        for (String url : urls) {
            result = Stream.concat(
                result,
                parseLogsInStream(getResponseBodyLineByLineStream(URI.create(url)))
            );
        }
        for (String file : files) {
            result = Stream.concat(result, parseLogsInStream(getFileLineByLineStream(Path.of(file))));
        }
        return result;
    }

    private static Set<String> getValidUrls(String[] paths) {
        Set<String> urls = new HashSet<>();
        for (String path : paths) {
            if (isValidUrl(path)) {
                urls.add(path);
            }
        }
        return urls;
    }

    private static Set<String> getValidFiles(String[] paths) {
        Set<String> files = new HashSet<>();
        for (String path : paths) {
            try {
                List<Path> unwrappedFiles = resolveGlob(path).toList();
                for (Path file : unwrappedFiles) {
                    files.add(file.toString());
                }
            } catch (IOException ignored) {
            }
        }
        return files;
    }

    public static LogReport createLogReport(String[] paths, OffsetDateTime startDate, OffsetDateTime endDate) {
        Set<String> files = getValidFiles(paths);
        Set<String> urls = getValidUrls(paths);
        List<String> sources = new ArrayList<>();
        sources.addAll(files);
        sources.addAll(urls);
        LogReportBuilder builder = new LogReportBuilder(sources, startDate, endDate);
        createStreamFromPathsAndUrls(files, urls)
            .filter(log -> log.timeLocal().isAfter(startDate) || log.timeLocal().isEqual(startDate))
            .filter(log -> log.timeLocal().isBefore(endDate) || log.timeLocal().isEqual(endDate))
            .forEach(builder::considerLog);
        return builder.build();
    }
}
