package com.HeadLessAngel.projectname;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class WinScreen implements Screen {
    private final AngryBird game;
    private final Texture winTexture;

    public WinScreen(AngryBird game) {
        this.game = game;
        winTexture = new Texture("you_won.png"); // Add this to assets
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(winTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
    }

    @Override
    public void dispose() {
        winTexture.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
