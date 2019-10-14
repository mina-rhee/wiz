package game;

import engine.ui.Viewport;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import template.Vec2d;

public class WizViewport extends Viewport<WizWorld> {

  public WizViewport(Vec2d position, Vec2d top, Vec2d size, double s) {
    super(position, top, size, s);
    setColor(Color.DEEPPINK);
  }
  
  @Override
  public void onDraw(GraphicsContext g) {
    g.setFill(getColor());
    g.fillRect(absPos.x, absPos.y, size.x, size.y);
    getGameworld().drawInBounds(g, topLeft, topLeft.plus(size.pmult(new Vec2d(scale))));
  }

  @Override
  public void onTick(long nanos) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onResize(Vec2d newSize) {
    setSize(newSize);
  }

}
