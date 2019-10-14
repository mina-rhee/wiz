package game.components;

import debugger.collisions.AABShape;
import engine.components.AABCollision;
import engine.components.CollisionComponent;
import template.Vec2d;

public class WizCollision extends AABCollision{

  public WizCollision(Vec2d coordinate, AABShape bound) {
    super(coordinate);
    setBound(bound);
  }

  @Override
  public void onCollision(CollisionComponent o) {
  }
  
  public Vec2d getPosition() {
    return getBound().getTopLeft().toVec2d();
  }

}
