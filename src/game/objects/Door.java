package game.objects;

import debugger.collisions.AABShape;
import debugger.support.Vec2f;
import engine.components.CollisionComponent;
import engine.components.DrawComponent;
import engine.components.TransformComponent;
import game.WizWorld;
import game.components.DoorDraw;
import game.components.WizCollision;
import template.Vec2d;

public class Door extends WizObject {
  
  public Door(Vec2d pos) {
    Vec2d s = WizWorld.doorSize;
    WizCollision collision = new WizCollision(pos, 
        new AABShape(pos.plus(s.smult(.25)).toVec2f(), 
            new Vec2f(s.x  * WizWorld.SCALE * .5 / WizWorld.doorScale, 
            s.y * WizWorld.SCALE * .5/ WizWorld.doorScale)));
    addComponent(collision);
    
    
    DrawComponent draw = new DoorDraw(WizWorld.doorSheet, WizWorld.doorPos,
        WizWorld.doorSize, WizWorld.doorFNum, WizWorld.SCALE / WizWorld.doorScale);
    addComponent(draw);
    
  }

  @Override
  public TransformComponent getTransformComponent() {
    return getCollisionComponent();
  }

  @Override
  public CollisionComponent getCollisionComponent() {
    return (CollisionComponent) getComponent("collision");
  }

  @Override
  public DrawComponent getDrawComponent() {
    return (DrawComponent) getComponent("draw");
  }

  @Override
  public Vec2d getPosition() {
    WizCollision c = (WizCollision) getComponent("collision");
    return c.getPosition();
  }

}
