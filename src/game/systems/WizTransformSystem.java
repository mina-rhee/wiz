package game.systems;

import engine.GameWorld;
import engine.systems.TransformSystem;
import game.WizWorld;
import game.objects.Player;
import game.objects.WizObject;
import javafx.scene.canvas.GraphicsContext;
import template.Vec2d;

public class WizTransformSystem extends TransformSystem<WizObject>{
  
  private WizWorld w;
  private Player p;
  public final double INC = 8;

  public WizTransformSystem(GameWorld<?> world, Player player) {
    super(world);
    w = (WizWorld) world;
    p = player;
  }

  @Override
  public void onTick(long nanos) {
    Vec2d inc = new Vec2d(0);
    if(w.downKeyDown){
      inc = inc.plus(new Vec2d(0, INC));
    }
    if(w.upKeyDown){
      inc = inc.plus(new Vec2d(0, -INC));
    }
    if(w.leftKeyDown){
      inc = inc.plus(new Vec2d(-INC, 0));
    }
    if(w.rightKeyDown){
      inc = inc.plus(new Vec2d(INC, 0));
    }
    /*
    if(inc.x != 0 || inc.y != 0) {
      p.getCenterComponent().resetCenter();
    }
    */
    p.getCollisionComponent().incrementCoord(inc);
  }
  
  @Override
  public void onDraw(GraphicsContext g) {}

}
