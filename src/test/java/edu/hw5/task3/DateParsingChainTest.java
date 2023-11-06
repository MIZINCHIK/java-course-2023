package edu.hw5.task3;

import edu.hw5.task3.parsers.DateParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DateParsingChainTest {
    private static final String CORRECT_DATE_FORMAT1 = "CorrectDate1";
    private static final String CORRECT_DATE_FORMAT2 = "CorrectDate2";
    private static final String INCORRECT_DATE_FORMAT1 = "IncorrectDate1";
    private static final String INCORRECT_DATE_FORMAT2 = "IncorrectDate2";
    private static final String INCORRECT_DATE_FORMAT3 = "IncorrectDate3";
    private DateParsingChain parsingChain;
    private DateParser parser1;
    private DateParser parser2;

    @BeforeEach
    void setUpMocks() {
        parser1 = Mockito.mock(DateParser.class);
        parser2 = Mockito.mock(DateParser.class);
        parsingChain = new DateParsingChain();

        Mockito.when(parser1.canParseDate(CORRECT_DATE_FORMAT1)).thenReturn(true);
        Mockito.when(parser1.canParseDate(INCORRECT_DATE_FORMAT1)).thenReturn(false);
        Mockito.when(parser1.canParseDate(CORRECT_DATE_FORMAT2)).thenReturn(false);
        Mockito.when(parser1.canParseDate(INCORRECT_DATE_FORMAT2)).thenReturn(false);
        Mockito.when(parser1.canParseDate(INCORRECT_DATE_FORMAT3)).thenReturn(false);
        Mockito.when(parser2.canParseDate(CORRECT_DATE_FORMAT1)).thenReturn(false);
        Mockito.when(parser2.canParseDate(INCORRECT_DATE_FORMAT1)).thenReturn(false);
        Mockito.when(parser2.canParseDate(CORRECT_DATE_FORMAT2)).thenReturn(true);
        Mockito.when(parser2.canParseDate(INCORRECT_DATE_FORMAT2)).thenReturn(false);
        Mockito.when(parser2.canParseDate(INCORRECT_DATE_FORMAT3)).thenReturn(false);

        Mockito.when(parser1.parseDate(CORRECT_DATE_FORMAT1)).thenReturn(LocalDate.now());
        Mockito.when(parser2.parseDate(CORRECT_DATE_FORMAT2)).thenReturn(LocalDate.now().plusDays(1));

        parsingChain.addParserToChain(parser1);
        parsingChain.addParserToChain(parser2);
    }

    @Test
    @DisplayName("Completely incorrect strings can't be parsed")
    void parseDate_whenIncorrectFormat_thenEmpty() {
        assertThat(parsingChain.parseDate(INCORRECT_DATE_FORMAT3)).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("Correct dates can be parsed")
    void parseDate_whenCorrectFormat_thenDate() {
        assertThat(parsingChain.parseDate(CORRECT_DATE_FORMAT1))
            .isEqualTo(Optional.of(LocalDate.now()));
        assertThat(parsingChain.parseDate(CORRECT_DATE_FORMAT2))
            .isEqualTo(Optional.of(LocalDate.now().plusDays(1)));
    }
}
