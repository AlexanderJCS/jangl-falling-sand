import jangl.JANGL;
import jangl.coords.PixelCoords;
import jangl.io.Window;
import jangl.io.keyboard.Keyboard;
import jangl.io.mouse.Mouse;
import jangl.time.Clock;
import org.lwjgl.glfw.GLFW;
import particles.ParticleSelector;
import particles.Particles;
import particles.Sand;
import particles.Water;

public class FallingSand {
    private static final double updateTime = 0.04;
    private double timeToUpdate;
    private final Particles particles;
    private final ParticleSelector particleSelector;

    public FallingSand() {
        this.particles = new Particles();
        this.particleSelector = new ParticleSelector(
                PixelCoords.distYtoNDC(Window.getScreenHeight() - Window.getScreenWidth())
        );

        this.particles.addParticle(new Sand(20, 20));

        this.timeToUpdate = 0;
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

        if (this.particleSelector.getSelected().equals("sand")) {
            this.particles.addParticle(new Sand((int) xy.x, (int) xy.y));
        } else if (this.particleSelector.getSelected().equals("water")) {
            this.particles.addParticle(new Water((int) xy.x, (int) xy.y));
        }
    }

    public void update() {
        this.particleSelector.update(Keyboard.getEvents());

        this.timeToUpdate += Clock.getTimeDelta();

        while (this.timeToUpdate > updateTime) {
            this.particles.update();
            this.timeToUpdate -= updateTime;
        }

        this.summonParticles();
    }

    public void run() {
        while (Window.shouldRun()) {
            Window.clear();
            this.particles.draw();
            this.particleSelector.draw();
            this.update();

            JANGL.update();
        }
    }

    public static void main(String[] args) {
        JANGL.init(1000, 1200);
        Window.setVsync(false);
        Window.setClearColor(1, 1, 1, 1);

        new FallingSand().run();

        Window.close();
    }
}
