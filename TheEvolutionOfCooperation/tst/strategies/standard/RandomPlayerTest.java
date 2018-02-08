package strategies.standard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RandomPlayerTest {

    private RandomPlayer player = new RandomPlayer();

    @Test
    public void test_getPlayerType() {
        String expected = "Random";
        assertEquals(expected, player.getPlayerType());
    }

}