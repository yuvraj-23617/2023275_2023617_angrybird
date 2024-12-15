package com.HeadLessAngel.projectname;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class AngryBird extends Game {
    public SpriteBatch batch;
    private StartScreen startScreen;
    private MainMenuScreen mainMenuScreen;
    private WinScreen winScreen;
    private LoseScreen loseScreen;
    private int currentLevel = 1;

    // List to hold birds
    private Array<Bird> birds;

    @Override
    public void create() {
        batch = new SpriteBatch();
        startScreen = new StartScreen(this);
        mainMenuScreen = new MainMenuScreen(this);
        winScreen = new WinScreen(this);
        loseScreen = new LoseScreen(this);
        birds = new Array<>();

        initializeBirds();
        setScreen(startScreen);
    }

    private void initializeBirds() {
        // Initialize three birds with specific positions and properties
        birds.add(new Bird(100, 200, "bird1.png")); // Example positions
        birds.add(new Bird(150, 200, "bird2.png"));
        birds.add(new Bird(200, 200, "bird3.png"));
    }

    public Array<Bird> getBirds() {
        return birds;
    }

    public void renderBirds() {
        batch.begin();
        for (Bird bird : birds) {
            bird.render(batch);
        }
        batch.end();
    }

    public void updateBirds(float delta) {
        for (Bird bird : birds) {
            bird.update(delta);
        }
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
        for (Bird bird : birds) {
            bird.dispose();
        }
    }
}
