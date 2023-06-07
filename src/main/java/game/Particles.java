package game;

import jangl.coords.NDCoords;
import jangl.coords.PixelCoords;
import jangl.graphics.MutableTexture;
import jangl.graphics.Texture;
import jangl.io.Window;
import jangl.shapes.Rect;
import jangl.time.Clock;
import particles.Air;
import particles.Particle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Particles {
    private static final float[] BACKGROUND_COLOR = new float[]{0.5f, 0.8f, 0.8f, 1};
    private final MutableTexture mutableTexture;
    private final Rect rect;
    private final Particle[][] particles;
    private final double updateTime;
    private double timeToUpdate;

    public Particles(double updateTime) {
        this.mutableTexture = new MutableTexture("src/main/resources/black.png");
        this.mutableTexture.fillImage(BACKGROUND_COLOR[0], BACKGROUND_COLOR[1], BACKGROUND_COLOR[2], 1);

        this.particles = new Particle[this.getHeight()][this.getWidth()];
        this.rect = new Rect(
                new PixelCoords(
                        0, Window.getScreenWidth()
                ).toScreenCoords(),
                2, PixelCoords.distYtoNDC(Window.getScreenWidth())
        );

        this.updateTime = updateTime;
        this.timeToUpdate = 0;
    }

    public void addParticle(Particle particle) {
        if (particle.getClass() != Air.class) {
            this.particles[particle.getY()][particle.getX()] = particle;
            this.mutableTexture.setPixelAt(particle.getX(), particle.getY(), particle.getRGBA());

        } else {
            // Since air is represented as null
            this.particles[particle.getY()][particle.getX()] = null;
            this.mutableTexture.setPixelAt(particle.getX(), particle.getY(), BACKGROUND_COLOR);
        }

    }

    public PixelCoords getPixelCoords(NDCoords ndCoords) {
        PixelCoords offset = new PixelCoords(
                0, Window.getScreenHeight() - new NDCoords(0, this.rect.getCenter().y + this.rect.getHeight() / 2).toPixelCoords().y
        );

        PixelCoords pixelCoords = ndCoords.toPixelCoords();

        float gridWidth = NDCoords.distXtoPixelCoords(this.rect.getWidth());
        float gridHeight = NDCoords.distYtoPixelCoords(this.rect.getHeight());

        float xPixels = pixelCoords.x + offset.x;
        float yPixels = (Window.getScreenHeight() - pixelCoords.y) - offset.y;

        float xGrid = xPixels * this.getHeight() / gridWidth;
        float yGrid = yPixels * this.getWidth() / gridHeight;

        return new PixelCoords(xGrid, yGrid);
    }

    public Particle getParticleAt(int x, int y) {
        return this.particles[y][x];
    }

    private void updateParticles() {
        // Make the column order random for every row to prevent biases in the direction of iteration
        // with certain particles such as water. Before this was added, water would be biased towards the left.
        List<Integer> columnOrder = new ArrayList<>(this.particles[0].length);

        for (int i = 0; i < this.particles[0].length; i++) {
            columnOrder.add(i);
        }

        for (int r = this.particles.length - 1; r >= 0; r--) {
            Collections.shuffle(columnOrder);

            for (int c : columnOrder) {
                Particle particle = this.particles[r][c];

                if (particle == null) {
                    continue;
                }

                particle.update(this);

                if (particle.getX() != c || particle.getY() != r) {
                    this.particles[particle.getY()][particle.getX()] = particle;
                    this.particles[r][c] = null;
                    this.mutableTexture.setPixelAt(c, r, BACKGROUND_COLOR);
                    this.mutableTexture.setPixelAt(particle.getX(), particle.getY(), particle.getRGBA());
                }
            }
        }
    }

    public void update() {
        this.timeToUpdate += Clock.getTimeDelta();

        while (this.timeToUpdate > updateTime) {
            this.updateParticles();
            this.timeToUpdate -= updateTime;
        }
    }

    public int getWidth() {
        return this.mutableTexture.width;
    }

    public int getHeight() {
        return this.mutableTexture.height;
    }

    public void draw() {
        this.mutableTexture.bind();
        this.rect.draw();
        Texture.unbind();
    }
}
