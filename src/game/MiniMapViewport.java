package game;

import java.util.ArrayList;

import debugger.collisions.AABShape;
import engine.ui.Viewport;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import template.Vec2d;

public class MiniMapViewport extends Viewport<WizWorld> {
  
  WizWorld w;
  ArrayList<AABShape> walls = new ArrayList<>();

  public MiniMapViewport(WizWorld w, Vec2d windowSize, double scale) {
    super( null, new Vec2d(0), w.getDungeon().getSize().toVec2d().smult(scale), scale);
    this.w = w;
    setAbsPosition(pos(windowSize, scale));
    updateWalls();
  }
  
  @Override
  public void onDraw(GraphicsContext g) {
    Color tGray = Color.rgb(0, 0, 0, 0.5);
    g.setFill(tGray);
    g.fillRect(getAbsPosition().x, getAbsPosition().y, 
        getSize().x, getSize().y);
    
    for(AABShape wall : walls) {
      g.setFill(Color.WHITE);
      Vec2d l = wall.getTopLeft().toVec2d().smult(scale).plus(getAbsPosition());
      Vec2d s = wall.getSize().toVec2d().smult(scale);
      g.fillRect(l.x, l.y, s.x, s.y);
    }
    
    Vec2d playerPos = w.getPlayer().getPosition().
        smult(scale / WizWorld.SCALE).plus(getAbsPosition());
    g.setFill(Color.AQUA);
    g.fillOval(playerPos.x, playerPos.y + scale /2 , scale - 2, scale - 2);
  }
  
  public void updateWalls() {
    walls = w.getDungeon().getWalls();
  }

  @Override
  public void onTick(long nanos) {
  }

  @Override
  public void onResize(Vec2d newSize) {
    setAbsPosition(pos(newSize, scale));
  }
  
  public Vec2d pos(Vec2d size, double scale) {
    return new Vec2d(size.x - ((w.getDungeon().getSize().x + 2) * scale) ,scale * 2);
  }

}
