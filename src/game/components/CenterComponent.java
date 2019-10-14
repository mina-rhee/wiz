package game.components;

import engine.components.AABCollision;
import engine.components.Component;
import game.WizViewport;
import game.WizWorld;
import game.objects.WizObject;
import javafx.scene.canvas.GraphicsContext;
import template.Vec2d;

public class CenterComponent implements Component {
  
  public static final String TAG = "center";
  
  private WizViewport view;
  private WizObject o;
  
  public CenterComponent(WizViewport v, WizObject player) {
    view = v;
    o = player;
  }
  
  @Override
  public String getTag() {
    return TAG;
  }

  @Override
  public void tick(long nanosSinceLastTick) {
    resetCenter();
  }
  
  public void resetCenter() {
    AABCollision c = (AABCollision) o.getCollisionComponent();
    Vec2d oMiddle = c.getBound().getTopLeft().plus(
        c.getBound().getSize().smult((float) .5)).toVec2d();
    
    Vec2d vTopLeft = oMiddle.minus(view.getSize().smult(.5));
    Vec2d vBotRight = vTopLeft.plus(view.getSize());
    
    double xPos = (WizWorld.topLeft.x > vTopLeft.x? WizWorld.topLeft.x :
      vBotRight.x > WizWorld.botRight.x? 
          WizWorld.botRight.x - view.getSize().x : vTopLeft.x);
    
    double yPos = (WizWorld.topLeft.y > vTopLeft.y? WizWorld.topLeft.y :
      vBotRight.y > WizWorld.botRight.y? 
          WizWorld.botRight.y - view.getSize().y : vTopLeft.y);
    
    view.setTopLeft(new Vec2d(xPos, yPos));
       
  }

  @Override
  public void draw(GraphicsContext g) {
    // TODO Auto-generated method stub
    
  }

}
