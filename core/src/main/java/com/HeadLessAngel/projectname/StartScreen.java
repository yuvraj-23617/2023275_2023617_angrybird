package com.HeadLessAngel.projectname;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Input;

public class StartScreen implements Screen {
    private final AngryBird game;
    private final Texture backgroundImage;
    private final Texture enterButtonTexture;
    private final Rectangle enterButtonBounds;

    public StartScreen(AngryBird game) {
        this.game = game;
        backgroundImage = new Texture(Gdx.files.internal("AngryBird.png"));
        enterButtonTexture = new Texture(Gdx.files.internal("EnterGameButton.png"));
        enterButtonBounds = new Rectangle(500, 0, 250, 100);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(backgroundImage, 105, 0);
        game.batch.draw(enterButtonTexture,
            enterButtonBounds.x,
            enterButtonBounds.y,
            enterButtonBounds.width,
            enterButtonBounds.height);
        game.batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (enterButtonBounds.contains(mouseX, mouseY)) {
                game.switchToMainMenu();
            }
        }
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
        enterButtonTexture.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
