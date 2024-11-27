package com.HeadLessAngel.projectname;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
    private final AngryBird game;
    private final int level;

    private Texture background;
    private Texture groundTexture;
    private Texture birdTexture;
    private Texture pigTexture;

    private Rectangle groundBounds;
    private Rectangle birdBounds;
    private Rectangle pigBounds;

    private Vector2 birdPosition;
    private Vector2 birdVelocity;
    private Vector2 initialPosition;

    private final Vector2 gravity = new Vector2(0, -400); // Gravity force
    private boolean birdLaunched;
    private boolean pigAlive;
    private boolean birdAlive;
    private boolean isDragging;

    private int birdsLeft;
    private boolean levelFinished;

    public GameScreen(AngryBird game, int level) {
        this.game = game;
        this.level = level;

        // Initialize textures and positions based on the level
        loadLevelAssets(level);

        initialPosition = new Vector2(150, 300); // Initial position near the slingshot
        birdPosition = new Vector2(initialPosition);
        birdVelocity = new Vector2(0, 0);
        birdBounds = new Rectangle(birdPosition.x, birdPosition.y, birdTexture.getWidth(), birdTexture.getHeight());

        groundBounds = new Rectangle(0, 0, Gdx.graphics.getWidth(), groundTexture.getHeight());
        pigBounds = new Rectangle(600, 200, pigTexture.getWidth(), pigTexture.getHeight());

        pigAlive = true;
        birdAlive = true;
        birdLaunched = false;
        isDragging = false;

        birdsLeft = 3; // Number of birds available
        levelFinished = false;
    }

    private void loadLevelAssets(int level) {
        // Load level-specific assets
        switch (level) {
            case 1:
                background = new Texture(Gdx.files.internal("level_1.png"));
                pigTexture = new Texture(Gdx.files.internal("pig.png"));
                break;
            case 2:
                background = new Texture(Gdx.files.internal("level_2.png"));
                pigTexture = new Texture(Gdx.files.internal("pig.png"));
                break;
            case 3:
                background = new Texture(Gdx.files.internal("level_3.png"));
                pigTexture = new Texture(Gdx.files.internal("pig.png"));
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }

        groundTexture = new Texture(Gdx.files.internal("ground.png"));
        birdTexture = new Texture(Gdx.files.internal("redbird.png"));
    }

    @Override
    public void render(float delta) {
        if (levelFinished) return;

        handleInput();

        // Apply gravity and update bird's position if launched
        if (birdLaunched && birdAlive) {
            birdVelocity.add(gravity.cpy().scl(delta)); // Apply gravity to velocity
            birdPosition.add(birdVelocity.cpy().scl(delta)); // Update position
            birdBounds.setPosition(birdPosition);

            // Check if the bird hits the ground
            if (birdBounds.overlaps(groundBounds)) {
                birdAlive = false; // Bird "dies" on the ground
                birdsLeft--; // Reduce available birds
                resetBirdIfNeeded();
            }
        }

        // Check for collision between the bird and the pig
        if (pigAlive && birdAlive && birdBounds.overlaps(pigBounds)) {
            pigAlive = false; // Pig "dies"
            checkLevelCompletion();
        }

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Render the ground
        game.batch.draw(groundTexture, 0, 0, Gdx.graphics.getWidth(), groundTexture.getHeight());

        // Render the pig only if it's alive
        if (pigAlive) {
            game.batch.draw(pigTexture, pigBounds.x, pigBounds.y);
        }

        // Render the bird only if it's alive
        if (birdAlive) {
            game.batch.draw(birdTexture, birdPosition.x, birdPosition.y);
        }

        game.batch.end();
    }

    private void handleInput() {
        if (!birdLaunched && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Start dragging if bird is not launched
            if (!birdLaunched && birdBounds.contains(mouseX, mouseY)) {
                isDragging = true;
            }

            // Drag the bird
            if (isDragging) {
                birdPosition.set(mouseX, mouseY);
            }
        } else if (isDragging) {
            // Release the bird
            isDragging = false;
            birdLaunched = true;

            // Calculate launch velocity based on stretch
            float launchX = initialPosition.x - birdPosition.x;
            float launchY = initialPosition.y - birdPosition.y;
            birdVelocity.set(launchX * 2, launchY * 2); // Adjust multiplier for desired speed
        }
    }

    private void resetBirdIfNeeded() {
        if (birdsLeft > 0) {
            birdAlive = true;
            birdLaunched = false;
            birdPosition.set(initialPosition);
            birdVelocity.set(0, 0);
        } else {
            checkLevelCompletion();
        }
    }

    private void checkLevelCompletion() {
        if (!pigAlive) {
            levelFinished = true;
            if (level < 3) {
                game.setScreen(new GameScreen(game, level + 1)); // Load next level
            } else {
                game.setScreen(new WinScreen(game)); // Transition to "You Won" screen
            }
        } else if (birdsLeft == 0) {
            levelFinished = true;
            game.setScreen(new LoseScreen(game)); // Transition to "You Lost" screen
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        groundTexture.dispose();
        birdTexture.dispose();
        pigTexture.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
