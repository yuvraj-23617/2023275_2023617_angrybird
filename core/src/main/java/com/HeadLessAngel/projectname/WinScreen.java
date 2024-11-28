package com.HeadLessAngel.projectname;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class WinScreen implements Screen {
    private final AngryBird game;
    private final Texture winTexture;
    private final Texture mainMenuButton;
    private final Texture nextLevelButton;
    private final Rectangle mainMenuBounds;
    private final Rectangle nextLevelBounds;

    public WinScreen(AngryBird game) {
        this.game = game;

        // Load textures
        winTexture = new Texture(Gdx.files.internal("you_won.png")); // Background image
        mainMenuButton = new Texture(Gdx.files.internal("menu.jpg")); // Main Menu button image
        nextLevelButton = new Texture(Gdx.files.internal("next level.png")); // Next Level button image

        // Define button bounds (adjust positions and sizes as needed)
        mainMenuBounds = new Rectangle(100, 150, 200, 100);
        nextLevelBounds = new Rectangle(100, 50, 200, 100);
    }

    @Override
    public void render(float delta) {
        handleInput(); // Handle button clicks

        // Clear the screen and draw the UI
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(winTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(mainMenuButton, mainMenuBounds.x, mainMenuBounds.y, mainMenuBounds.width, mainMenuBounds.height);
        game.batch.draw(nextLevelButton, nextLevelBounds.x, nextLevelBounds.y, nextLevelBounds.width, nextLevelBounds.height);
        game.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Convert touch coordinates

            // Handle Next Level button click
            if (nextLevelBounds.contains(touchX, touchY)) {
                int currentLevel = game.getCurrentLevel();
                if (currentLevel < 3) {
                    // Move to the next level
                    game.setCurrentLevel(currentLevel + 1);
                    game.setScreen(new GameScreen(game, currentLevel + 1));
                }
            }

            // Handle Main Menu button click
            else if (mainMenuBounds.contains(touchX, touchY)) {
                // Navigate to Stages Screen instead of Main Menu
                game.setScreen(new StagesScreen(game)); // Update screen transition here
            }
        }
    }

    @Override
    public void dispose() {
        winTexture.dispose();
        mainMenuButton.dispose();
        nextLevelButton.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
