package array.max_consecutive_gap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeTest {

    private final Code code = new Code();

    @Test
    void returnsFiveForMaxGapInSortedForm() {
        assertEquals(5, code.maximumGap(new int[]{1, 10, 5}));
    }

    @Test
    void returnsOneWhenDuplicateValuePresent() {
        assertEquals(1, code.maximumGap(new int[]{10, 9, 10}));
    }
}
