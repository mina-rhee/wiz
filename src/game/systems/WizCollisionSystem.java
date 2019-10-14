package game.systems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import debugger.collisions.AABShape;
import debugger.support.Vec2f;
import engine.GameWorld;
import engine.systems.CollisionSystem;
import game.WizWorld;
import game.objects.Door;
import game.objects.Player;
import game.objects.WallObject;
import game.objects.WizObject;
import javafx.scene.canvas.GraphicsContext;

public class WizCollisionSystem extends CollisionSystem<WizObject>{
  
  WizWorld w;
  public final int WALLLAYER = 0;
  public final int OTHERLAYER = 1;
  public final int DOORLAYER = 2;

  public WizCollisionSystem(GameWorld<?> world) {
    super(world);
    w = (WizWorld) world;
    List<AABShape> walls = w.getDungeon().getWalls();
    Set<WizObject> wallLayer = new HashSet<>();
    float scale = (float) WizWorld.SCALE;
    for(AABShape wall : walls) {
      AABShape scaleWall = new AABShape(wall.getTopLeft().smult(scale),
          wall.getSize().smult(scale));
      wallLayer.add(new WallObject(scaleWall));
    }
    getLayers().add(WALLLAYER, wallLayer);
    addLayer(OTHERLAYER);
    addLayer(DOORLAYER);
  }
  
  public void addPlayer(Player w) {
    addObject(w, OTHERLAYER);
  }
  
  public void addDoor(Door d) {
    addObject(d, DOORLAYER);
  }

  @Override
  public void checkCollisions() {
    Set<WizObject> walls = getLayer(WALLLAYER);
    Set<WizObject> other = getLayer(OTHERLAYER);
    Set<WizObject> door = getLayer(DOORLAYER);
    for (WizObject wall : walls) {
      for (WizObject o : other) {
        Vec2f v = wall.getCollisionComponent().collides(o.getCollisionComponent());
        if(v != null) {
          o.getTransformComponent().incrementCoord(v.toVec2d());
        }
      }
    }
    
    for(WizObject o : other) {
      for(WizObject d : door) {
        Vec2f v = o.getCollisionComponent().collides(d.getCollisionComponent());
        if(v != null) {
          w.newDungeon();
        }
      }
    }
  }

  @Override
  public void onDraw(GraphicsContext g) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onTick(long nanos) {
    checkCollisions();
  }

}
