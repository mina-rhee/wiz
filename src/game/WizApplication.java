package game;

import engine.Application;
import template.Vec2d;
import template.Vec2i;

public class WizApplication extends Application {

  public WizApplication() {
    super("wizard");
  }

  @Override
  protected void onShutdown() {
    // TODO Auto-generated method stub
    
  }

  @Override
  protected void onStartup() {
    StartScreen start = new StartScreen(currentStageSize, this);
    addScreen(0, start);
    changeFocus(0);
  }
  
  public void startGame(long seed) {
    WizViewport v = new WizViewport(new Vec2d(0), new Vec2d(0), currentStageSize, 1);
    WizWorld world = new WizWorld(System.currentTimeMillis(), new Vec2i(50, 30), 8, 
        3, 2, v);
    v.setGameworld(world);
    PlayScreen playScreen = new PlayScreen(currentStageSize, v, world);
    world.setMini(playScreen.getMini());
    addScreen(1, playScreen);
    changeFocus(1);
  }
  
  

}
