package game.objects;

import debugger.collisions.AABShape;
import debugger.support.Vec2f;
import engine.components.CollisionComponent;
import engine.components.DrawComponent;
import engine.components.SpriteComponent;
import engine.components.TransformComponent;
import game.WizViewport;
import game.WizWorld;
import game.components.CenterComponent;
import game.components.WizCollision;
import template.Vec2d;

public class Player extends WizObject {
  
  public Player(Vec2d pos, WizViewport v) {
    Vec2d s = WizWorld.avatarSize;
    WizCollision collision = new WizCollision(pos, 
        new AABShape(pos.toVec2f(), new Vec2f(s.x  * WizWorld.SCALE/ WizWorld.avatarScale, 
            s.y * WizWorld.SCALE/ WizWorld.avatarScale)));
    addComponent(collision);
    
    DrawComponent draw = new SpriteComponent(WizWorld.avatarSheet, WizWorld.avatarPos, 
        WizWorld.avatarSize, WizWorld.SCALE / WizWorld.avatarScale);
    addComponent(draw);
    
    CenterComponent center = new CenterComponent(v, this);
    addComponent(center);
  }

  @Override
  public CollisionComponent getCollisionComponent() {
     return (CollisionComponent) getComponent("collision");
  }

  @Override
  public TransformComponent getTransformComponent() {
    return getCollisionComponent();
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
  
  public CenterComponent getCenterComponent() {
    return (CenterComponent) getComponent("center");
  }

}
