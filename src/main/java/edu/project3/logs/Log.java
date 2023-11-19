package edu.project3.logs;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.net.InetAddress.getByName;
import static java.time.format.DateTimeFormatter.ofPattern;

@SuppressWarnings("MultipleStringLiterals")
public record Log(InetAddress remoteAddress, String remoteUser, OffsetDateTime timeLocal, String requestMethod,
                  String resource, String httpVersion, Integer code, long bytesSent, String httpReferer,
                  String httpUserAgent) {
    private static final String REMOTE_ADDR = "([^ ]*)";
    private static final String REMOTE_USER = "(.*)";
    private static final String TIME_LOCAL = "(\\d{2}\\/\\w{3}\\/\\d+:\\d{2}:\\d{2}:\\d{2} [+-]\\d{4})";
    private static final String REQUEST =
        "(GET|HEAD|POST|PUT|DELETE|CONNECT|OPTIONS|TRACE|PATCH) ([^\"]*) (HTTP\\/[^\"]*)";
    private static final String STATUS = "(\\d{3})";
    private static final String BODY_BYTES_SENT = "(\\d+)";
    private static final String HTTP_REFERER = "([^\"]*)";
    private static final String HTTP_USER_AGENT = "([^\"]*)";
    private static final String FINAL_REGEX =
        "^" + REMOTE_ADDR + " - " + REMOTE_USER + " \\[" + TIME_LOCAL + "\\] \"" + REQUEST + "\" " + STATUS + " "
            + BODY_BYTES_SENT + " \"" + HTTP_REFERER + "\" \"" + HTTP_USER_AGENT + "\"$";
    private static final Pattern PATTERN = Pattern.compile(FINAL_REGEX);
    private static final String DATE_FORMAT = "d/LLL/u:HH:mm:ss Z";

    public static boolean looksLikeLog(String candidate) {
        return candidate.matches(FINAL_REGEX);
    }

    @SuppressWarnings("MagicNumber")
    public static Optional<Log> parseLog(String log) {
        Matcher matcher = PATTERN.matcher(log);
        if (!matcher.find()) {
            return Optional.empty();
        }
        InetAddress remoteAddress;
        try {
            remoteAddress = getByName(matcher.group(1));
        } catch (UnknownHostException e) {
            remoteAddress = null;
        }
        String remoteUser = matcher.group(2);
        OffsetDateTime timeLocal = null;
        try {
            timeLocal = OffsetDateTime.parse(matcher.group(3), ofPattern(DATE_FORMAT));
        } catch (DateTimeParseException ignored) {
        }
        String requestMethod = matcher.group(4);
        String resource = matcher.group(5);
        String httpVersion = matcher.group(6);
        int code = -1;
        try {
            code = Integer.parseInt(matcher.group(7));
        } catch (NumberFormatException ignored) {
        }
        long bytesSent = -1;
        try {
            bytesSent = Long.parseLong(matcher.group(8));
        } catch (NumberFormatException ignored) {
        }
        String httpReferer = matcher.group(9);
        String httpUserAgent = matcher.group(10);
        if (remoteAddress == null || timeLocal == null || code < 0 || bytesSent < 0) {
            return Optional.empty();
        }
        return Optional.of(new Log(
            remoteAddress,
            remoteUser,
            timeLocal,
            requestMethod,
            resource,
            httpVersion,
            code,
            bytesSent,
            httpReferer,
            httpUserAgent
        ));
    }
}
