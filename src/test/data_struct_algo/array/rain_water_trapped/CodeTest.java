package array.rain_water_trapped;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeTest {

    private final Code code = new Code();

    @Test
    void trapsOneUnitBetweenSingleDip() {
        assertEquals(1, code.trap(new int[]{0, 1, 0, 2}));
    }

    @Test
    void trapsNothingWhenOnlyAscending() {
        assertEquals(0, code.trap(new int[]{1, 2}));
    }
}
