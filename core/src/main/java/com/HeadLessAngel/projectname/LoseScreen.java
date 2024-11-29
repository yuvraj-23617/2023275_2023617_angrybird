package com.HeadLessAngel.projectname;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class LoseScreen implements Screen {
    private final AngryBird game;
    private final Texture loseTexture;
    private final Texture restartTexture;
    private final Texture mainMenuTexture;

    private final Rectangle restartBounds;
    private final Rectangle mainMenuBounds;

    public LoseScreen(AngryBird game) {
        this.game = game;
        loseTexture = new Texture("you_lost.png");
        restartTexture = new Texture("restart.png");
        mainMenuTexture = new Texture("menu.png");

        // Position buttons
        restartBounds = new Rectangle(350, 100, 250, 150);
        mainMenuBounds = new Rectangle(700, 100, 250, 150);
    }

    @Override
    public void render(float delta) {

        handleInput(); // Handle button clicks
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(loseTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(restartTexture, restartBounds.x, restartBounds.y, restartBounds.width, restartBounds.height);
        game.batch.draw(mainMenuTexture, mainMenuBounds.x, mainMenuBounds.y, mainMenuBounds.width, mainMenuBounds.height);
        game.batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            if (restartBounds.contains(touchPos.x, Gdx.graphics.getHeight() - touchPos.y)) {
                // Restart the last played level
                game.setScreen(new GameScreen(game, game.getCurrentLevel()));
                dispose();
            } else if (mainMenuBounds.contains(touchPos.x, Gdx.graphics.getHeight() - touchPos.y)) {
                // Return to main menu
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        }
    }


    @Override
    public void dispose() {
        loseTexture.dispose();
        restartTexture.dispose();
        mainMenuTexture.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
