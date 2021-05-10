package com.game.UI;

import com.game.logic.GameLogic;
import com.game.map.GameMap;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameFrame extends JFrame {
    // 菜单栏
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menuOperate = new JMenu("操作");
    private final JMenuItem clear = new JMenuItem("清空画面");
    private final JMenuItem random = new JMenuItem("随机生成画面");
    private final JMenuItem step = new JMenuItem("进行单步演化");
    private final JMenuItem start = new JMenuItem("开始不间断演化");
    private final JMenuItem stop = new JMenuItem("结束不间断演化");
    private final JMenu menuSetting = new JMenu("设置");
    private final JMenuItem width = new JMenuItem("宽度");
    private final JMenuItem height = new JMenuItem("高度");
    private final JMenuItem mortality = new JMenuItem("死亡率");
    private final JMenuItem speed = new JMenuItem("速度");

    // 按钮栏
    private final JToolBar toolBar = new JToolBar();
    private final JButton btnClear = new JButton("清空画面");
    private final JButton btnRandom = new JButton("随机生成画面");
    private final JButton btnStep = new JButton("进行单步演化");
    private final JButton btnStart = new JButton("开始不间断演化");
    private final JButton btnStop = new JButton("结束不间断演化");

    private final GameLogic gameLogic = new GameLogic();

    private int[][] gameMatrix;                 // 状态矩阵
    private JRadioButton[][] cellMatrix;        // 细胞矩阵


    public GameMap gameMap = new GameMap(35,45,2);
    private JPanel gamePanel = new JPanel();

    public GameFrame() {

        this.setTitle("生命游戏");
        this.setJMenuBar(menuBar);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image icon = toolkit.getImage("lifeGame.jpg");
        this.setIconImage(icon);
        this.setSize(800,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 初始化布局
        initButtonLayout();
        initGameLayout();
    }

    // 初始化按钮布局
    private void initButtonLayout() {
        // 菜单模块初始化
        menuOperate.add(clear);
        menuOperate.add(random);
        menuOperate.add(step);
        menuOperate.add(start);
        menuOperate.add(stop);
        menuBar.add(menuOperate);
        // 添加监听
        clear.addActionListener(e -> onClear());
        random.addActionListener(e -> onRandom());
        step.addActionListener(e -> onStep());
        start.addActionListener(e -> onStart());
        stop.addActionListener(e -> onStop());

        menuSetting.add(width);
        menuSetting.add(height);
        menuSetting.add(mortality);
        menuSetting.add(speed);
        menuBar.add(menuSetting);
        // 添加监听
        width.addActionListener(e -> onSetWidth());
        height.addActionListener(e ->onSetHeight());
        mortality.addActionListener(e -> onSetMortality());
        speed.addActionListener(e -> onSetSpeed());

        // 按钮模块初始化
        toolBar.add(btnClear);
        toolBar.add(btnRandom);
        toolBar.add(btnStep);
        toolBar.add(btnStart);
        toolBar.add(btnStop);
        getContentPane().add(toolBar, BorderLayout.NORTH);

        btnClear.addActionListener(e -> onClear());
        btnRandom.addActionListener(e -> onRandom());
        btnStep.addActionListener(e -> onStep());
        btnStart.addActionListener(e -> onStart());
        btnStop.addActionListener(e -> onStop());

        // 设置按钮状态
        stop.setEnabled(false);
        btnStop.setEnabled(false);
    }

    // 初始化游戏界面布局
    private void initGameLayout() {
        int rows = gameMap.getHeight();
        int cols = gameMap.getWidth();
        gamePanel = new JPanel();

        gamePanel.setSize(400,300);
        gamePanel.setLayout(new GridLayout(rows, cols));


        cellMatrix = new JRadioButton[rows][cols];
        gameMatrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JRadioButton radioButton = new JRadioButton();
                radioButton.setSize(30, 30);
                radioButton.setSelected(false);
                gameMatrix[i][j] = gameMap.getStatue(i, j);
                cellMatrix[i][j] = radioButton;
                gamePanel.add(radioButton);
            }
        }
        getContentPane().add(gamePanel, BorderLayout.CENTER);
        updateMap();
    }

    // 更新游戏地图
    public void updateMap() {
        gameMatrix = gameMap.getGameMap();
        for (int i = 0; i < gameMatrix.length; i++) {
            for (int j = 0; j < gameMatrix[i].length; j++) {
                cellMatrix[i][j].setSelected(gameMatrix[i][j] == 1);
            }
        }
        gamePanel.updateUI();
    }

    // 清空画面
    private void onClear() {
        for (int[] matrix : gameMatrix)
            Arrays.fill(matrix, 0);
        gameMap.setStatue(gameMatrix);
        updateMap();
    }

    // 随机生成画面
    private void onRandom() {
        for (int i = 0; i < gameMatrix.length; i++) {
            for (int j = 0; j < gameMatrix[i].length; j++) {
                gameMatrix[i][j] = new Random().nextInt(2);
            }
        }
        gameMap.setStatue(gameMatrix);
        updateMap();
    }

    // 单步演化
    private void onStep() {
        for (int i = 0; i < gameMatrix.length; i++) {
            for (int j = 0; j < gameMatrix[i].length; j++) {
                gameMatrix[i][j] = cellMatrix[i][j].isSelected()? 1:0;
            }
        }
        gameMap.setStatue(gameMatrix);
        gameLogic.game_cycle(gameMap);
        updateMap();
    }

    // 开始演化
    private void onStart() {
        GameLogic.gameStatue = GameLogic.START;
        new Thread(new GameTask()).start();
        // 设置按钮状态
        start.setEnabled(false);
        btnStart.setEnabled(false);
        stop.setEnabled(true);
        btnStop.setEnabled(true);
    }

    // 停止演化
    private void onStop() {
        GameLogic.gameStatue = GameLogic.STOP;
        // 设置按钮状态
        start.setEnabled(true);
        btnStart.setEnabled(true);
        stop.setEnabled(false);
        btnStop.setEnabled(false);
    }

    // 设置地图宽度
    private void onSetWidth() {
        String str = JOptionPane.showInputDialog("请输入需要设置的宽度");
        gameMap.setWidth(Integer.parseInt(str));
        remove(gamePanel);
        initGameLayout();
    }

    // 设置地图高度
    private void onSetHeight() {
        String str = JOptionPane.showInputDialog("请输入需要设置的高度");
        gameMap.setHeight(Integer.parseInt(str));
        remove(gamePanel);
        initGameLayout();
    }

    // 设置死亡率
    private void onSetMortality() {
        String str = JOptionPane.showInputDialog("请输入需要设置的死亡率");
        gameMap.setMortality(Integer.parseInt(str));
    }

    // 设置演化速度
    private void onSetSpeed() {
        String str = JOptionPane.showInputDialog("请输入需要设置的演化速度");
        gameMap.setSpeed(Integer.parseInt(str));
    }

    // 程序入口
    public static void main(String[] args) {
        new GameFrame();
    }

    // 游戏线程类
    private class GameTask implements Runnable {
        // 一个生命周期
        @Override
        public void run() {
            while (GameLogic.gameStatue) {
                onStep();
                try {
                    TimeUnit.MILLISECONDS.sleep(1000 / gameMap.getSpeed());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
