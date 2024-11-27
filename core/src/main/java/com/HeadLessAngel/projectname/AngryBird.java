package com.HeadLessAngel.projectname;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AngryBird extends Game {
    public SpriteBatch batch;
    private StartScreen startScreen;
    private MainMenuScreen mainMenuScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();
        startScreen = new StartScreen(this);
        mainMenuScreen = new MainMenuScreen(this);
        setScreen(startScreen);
    }

    public void switchToMainMenu() {
        setScreen(mainMenuScreen);
    }

    @Override
    public void dispose() {
        batch.dispose();
        startScreen.dispose();
        mainMenuScreen.dispose();
    }
}
