package game.components;

import engine.components.AnimatedSprite;
import javafx.scene.image.Image;
import template.Vec2d;

public class DoorDraw extends AnimatedSprite {

  public DoorDraw(Image i, Vec2d iPosition, Vec2d iSize, int fNum,
      double iScale) {
    super(i, iPosition, iSize, fNum, iScale);
  }

  @Override
  public void incrementPosition() {
    setImgPosition(new Vec2d(480 * getCurFrame() + 100, 0));
  }

}
