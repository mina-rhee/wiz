package game.components;

import engine.components.AnimatedSprite;
import javafx.scene.image.Image;
import template.Vec2d;

public class WizAnimated extends AnimatedSprite {

  public WizAnimated(Image i, Vec2d iPosition, Vec2d iSize, int fNum,
      double iScale) {
    super(i, iPosition, iSize, fNum, iScale);
  }

  @Override
  public void incrementPosition() {
    // TODO Auto-generated method stub
    
  }

}
