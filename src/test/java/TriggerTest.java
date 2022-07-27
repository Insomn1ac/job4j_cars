import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TriggerTest {
    @Test
    public void when1Then1() {
        assertThat(new Trigger().trigger()).isEqualTo(1);
    }
}