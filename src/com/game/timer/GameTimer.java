package com.game.timer;

import com.game.UI.GameFrame;
import com.game.map.GameMap;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends Timer {
    // 动画默认间隔200ms
    private static final int DEFAULT_DURATION = 200;

    // 一个生命周期
    private int interval = DEFAULT_DURATION;
    private GameMap gameMap;
    private GameFrame gameFrame;

    public GameTimer(GameMap gameMap, GameFrame game) {
        this.gameMap = gameMap;
    }

    public void start() {
        Timer timer = new Timer();
        timer.schedule(new Task(), 0, interval);
        timer.cancel();
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    private class Task extends TimerTask {
        public void run() {

        }
    }
}
