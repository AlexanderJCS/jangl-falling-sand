import jangl.JANGL;
import jangl.coords.PixelCoords;
import jangl.io.Window;
import jangl.io.keyboard.Keyboard;
import jangl.io.mouse.Mouse;
import org.lwjgl.glfw.GLFW;
import game.ParticleSelector;
import game.Particles;
import particles.Barrier;
import particles.Sand;
import particles.Stone;
import particles.Water;

public class FallingSand {
    private final Particles particles;
    private final ParticleSelector particleSelector;

    public FallingSand() {
        this.particles = new Particles(0.02);
        this.particleSelector = new ParticleSelector(
                PixelCoords.distYtoNDC(Window.getScreenHeight() - Window.getScreenWidth())
        );

        this.particles.addParticle(new Sand(20, 20));
    }

    public void summonParticles() {
        if (!Mouse.mouseDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
            return;
        }

        PixelCoords xy = this.particles.getPixelCoords(Mouse.getMousePos());

        boolean outOfBounds = xy.x < 0 || xy.x >= this.particles.getWidth() ||
                xy.y < 0 || xy.y >= this.particles.getHeight();

        if (outOfBounds || this.particles.getParticleAt((int) xy.x, (int) xy.y) != null) {
            return;
        }

        String selected = this.particleSelector.getSelected();

        switch (selected) {
            case "sand"    -> this.particles.addParticle(new Sand((int) xy.x, (int) xy.y));
            case "water"   -> this.particles.addParticle(new Water((int) xy.x, (int) xy.y));
            case "stone"   -> this.particles.addParticle(new Stone((int) xy.x, (int) xy.y));
            case "barrier" -> this.particles.addParticle(new Barrier((int) xy.x, (int) xy.y));
        }
    }

    private void update() {
        this.particleSelector.update(Keyboard.getEvents());

        this.summonParticles();
        this.particles.update();
    }

    private void draw() {
        Window.clear();

        this.particles.draw();
        this.particleSelector.draw();
    }

    public void run() {
        while (Window.shouldRun()) {
            this.draw();
            this.update();

            JANGL.update();
        }
    }

    public static void main(String[] args) {
        JANGL.init(1000, 1200);
        Window.setVsync(true);
        Window.setClearColor(1, 1, 1, 1);

        new FallingSand().run();

        Window.close();
    }
}
