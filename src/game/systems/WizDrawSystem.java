package game.systems;

import java.util.Iterator;
import java.util.Set;

import engine.GameWorld;
import engine.dungeon.Dungeon;
import engine.dungeon.SpacePartition;
import engine.systems.DrawSystem;
import game.WizViewport;
import game.WizWorld;
import game.components.TileDraw;
import game.objects.WizObject;
import javafx.scene.canvas.GraphicsContext;
import template.Vec2d;

public class WizDrawSystem extends DrawSystem<WizObject> {
  
  public static final String TAG = "DRAW";
  private int[][] tiles;
  WizViewport v;

  public WizDrawSystem(GameWorld<WizObject> world, WizViewport view, Dungeon d) {
    super(world);
    v = view;
    int x = d.getSize().x;
    int y = d.getSize().y;
    tiles = new int[x][y];
    for(int a = 0; a < x; a++) {
      for(int b = 0; b < y; b++) {
        tiles[a][b] = 0;
      }
    }
    SpacePartition.fillArray(d, tiles);
    addLayer(0);
  }
  
  @Override
  public void drawInBounds(GraphicsContext g, Vec2d topLeft, Vec2d botRight) {
    double tSize = WizWorld.SCALE;
    for(int x = (int) (topLeft.x/tSize); x < (int) (botRight.x/tSize) + 2 
        && x < tiles.length; x++) {
      for(int y = (int) (topLeft.y/tSize); y < (int) (botRight.y/tSize) + 2 
          && y < tiles[0].length; y++) {
        TileDraw.DrawTile(g, tiles[x][y], v.gameToScreen(new Vec2d(x * tSize, y * tSize)), 
            tSize);
      }
    }
    
    for(Set<WizObject> layer : getLayers()) {
      Iterator<WizObject> itr = layer.iterator();
      while(itr.hasNext()) {
        WizObject w = itr.next();
        w.getDrawComponent().drawAt(g, v.gameToScreen(w.getPosition()), 1);
      }
    }
  }
  
  public void addObject(WizObject p) {
    addObject(p, 0);
  }

  @Override
  public String getTag() {
    return TAG;
  }

  @Override
  public void onDraw(GraphicsContext g) {}

  @Override
  public void onTick(long nanos) {
    for(Set<WizObject> layer : getLayers()) {
      Iterator<WizObject> itr = layer.iterator();
      while(itr.hasNext()) {
        WizObject w = itr.next();
        w.getDrawComponent().tick(nanos);
      }
    }
  }

}
