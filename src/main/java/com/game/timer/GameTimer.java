package com.game.timer;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    // 动画默认间隔500ms
    private static final int DEFAULT_DURATION = 500;
    // 一个生命周期
    private int interval = DEFAULT_DURATION;

    Timer timer;

    // 改变时间间隔
    public void setInterval(int interval) {
        this.interval = interval;
    }

    // 计时器启动
    public void start() {
        timer = new Timer();
        timer.schedule(new Task(), 0, interval);
    }

    // 计时器停止
    public void stop() {
        timer.cancel();
    }

    // 任务
    private class Task extends TimerTask {
        public void run() {
            //new GameLogic().gameCycle(GameFrame.gameMap);
        }
    }
}
