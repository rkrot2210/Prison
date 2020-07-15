package rk.win;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class ObjectDrop {

    Texture texture;
    Rectangle rectangle;

    public ObjectDrop(Texture texture, Rectangle rectangle) {
        this.texture = texture;
        this.rectangle = rectangle;
    }

}
