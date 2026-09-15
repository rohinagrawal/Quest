package array.max_abs_distance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeTest {

    private final Code code = new Code();

    @Test
    void returnsFiveForMixedSignArray() {
        assertEquals(5, code.maxArr(new int[]{1, 3, -1}));
    }

    @Test
    void returnsZeroForSingleElementArray() {
        assertEquals(0, code.maxArr(new int[]{2}));
    }
}
