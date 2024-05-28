package spw4.connectfour;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.junit.jupiter.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConnectFourImplTest {

    ConnectFour connectFour;

    @BeforeEach
    public void setup() {
        connectFour = new ConnectFourImpl(Player.red);
    }

    @Test
    void testCtorConnectFourImpl() {
        assertNotNull(connectFour);
    }

    @ParameterizedTest
    @ValueSource(strings = {"none", "red", "yellow"})
    void testGetPlayerOnTurn(String player) {
        Player playerOnTurn = Player.valueOf(player);
        ConnectFourImpl connectFour = new ConnectFourImpl(playerOnTurn);
        assertEquals(playerOnTurn, connectFour.getPlayerOnTurn());
    }

    @ParameterizedTest
    @MethodSource
    void testDropAndGetPlayerAt(int col, Player player) {
        ConnectFourImpl connectFour = new ConnectFourImpl(player);
        connectFour.drop(col);
        assertEquals(player, connectFour.getPlayerAt(0, col));
    }

    static Stream<Arguments> testDropAndGetPlayerAt() {
        return Stream.of(
                Arguments.of(0, Player.red),
                Arguments.of(1, Player.red),
                Arguments.of(2, Player.red),
                Arguments.of(3, Player.red),
                Arguments.of(4, Player.red),
                Arguments.of(5, Player.red),
                Arguments.of(6, Player.red),
                Arguments.of(0, Player.yellow),
                Arguments.of(1, Player.yellow),
                Arguments.of(2, Player.yellow),
                Arguments.of(3, Player.yellow),
                Arguments.of(4, Player.yellow),
                Arguments.of(5, Player.yellow),
                Arguments.of(6, Player.yellow),
                Arguments.of(0, Player.none),
                Arguments.of(1, Player.none),
                Arguments.of(2, Player.none),
                Arguments.of(3, Player.none),
                Arguments.of(4, Player.none),
                Arguments.of(5, Player.none),
                Arguments.of(6, Player.none)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testGetPlayerAtThrowsException(int row, int col, Player player) {
        ConnectFourImpl connectFour = new ConnectFourImpl(player);
        assertThrows(IllegalArgumentException.class, () -> connectFour.getPlayerAt(row, col));
    }

    static Stream<Arguments> testGetPlayerAtThrowsException() {
        return Stream.of(
                Arguments.of(-1, 0, Player.red),
                Arguments.of(7, 0, Player.red),
                Arguments.of(Integer.MAX_VALUE, 0, Player.red),
                Arguments.of(Integer.MIN_VALUE, 0, Player.red),
                Arguments.of(0, -1, Player.red),
                Arguments.of(0, 7, Player.red),
                Arguments.of(0, Integer.MAX_VALUE, Player.red),
                Arguments.of(0, Integer.MIN_VALUE, Player.red)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testDropThrowsException(int col, Player player) {
        ConnectFourImpl connectFour = new ConnectFourImpl(player);
        assertThrows(IllegalArgumentException.class, () -> connectFour.drop(col));
    }

    static Stream<Arguments> testDropThrowsException() {
        return Stream.of(
                Arguments.of(-1, Player.red),
                Arguments.of(7, Player.red),
                Arguments.of(Integer.MAX_VALUE, Player.red),
                Arguments.of(Integer.MIN_VALUE, Player.red)
        );
    }

    @Test
    void testPlayerSwitchAfterDrop() {
        connectFour.drop(0);
        assertEquals(Player.yellow, connectFour.getPlayerOnTurn());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void testStackingDrops(int col) {
        connectFour.drop(col);
        connectFour.drop(col);
        connectFour.drop(col);
        assertEquals(Player.red, connectFour.getPlayerAt(0, col));
        assertEquals(Player.yellow, connectFour.getPlayerAt(1, col));
        assertEquals(Player.red, connectFour.getPlayerAt(2, col));
    }

    @Test
    void testStackingToManyDropsInOneColumnNotChangingPlayer() {
        connectFour.drop(0);
        connectFour.drop(0);
        connectFour.drop(0);
        connectFour.drop(0);
        connectFour.drop(0);
        connectFour.drop(0);
        connectFour.drop(0);
        assertEquals(Player.red, connectFour.getPlayerOnTurn());
    }

    @Test
    void testReset() {
        connectFour.drop(0);
        connectFour.drop(1);
        connectFour.drop(2);
        connectFour.drop(3);
        connectFour.reset(Player.yellow);
        assertEquals(Player.yellow, connectFour.getPlayerOnTurn());
        assertEquals(Player.none, connectFour.getPlayerAt(0, 0));
        assertEquals(Player.none, connectFour.getPlayerAt(0, 1));
        assertEquals(Player.none, connectFour.getPlayerAt(0, 2));
        assertEquals(Player.none, connectFour.getPlayerAt(0, 3));
    }

    @Test
    void testIsGameOverHorizontal() {
        connectFour.drop(0);
        connectFour.drop(0);
        connectFour.drop(1);
        connectFour.drop(1);
        connectFour.drop(2);
        connectFour.drop(2);
        connectFour.drop(3);
        assertTrue(connectFour.isGameOver());
    }

    @Test
    void testIsGameNotOverHorizontal() {
        connectFour.drop(0);
        connectFour.drop(0);
        connectFour.drop(1);
        connectFour.drop(1);
        connectFour.drop(2);
        connectFour.drop(3);
        connectFour.drop(4);
        assertFalse(connectFour.isGameOver());
    }

    @Test
    void testIsGameOverDiagonalBottomLeftToTopRight() {
        connectFour.drop(0);
        connectFour.drop(1);
        connectFour.drop(1);
        connectFour.drop(2);
        connectFour.drop(2);
        connectFour.drop(3);
        connectFour.drop(2);
        connectFour.drop(3);
        connectFour.drop(4);
        connectFour.drop(3);
        connectFour.drop(3);

        assertTrue(connectFour.isGameOver());
    }

    @Test
    void testIsGameOverDiagonalTopLeftToBottomRight() {
        connectFour.drop(5);
        connectFour.drop(5);
        connectFour.drop(4);
        connectFour.drop(2);
        connectFour.drop(4);
        connectFour.drop(4);
        connectFour.drop(3);
        connectFour.drop(3);
        connectFour.drop(3);
        connectFour.drop(3);
        connectFour.drop(2);
        connectFour.drop(2);
        connectFour.drop(2);
        connectFour.drop(2);
        assertTrue(connectFour.isGameOver());
    }


    @Test
    void testIsGameOverVertical() {
        connectFour.drop(0);
        connectFour.drop(1);
        connectFour.drop(0);
        connectFour.drop(1);
        connectFour.drop(0);
        connectFour.drop(1);
        connectFour.drop(0);
        assertTrue(connectFour.isGameOver());
    }

    @Test
    void testIsGameNotOverVertical() {
        connectFour.drop(0);
        connectFour.drop(1);
        connectFour.drop(0);
        connectFour.drop(1);
        connectFour.drop(0);
        connectFour.drop(1);
        connectFour.drop(1);
        assertFalse(connectFour.isGameOver());
    }

    @Test
    void testGetWinner() {
        ConnectFourImpl connectFour = new ConnectFourImpl(Player.yellow);
        connectFour.drop(0);
        connectFour.drop(0);
        connectFour.drop(1);
        connectFour.drop(1);
        connectFour.drop(2);
        connectFour.drop(2);
        connectFour.drop(3);
        assertEquals(Player.yellow, connectFour.getWinner());
    }

    @Test
    void testGetWinnerNoWinner() {
        ConnectFourImpl connectFour = new ConnectFourImpl(Player.yellow);
        connectFour.drop(0);
        connectFour.drop(0);
        connectFour.drop(1);
        connectFour.drop(1);
        connectFour.drop(2);
        connectFour.drop(2);
        assertEquals(Player.none, connectFour.getWinner());
    }

    @Test
    void testToString() {
        connectFour.drop(3);
        connectFour.drop(2);
        connectFour.drop(1);
        String expected = """

                Player: YELLOW
                | .  .  .  .  .  .  . |
                | .  .  .  .  .  .  . |
                | .  .  .  .  .  .  . |
                | .  .  .  .  .  .  . |
                | .  .  .  .  .  .  . |
                | .  R  Y  R  .  .  . |
                """;
        assertEquals(expected, connectFour.toString());
    }
}
