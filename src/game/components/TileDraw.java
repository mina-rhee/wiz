package game.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import template.Vec2d;

public class TileDraw {
  
  public static void DrawTile(GraphicsContext g, int type, Vec2d vec2d, 
      double len) {
    if(type == 0) {
      g.setFill(Color.FORESTGREEN);
      g.fillRect(vec2d.x, vec2d.y, len, len);
      //g.drawImage(WizWorld.grass, 0, 0, WizWorld.grassSize.x, 
       //   WizWorld.grassSize.y, vec2d.x, vec2d.y, len, len);
    } else if(type == 1) {
     //g.drawImage(WizWorld.moss, 0, 0, WizWorld.mossSize.x, 
      //    WizWorld.mossSize.y, vec2d.x, vec2d.y, len, len);
      g.setFill(Color.PALEGREEN);
      g.fillRect(vec2d.x, vec2d.y, len, len);
    }
  }
  
}
