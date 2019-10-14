package game;

import engine.Screen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import template.Vec2d;

public class PlayScreen extends Screen {
  
  private WizViewport v;
  private MiniMapViewport mini;
  private WizWorld w;

  public PlayScreen(Vec2d s, WizViewport view, WizWorld world) {
    super(s);
    v = view;
    w = world;
    mini = new MiniMapViewport(w, size, 5);
  }
  
  public MiniMapViewport getMini() {
    return mini;
  }

  @Override
  protected void onDraw(GraphicsContext g) {
    v.onDraw(g);
    mini.onDraw(g);
  }
  
  @Override
  protected void onTick(long nanos) {
    w.onTick(nanos);
  }

  @Override
  protected void onResize(Vec2d newSize) {
    v.onResize(newSize);
    mini.onResize(newSize);
  }
  
  @Override
  protected void onKeyPressed(KeyEvent e) {
    w.onKeyPressed(e);
  }
  
  @Override
  protected void onKeyReleased(KeyEvent e) {
    w.onKeyReleased(e);
  }

}
