package game;

import java.util.Random;

import engine.Screen;
import engine.ui.Button;
import engine.ui.UIElement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import template.Vec2d;

public class StartScreen extends Screen {
  
  public static final Vec2d BUTTONSIZE = new Vec2d(150,50);
  
  private Button b1 = new Button(BUTTONSIZE, new Vec2d(0), 
      new Vec2d(0), null);
  private Button b2 = new Button(BUTTONSIZE, new Vec2d(0), 
      new Vec2d(0), null);
  private Button b3 = new Button(BUTTONSIZE, new Vec2d(0), 
      new Vec2d(0), null);
  private Button play = new Button(BUTTONSIZE, new Vec2d(0),
      new Vec2d(0), null);
  private Button chosen = null;
  private double SPACE = 50;
  
  WizApplication app;
  
  Font seedFont = new Font("Helvetica", 12);
  Font playFont = new Font("Helvetica", 22);

  public StartScreen(Vec2d s, WizApplication a) {
    super(s);
    app = a;
    
    Random r = new Random();
    long l1 = r.nextLong();
    long l2 = r.nextLong();
    long l3 = r.nextLong();
    b1.setText(l1 + "");
    b2.setText(l2 + "");
    b3.setText(l3 + "");
    b1.setColor(Color.PINK);
    b2.setColor(Color.PINK);
    b3.setColor(Color.PINK);
    b1.setFont(seedFont);
    b2.setFont(seedFont);
    b3.setFont(seedFont);
    play.setText("Start Game");
    play.setColor(Color.HOTPINK);
    play.setTextColor(Color.WHITE);
    play.setFont(playFont);
    calcButtonPos();
  }
  
  private void calcButtonPos() {
    double helperX = (size.x - (3 * BUTTONSIZE.x) - (2 * SPACE)) / 2.0;
    double helperY = size.y * 3.0 / 4.0;
    b1.setAbsPosition(new Vec2d(helperX, helperY));
    b2.setAbsPosition(new Vec2d(helperX + SPACE + BUTTONSIZE.x, helperY));
    b3.setAbsPosition(new Vec2d(helperX + (SPACE + BUTTONSIZE.x) * 2, helperY));
    play.setAbsPosition(new Vec2d( (size.x  - BUTTONSIZE.x) / 2.0, 
        size.y / 5.0));
  }

  @Override
  protected void onDraw(GraphicsContext g) {
    g.setFill(Color.LIGHTSEAGREEN);
    g.fillRect(0, 0, size.x, size.y);
    b1.onDraw(g);
    b2.onDraw(g);
    b3.onDraw(g);
    if(chosen != null) {
      play.onDraw(g);
      g.setStroke(Color.DEEPPINK);
      Vec2d chosentl = chosen.getAbsPosition();
      Vec2d chosens = chosen.getSize();
      g.setLineWidth(2);
      g.strokeRoundRect(chosentl.x, chosentl.y, chosens.x, chosens.y, 5, 5);
      g.setLineWidth(1);
    }
    g.setFill(Color.DARKGREEN);
    Vec2d centerText = UIElement.centerText("Select Seed:", playFont, new Vec2d(0), size);
    g.setFont(playFont);
    g.fillText("Select Seed:", centerText.x, size.y * .6);
  }

  @Override
  protected void onResize(Vec2d newSize) {
    size = newSize;
    calcButtonPos();
  }
  
  @Override 
  protected void onMouseMoved(MouseEvent e) {
    b1.onMouseMoved(e);
    b2.onMouseMoved(e);
    b3.onMouseMoved(e);
    play.onMouseMoved(e);
  }
  
  @Override
  protected void onMouseClicked(MouseEvent e) {
    Vec2d mousePos = new Vec2d(e.getSceneX(), e.getSceneY());
    if(b1.inElement(mousePos)) {
      chosen = b1;
    } else if(b2.inElement(mousePos)) {
      chosen = b2;
    } else if (b3.inElement(mousePos)) {
      chosen = b3;
    } else if (play.inElement(mousePos) && chosen != null) {
      app.startGame(Long.parseLong(chosen.getText()));
    }
  }

}
