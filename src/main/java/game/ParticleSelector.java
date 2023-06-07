package game;

import jangl.coords.NDCoords;
import jangl.graphics.font.Text;
import jangl.graphics.font.parser.Font;
import jangl.io.keyboard.KeyEvent;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class ParticleSelector {
    private final Font font;
    private final Text infoText;
    private final Text selectedText;
    private String selected;

    public ParticleSelector(float height) {
        this.font = new Font("src/main/resources/arial/arial.fnt", "src/main/resources/arial/arial.png");

        this.selected = "sand";

        this.infoText = new Text(new NDCoords(-0.95f, -height / 10 + 1), this.font, height / 5f,
                "Press S for sand, W for water, z for stone,\nB for barrier, A for air. Click to spawn particles");

        this.selectedText = new Text(new NDCoords(-0.95f, -height / 1.5f + 1), this.font, height / 5f,
                "Selected: " + this.selected);
    }

    public void draw() {
        this.infoText.draw();
        this.selectedText.draw();
    }

    public void update(List<KeyEvent> keyEvents) {
        for (KeyEvent keyEvent : keyEvents) {
            if (keyEvent.action != GLFW.GLFW_PRESS) {
                continue;
            }

            char key = Character.toLowerCase(keyEvent.key);
            boolean newOptionSelected = true;

            switch (key) {
                case 's' -> this.selected = "sand";
                case 'w' -> this.selected = "water";
                case 'z' -> this.selected = "stone";
                case 'b' -> this.selected = "barrier";
                case 'a' -> this.selected = "air";
                default -> newOptionSelected = false;
            }

            if (newOptionSelected) {
                this.selectedText.setText("Selected: " + this.selected);
            }
        }
    }

    public String getSelected() {
        return this.selected;
    }
}
