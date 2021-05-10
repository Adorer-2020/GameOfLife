package com.game.logic;

import com.game.map.GameMap;

import java.util.Arrays;
import java.util.Random;

public class GameLogic {

    public static final boolean STOP = false;
    public static final boolean START = true;

    public static boolean gameStatue;

    // 清空地图
    public void clearMap(GameMap gameMap) {
        int rows = gameMap.getHeight();
        int cols = gameMap.getWidth();
        int[][] nextMap = new int[rows][cols];
        for (int[] map : nextMap)
            Arrays.fill(map, 0);
        // 更新地图
        gameMap.setStatue(nextMap);
    }

    // 随机生成地图
    public void randomMap(GameMap gameMap) {
        int rows = gameMap.getHeight();
        int cols = gameMap.getWidth();
        int[][] nextMap = new int[rows][cols];
        for (int i = 0; i < nextMap.length; i++)
            for (int j = 0; j < nextMap[i].length; j++)
                nextMap[i][j] = new Random().nextInt(2);
        // 更新地图
        gameMap.setStatue(nextMap);
    }

    // 进行一个生命周期
    public void gameCycle(GameMap gameMap) {
        int rows = gameMap.getHeight();
        int cols = gameMap.getWidth();
        int[][] nextMap = new int[rows][cols];
        for (int i = 0; i < nextMap.length; i++) {
            for (int j = 0; j < nextMap[i].length; j++) {
                nextMap[i][j] = 0;
                int neighborCount = gameMap.getNeighborCount(i, j);
                if (neighborCount == 3)
                    nextMap[i][j] = 1;
                else if (neighborCount == 2)
                    nextMap[i][j] = gameMap.getStatue(i, j);
            }
        }
        // 更新地图
        gameMap.setStatue(nextMap);
    }
}
