package game;

import java.util.Random;

import engine.Screen;
import engine.ui.Button;
import javafx.scene.canvas.GraphicsContext;
import template.Vec2d;

public class StartScreen extends Screen {
  
  public static final Vec2d BUTTONSIZE = new Vec2d(100,50);
  
  private Button b1 = new Button(BUTTONSIZE, new Vec2d(0), 
      new Vec2d(0), null);
  private Button b2 = new Button(BUTTONSIZE, new Vec2d(0), 
      new Vec2d(0), null);
  private Button b3 = new Button(BUTTONSIZE, new Vec2d(0), 
      new Vec2d(0), null);
  private Button play = new Button(BUTTONSIZE.smult(2), new Vec2d(0),
      new Vec2d(0), null);
  private Button chosen = null;

  public StartScreen(Vec2d s) {
    super(s);
    
    Random r = new Random();
    long l1 = r.nextLong();
    long l2 = r.nextLong();
    long l3 = r.nextLong();
    b1.setText(l1 + "");
    b2.setText(l2 + "");
    b3.setText(l3 + "");
    play.setText("Start Game");
    calcButtonPos();
  }
  
  private void calcButtonPos() {
    double helperX = (size.x - 5 * BUTTONSIZE.x) / 2.0;
    double helperY = size.y * 2.0 / 3.0;
    b1.setAbsPosition(new Vec2d(helperX, helperY));
    b2.setAbsPosition(new Vec2d(helperX + BUTTONSIZE.x * 2, helperY));
    b3.setAbsPosition(new Vec2d(helperX + BUTTONSIZE.x * 4, helperY));
    play.setAbsPosition(new Vec2d( (size.x / 2.0) - BUTTONSIZE.x, 
        BUTTONSIZE.y / 3.0));
  }
  
  private void buttonPressed(Button b) {
    if(b == b1) {
      chosen = b1;
    } else if (b == b2) {
      chosen = b2;
    } else if (b == b3) {
      chosen = b2;
    }
  }

  @Override
  protected void onDraw(GraphicsContext g) {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void onResize(Vec2d newSize) {
    size = newSize;
  }

}
