package game.objects;

import debugger.collisions.AABShape;
import engine.components.CollisionComponent;
import engine.components.DrawComponent;
import engine.components.TransformComponent;
import game.components.WizCollision;
import template.Vec2d;

public class WallObject extends WizObject{
  
  public WallObject(AABShape wall) {
    CollisionComponent collision = new 
        WizCollision(null, wall);
    addComponent(collision);
  }

  @Override
  public CollisionComponent getCollisionComponent() {
    return (WizCollision) getComponent("collision");
  }

  @Override
  public DrawComponent getDrawComponent() {
    return null;
  }

  @Override
  public TransformComponent getTransformComponent() {
    return getCollisionComponent();
  }

  @Override
  public Vec2d getPosition() {
    WizCollision c = (WizCollision) getComponent("collision");
    return c.getPosition();
  }

}
