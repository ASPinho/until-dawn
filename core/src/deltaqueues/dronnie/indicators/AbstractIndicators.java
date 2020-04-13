package deltaqueues.dronnie.indicators;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import deltaqueues.dronnie.elements.Dronnie;

public abstract class AbstractIndicators {

    protected Dronnie dronnie;
    protected String quality;
    protected int quantity;
    protected BitmapFont writeIndicator;

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BitmapFont getWriteIndicator() {
        return writeIndicator;
    }

    public void setWriteIndicator(BitmapFont writeIndicator) {
        this.writeIndicator = writeIndicator;
    }

}
