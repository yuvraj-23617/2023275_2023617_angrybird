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
        winTexture = new Texture(Gdx.files.internal("you_won.png")); // Ensure this texture is available
        mainMenuButton = new Texture(Gdx.files.internal("menu.jpg")); // Path to menu button
        nextLevelButton = new Texture(Gdx.files.internal("next level.png")); // Path to next level button

        // Button bounds (adjust according to your UI)
        mainMenuBounds = new Rectangle(100, 150, 200, 100);
        nextLevelBounds = new Rectangle(100, 50, 200, 100);
    }

    @Override
    public void render(float delta) {
        // Handle input
        handleInput();

        // Clear the screen and draw background
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
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Flip Y-axis

            // Handle next level button click
            if (nextLevelBounds.contains(touchX, touchY)) {
                int currentLevel = game.getCurrentLevel();
                if (currentLevel < 3) {
                    // Move to the next level
                    game.setCurrentLevel(currentLevel + 1);
                    game.setScreen(new GameScreen(game, currentLevel + 1));
                }
            }

            // Handle main menu button click
            else if (mainMenuBounds.contains(touchX, touchY)) {
                game.setScreen(new MainMenuScreen(game)); // Transition to main menu
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
