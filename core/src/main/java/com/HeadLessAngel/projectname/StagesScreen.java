package com.HeadLessAngel.projectname;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class StagesScreen implements Screen {
    private final AngryBird game;
    private final Texture background;
    private final Texture stage1Button;
    private final Texture stage2Button;
    private final Texture stage3Button;
    private final Texture levelimage;
    private final Texture backButton;

    // Rectangle bounds for buttons
    private final Rectangle stage1Bounds;
    private final Rectangle stage2Bounds;
    private final Rectangle stage3Bounds;
    private final Rectangle levelimageBounds;
    private final Rectangle backBounds;

    public StagesScreen(AngryBird game) {
        this.game = game;

        // textures
        background = new Texture(Gdx.files.internal("redscreen.png"));
        stage1Button = new Texture(Gdx.files.internal("stage1.png"));
        stage2Button = new Texture(Gdx.files.internal("stage2.png"));
        stage3Button = new Texture(Gdx.files.internal("stage3.png"));
        levelimage = new Texture(Gdx.files.internal("LEVEL.png"));
        backButton = new Texture(Gdx.files.internal("back.png"));

        stage1Bounds = new Rectangle(100, 400, 300, 200);
        stage2Bounds = new Rectangle(450, 400, 300, 200);
        stage3Bounds = new Rectangle(800, 400, 300, 200);
        levelimageBounds = new Rectangle(450, 650, 300, 200);
        backBounds = new Rectangle(50, 50, 150, 100);
    }

    @Override
    public void render(float delta) {
        handleInput();

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        // background
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // buttons
        game.batch.draw(stage1Button, stage1Bounds.x, stage1Bounds.y, stage1Bounds.width, stage1Bounds.height);
        game.batch.draw(stage2Button, stage2Bounds.x, stage2Bounds.y, stage2Bounds.width, stage2Bounds.height);
        game.batch.draw(stage3Button, stage3Bounds.x, stage3Bounds.y, stage3Bounds.width, stage3Bounds.height);
        game.batch.draw(levelimage, levelimageBounds.x, levelimageBounds.y, levelimageBounds.width, levelimageBounds.height);
        game.batch.draw(backButton, backBounds.x, backBounds.y, backBounds.width, backBounds.height);

        game.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (stage1Bounds.contains(touchX, touchY)) {
                game.setScreen(new GameScreen(game, 1)); // level 1
                dispose();
            }
            else if (stage2Bounds.contains(touchX, touchY)) {
                game.setScreen(new GameScreen(game, 2)); // level 2
                dispose();
            }
            else if (stage3Bounds.contains(touchX, touchY)) {
                game.setScreen(new GameScreen(game, 3)); // level 3
                dispose();
            }
            else if (backBounds.contains(touchX, touchY)) {
                game.setScreen(new MainMenuScreen(game)); // Return to main menu
                dispose();
            }
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        stage1Button.dispose();
        stage2Button.dispose();
        stage3Button.dispose();
        backButton.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
