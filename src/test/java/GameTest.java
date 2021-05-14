import UI.GameFrame;
import logic.GameLogic;
import map.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    GameMap map = new GameMap(5, 5, 2);
    GameLogic game = new GameLogic();
    GameFrame gameFrame;

    int[][] testMap =
            {
                    {0, 1, 0, 0, 0},
                    {1, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 0},
                    {0, 0, 0, 0, 0}
            };

    @BeforeEach
    void setMap() {
        map.setStatue(testMap);
    }

    @Test
    void getNeighborCount() {
        //Case 1: 顶点测试
        assertEquals(3, map.getNeighborCount(0, 0));
        //Case 2: 边界测试
        assertEquals(2, map.getNeighborCount(0, 2));
        //Case 3: 中间值测试
        assertEquals(2, map.getNeighborCount(2, 2));
        //Case 4: 外界测试
        assertEquals(-1, map.getNeighborCount(-1, -1));
    }

    @Test
    void gameCycle() {
        game.gameCycle(map);
        int[][] nextMap =
                {
                        {1, 1, 0, 0, 0},
                        {1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0}
                };
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(nextMap[i][j], map.getGameMap()[i][j]);
            }
        }
    }

    @Test
    void clearMap() {
        game.clearMap(map);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(0, map.getGameMap()[i][j]);
            }
        }
    }

    @Test
    void randomMap() {
        int[][] blankMap =
                {
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0}
                };
        map.setStatue(blankMap);
        game.randomMap(map);
        int numLive = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                numLive += map.getStatue(i, j);
            }
        }
        // 随机生成地图后地图上活细胞为 0 的概率基本为 0 ，因此可以将这种情况近似划分为测试不通过
        assertTrue(numLive > 0 && numLive < 25);

    }

    @Test
    public void testGameFrame() {
        //GameFrame gameFrame = //mock(GameFrame.class);
    }
}