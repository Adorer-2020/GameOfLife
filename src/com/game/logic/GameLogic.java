package com.game.logic;

import com.game.map.GameMap;

public class GameLogic {
    public static final boolean STOP = false;
    public static final boolean START = true;

    public static boolean gameStatue;

    // 进行一个生命周期
    public void game_cycle(GameMap gameMap) {
        int[][] nextMap = new int [gameMap.getHeight()][gameMap.getWidth()];
        for(int i = 0; i < nextMap.length; i++) {
            for(int j = 0; j < nextMap[i].length; j++) {
                nextMap[i][j] = 0;
                int neighborCount = gameMap.getNeighborCount(i, j);
                if(neighborCount == 3)
                    nextMap[i][j] = 1;
                else if(neighborCount == 2) {
                    nextMap[i][j] = gameMap.getStatue(i, j);
                }
            }
        }
        // 更新地图
        gameMap.setStatue(nextMap);
    }
}
