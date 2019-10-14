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
    
    WizViewport v = new WizViewport(new Vec2d(0), new Vec2d(0), currentStageSize, 1);
    WizWorld world = new WizWorld(System.currentTimeMillis(), new Vec2i(50, 30), 12, 
        3, 2, v);
    v.setGameworld(world);
    PlayScreen playScreen = new PlayScreen(currentStageSize, v, world);
    world.setMini(playScreen.getMini());
    addScreen(0, playScreen);
    changeFocus(0);
  }

}
