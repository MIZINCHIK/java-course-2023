package edu.hw3.task1;

import edu.hw3.task1.alphabet.Alphabet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class AtbashTest {
    @InjectMocks
    private Atbash atbash;
    @Mock
    private Alphabet alphabet;

    @Test
    @DisplayName("Encode a non-empty string")
    void encodeAtbash_nonEmptyString() {
        String targetString = "AAaazzZZHel";
        String correctAnswer = "ZZzzaaAASvo";
        Mockito.when(alphabet.mirrorSymbol('A')).thenReturn(Integer.valueOf('Z'));
        Mockito.when(alphabet.mirrorSymbol('a')).thenReturn(Integer.valueOf('z'));
        Mockito.when(alphabet.mirrorSymbol('z')).thenReturn(Integer.valueOf('a'));
        Mockito.when(alphabet.mirrorSymbol('Z')).thenReturn(Integer.valueOf('A'));
        Mockito.when(alphabet.mirrorSymbol('H')).thenReturn(Integer.valueOf('S'));
        Mockito.when(alphabet.mirrorSymbol('H')).thenReturn(Integer.valueOf('S'));
        Mockito.when(alphabet.mirrorSymbol('e')).thenReturn(Integer.valueOf('v'));
        Mockito.when(alphabet.mirrorSymbol('l')).thenReturn(Integer.valueOf('o'));
        assertThat(atbash.encodeAtbash(targetString)).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Encode an empty String")
    void encodeAtbash_emptyString() {
        String targetString = "";
        String correctAnswer = "";
        assertThat(atbash.encodeAtbash(targetString)).isEqualTo(correctAnswer);
    }
}
