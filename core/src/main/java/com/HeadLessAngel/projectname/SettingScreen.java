package com.HeadLessAngel.projectname;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SettingScreen implements Screen {
    private static final int WORLD_WIDTH = 1300;
    private static final int WORLD_HEIGHT = 975;

    private final AngryBird game;
    private final int level;
    private final Texture background;
    private final Texture resumeTexture;
    private final Texture restartTexture;
    private final Texture exitTexture;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    private final Rectangle resumeBounds;
    private final Rectangle restartBounds;
    private final Rectangle exitBounds;

    public SettingScreen(AngryBird game, int level) {
        this.game = game;
        this.level = level;
        background = new Texture("redscreen.png");
        resumeTexture = new Texture("resume.png");
        restartTexture = new Texture("restart.png");
        exitTexture = new Texture("exitt.png");
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);

        resumeBounds = new Rectangle(425, 575, 450, 225);
        restartBounds = new Rectangle(425, 375, 450, 225);
        exitBounds = new Rectangle(425, 175, 450, 225);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        batch.draw(resumeTexture, resumeBounds.x, resumeBounds.y, resumeBounds.width, resumeBounds.height);
        batch.draw(restartTexture, restartBounds.x, restartBounds.y, restartBounds.width, restartBounds.height);
        batch.draw(exitTexture, exitBounds.x, exitBounds.y, exitBounds.width, exitBounds.height);
        batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (resumeBounds.contains(touchPos.x, touchPos.y)) {
                returnToLevel();
            } else if (restartBounds.contains(touchPos.x, touchPos.y)) {
                restartLevel();
            } else if (exitBounds.contains(touchPos.x, touchPos.y)) {
                exitLevel();
            }
        }
    }

    private void returnToLevel() {
        game.setScreen(new GameScreen(game, level));
        dispose();
    }

    private void restartLevel() {
        game.setScreen(new GameScreen(game, level));
        dispose();
    }

    private void exitLevel() {
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        background.dispose();
        resumeTexture.dispose();
        restartTexture.dispose();
        exitTexture.dispose();
        batch.dispose();
    }
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
