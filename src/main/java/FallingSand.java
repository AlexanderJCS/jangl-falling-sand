import jangl.JANGL;
import jangl.coords.PixelCoords;
import jangl.io.Window;
import jangl.io.mouse.Mouse;
import jangl.time.Clock;
import org.lwjgl.glfw.GLFW;
import particles.Particles;
import particles.Sand;
import particles.Water;

public class FallingSand {
    private static final double updateTime = 0.04;
    private double timeToUpdate;
    private final Particles particles;

    public FallingSand() {
        this.particles = new Particles();
        this.particles.addParticle(new Sand(20, 20));

        this.timeToUpdate = 0;
    }

    public void update() {
        this.timeToUpdate += Clock.getTimeDelta();

        while (this.timeToUpdate > updateTime) {
            this.particles.update();
            this.timeToUpdate -= updateTime;
        }

        if (Mouse.mouseDown(GLFW.GLFW_MOUSE_BUTTON_1) || Mouse.mouseDown(GLFW.GLFW_MOUSE_BUTTON_2)) {
            PixelCoords xy = this.particles.getPixelCoords(Mouse.getMousePos());

            boolean outOfBounds = xy.x < 0 || xy.x >= this.particles.getWidth() ||
                    xy.y < 0 || xy.y >= this.particles.getHeight();

            if (!outOfBounds && this.particles.getParticleAt((int) xy.x, (int) xy.y) == null) {
                if (Mouse.mouseDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
                    this.particles.addParticle(new Sand((int) xy.x, (int) xy.y));
                } else {
                    this.particles.addParticle(new Water((int) xy.x, (int) xy.y));
                }
            }
        }
    }

    public void run() {
        while (Window.shouldRun()) {
            this.particles.draw();
            this.update();

            JANGL.update();
        }
    }

    public static void main(String[] args) {
        JANGL.init(1200, 1200);
        Window.setVsync(false);

        new FallingSand().run();

        Window.close();
    }
}
