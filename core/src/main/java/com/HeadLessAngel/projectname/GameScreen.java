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

    private Texture catapultTexture;
    private Texture background;
    private Texture groundTexture;
    private Texture birdTexture;
    private Texture pigTexture;

    private Rectangle groundBounds;
    private Rectangle birdBounds;
    private Rectangle pigBounds;
    private Rectangle pigBounds2;

    private Vector2 birdPosition;
    private Vector2 birdVelocity;
    private Vector2 initialPosition;

    private final Vector2 gravity = new Vector2(0, -400); // Gravity force

    private boolean birdLaunched;
    private boolean pigAlive;
    private boolean pigAlive2;
    private boolean birdAlive;
    private boolean isDragging;
    private boolean levelFinished;

    private int birdsLeft;

    // New variables for catapult positioning
    private int catapultX;
    private int catapultY;
    private int catapultWidth;
    private int catapultHeight;

    public GameScreen(AngryBird game, int level) {
        this.game = game;
        this.level = level;

        // Initialize textures and positions based on the level
        loadLevelAssets(level);

        birdPosition = new Vector2(initialPosition);
        birdVelocity = new Vector2(0, 0);
        birdBounds = new Rectangle(birdPosition.x, birdPosition.y, birdTexture.getWidth(), birdTexture.getHeight());

        groundBounds = new Rectangle(0, 0, Gdx.graphics.getWidth(), groundTexture.getHeight());

        pigAlive2 = level == 2 || level == 3; // Pigs in level 2 and 3 have two pigs
        pigAlive = true;
        birdAlive = true;
        birdLaunched = false;
        isDragging = false;

        birdsLeft = 1; // Number of birds available
        levelFinished = false;
    }

    private void loadLevelAssets(int level) {
        // Load level-specific assets
        switch (level) {
            case 1:
                background = new Texture(Gdx.files.internal("level_1(1).png"));
                pigTexture = new Texture(Gdx.files.internal("pig.png"));
                catapultTexture = new Texture(Gdx.files.internal("catapult.png"));
                initialPosition = new Vector2(150, 300);

                // Catapult position for level 1
                catapultX = 100;  // X position
                catapultY = 200;  // Y position
                catapultWidth = 100;
                catapultHeight = 200;

                pigBounds = new Rectangle(600, 200, pigTexture.getWidth(), pigTexture.getHeight());
                pigAlive2 = false;
                break;

            case 2:
                background = new Texture(Gdx.files.internal("level_2(1).png"));
                pigTexture = new Texture(Gdx.files.internal("pig.png"));
                catapultTexture = new Texture(Gdx.files.internal("catapult.png"));
                initialPosition = new Vector2(200, 350);

                // Catapult position for level 2
                catapultX = 250;  // Different X position
                catapultY = 550;  // Different Y position
                catapultWidth = 120;
                catapultHeight = 180;

                pigBounds = new Rectangle(625, 375, pigTexture.getWidth(), pigTexture.getHeight());
                pigBounds2 = new Rectangle(950, 500, pigTexture.getWidth(), pigTexture.getHeight());
                pigAlive2 = true;
                break;

            case 3:
                background = new Texture(Gdx.files.internal("level_3(1).png"));
                pigTexture = new Texture(Gdx.files.internal("pig.png"));
                catapultTexture = new Texture(Gdx.files.internal("catapult.png"));
                initialPosition = new Vector2(100, 400);

                // Catapult position for level 3
                catapultX = 50;   // Different X position
                catapultY = 300;  // Different Y position
                catapultWidth = 90;
                catapultHeight = 220;

                pigBounds = new Rectangle(500, 150, pigTexture.getWidth(), pigTexture.getHeight());
                pigBounds2 = new Rectangle(800, 300, pigTexture.getWidth(), pigTexture.getHeight());
                pigAlive2 = true;
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
        if (pigAlive2 && birdAlive && birdBounds.overlaps(pigBounds2)) {
            pigAlive2 = false; // Second pig "dies"
            checkLevelCompletion(); // Check level completion
        }

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw catapult for all levels
        game.batch.draw(
            catapultTexture,
            catapultX,
            catapultY,
            catapultWidth,
            catapultHeight
        );

        game.batch.draw(groundTexture, 0, 0, Gdx.graphics.getWidth(), groundTexture.getHeight());

        // Render the pig only if it's alive
        if (pigAlive) {
            game.batch.draw(pigTexture, pigBounds.x, pigBounds.y);
        }
        if (pigAlive2 && (level == 2 || level == 3)) { // Render pig2 in levels 2 and 3
            game.batch.draw(pigTexture, pigBounds2.x, pigBounds2.y);
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
        if (!pigAlive && !pigAlive2) {
            // Player wins the current level
            levelFinished = true;

            // Show "You Won" screen after every level
            game.setScreen(new WinScreen(game)); // Pass current level to WinScreen
        } else if (birdsLeft == 0) {
            // Player loses the current level
            levelFinished = true;
            game.setScreen(new LoseScreen(game)); // Transition to lose screen
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        groundTexture.dispose();
        birdTexture.dispose();
        pigTexture.dispose();
        catapultTexture.dispose(); // Don't forget to dispose of the catapult texture
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
