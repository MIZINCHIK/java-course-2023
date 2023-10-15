package edu.hw2;

import edu.hw2.Task4.CallingInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Calling info from this class and method")
    void thisIsTheMethodName() {
        CallingInfo info = CallingInfo.callingInfo();
        assertThat(info.className()).isEqualTo(this.getClass().getName());
        assertThat(info.methodName()).isEqualTo("thisIsTheMethodName");
    }
}
