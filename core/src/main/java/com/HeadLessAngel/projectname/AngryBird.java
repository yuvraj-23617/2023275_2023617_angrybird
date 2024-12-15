package com.HeadLessAngel.projectname;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AngryBird extends Game {
    public SpriteBatch batch;
    private StartScreen startScreen;
    private MainMenuScreen mainMenuScreen;
    private WinScreen winScreen;
    private LoseScreen loseScreen;
    private int currentLevel = 1;

    @Override
    public void create() {
        batch = new SpriteBatch();
        startScreen = new StartScreen(this);
        mainMenuScreen = new MainMenuScreen(this);
        winScreen = new WinScreen(this);
        loseScreen = new LoseScreen(this);
        setScreen(startScreen);
    }

    public void switchToMainMenu() {
        setScreen(mainMenuScreen);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    public void switchToWinScreen() {
        setScreen(winScreen);
    }

    public void switchToLoseScreen() {
        setScreen(loseScreen);
    }

    @Override
    public void dispose() {
        batch.dispose();
        startScreen.dispose();
        mainMenuScreen.dispose();
        winScreen.dispose();
        loseScreen.dispose();
    }
}
