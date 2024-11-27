package com.HeadLessAngel.projectname;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuScreen implements Screen {
    private final AngryBird game;
    private final Texture background;
    private final Texture play;
    private final Texture exit;
    // Rectangle bounds for buttons
    private final Rectangle playBounds;
    private final Rectangle exitBounds;

    public MainMenuScreen(AngryBird game) {
        this.game = game;
        background = new Texture(Gdx.files.internal("redscreen.png"));
        play = new Texture(Gdx.files.internal("play.png"));
        exit = new Texture(Gdx.files.internal("exit.png"));

        // Initialize button bounds
        playBounds = new Rectangle(225, 350, 400, 300);
        exitBounds = new Rectangle(610, 350, 400, 300);
    }

    @Override
    public void render(float delta) {
        // Handle input
        handleInput();

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        // Draw background to fill screen
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Draw buttons with specified dimensions
        game.batch.draw(play, playBounds.x, playBounds.y, playBounds.width, playBounds.height);
        game.batch.draw(exit, exitBounds.x, exitBounds.y, exitBounds.width, exitBounds.height);
        game.batch.end();
    }

    private void handleInput() {
        // Check if user touched/clicked the screen
        if (Gdx.input.justTouched()) {
            // Get the touch coordinates
            float touchX = Gdx.input.getX();
            // Flip Y coordinate because OpenGL coordinates start from bottom-left
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Check if play button was clicked
            if (playBounds.contains(touchX, touchY)) {
                // Change to stages screen
                game.setScreen(new StagesScreen(game));
                dispose();
            }
            // Check if exit button was clicked
            else if (exitBounds.contains(touchX, touchY)) {
                // Exit the game
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        play.dispose();
        exit.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
