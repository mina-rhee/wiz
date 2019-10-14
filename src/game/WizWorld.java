package game;

import java.util.Random;

import engine.GameWorld;
import engine.dungeon.Dungeon;
import engine.dungeon.SpacePartition;
import game.objects.Door;
import game.objects.Player;
import game.objects.WizObject;
import game.systems.WizCollisionSystem;
import game.systems.WizDrawSystem;
import game.systems.WizTransformSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import template.Vec2d;
import template.Vec2i;

public class WizWorld extends GameWorld<WizObject> {
  
  private Dungeon d;
  private WizViewport v;
  private MiniMapViewport mini;
  public static final double SCALE = 50;
  
  //sprite stuff
  public static final Image avator = new Image("spritesheets/avatars.png");
  
  public static final Image grass = new Image("spritesheets/grass_tile.png");
  public static final Vec2i grassSize = new Vec2i(64);
  
  public static final Image moss = new Image("spritesheets/moss.png");
  public static final Vec2i mossSize = new Vec2i(100);
  
  public static final Image avatarSheet = new Image("spritesheets/avatars.png");
  public static final Vec2d avatarPos = new Vec2d(145, 93);
  public static final Vec2d avatarSize = new Vec2d(60, 100);
  public static final double avatarScale = 60;
  
  public static final Image doorSheet = new Image("spritesheets/door.png");
  public static final Vec2d doorPos = new Vec2d(100,0);
  public static final Vec2d doorSize = new Vec2d(280,480);
  public static final double doorScale = 200;
  public static final int doorFNum = 26;
  
  public boolean upKeyDown = false;
  public boolean downKeyDown = false;
  public boolean leftKeyDown = false;
  public boolean rightKeyDown = false;
  
  public static Vec2d topLeft = new Vec2d(0);
  public static Vec2d botRight;
  
  private Player p;
  private Door door;
  
  private Vec2i dungeonSize;
  private int minDepth;
  private int hallWidth;
  private int overlap;
  private long seed;
  
  public WizWorld(long seed, Vec2i dungeonSize, int minDepth, int hallWidth, 
      int overlap, WizViewport view) {
    
    this.dungeonSize = dungeonSize;
    this.minDepth = minDepth;
    this.hallWidth = hallWidth;
    this.overlap = overlap;
    this.seed = seed;
    
    v = view;
    
    newDungeon();
  }
  
  public void setMini(MiniMapViewport m) {
    mini = m;
  }
  
  public void newDungeon() {
    d = SpacePartition.createRandomDungeon(dungeonSize, minDepth, overlap, hallWidth, seed);
    
    botRight = new Vec2d(dungeonSize.x * SCALE, dungeonSize.y * SCALE);
    
    Vec2d spawnPos = SpacePartition.getSpawnPos(d).smult(SCALE);
    p = new Player(spawnPos, v);
    
    Vec2d doorPos = SpacePartition.getDoorPos(d.getRight()).smult(SCALE);
    door = new Door(doorPos);
    
    WizDrawSystem drawSystem = new WizDrawSystem(this, v, d);
    drawSystem.addObject(p);
    drawSystem.addObject(door);
    addSystem(drawSystem);
    
    WizCollisionSystem collision = new WizCollisionSystem(this);
    collision.addPlayer(p);
    collision.addDoor(door);
    addSystem(collision);
    
    WizTransformSystem transform = new WizTransformSystem(this, p);
    addSystem(transform);
    
    seed = new Random(seed).nextLong();
    
    p.getCenterComponent().resetCenter();
    
    if(mini != null) {
      mini.updateWalls();
    }
  }

  @Override
  public void onTick(long nanos) {
    getSystem("TRANSFORM").onTick(nanos);
    getSystem("COLLISION").onTick(nanos);
    getSystem("DRAW").onTick(nanos);
    p.getCenterComponent().tick(nanos);
  }

  @Override
  public void drawInBounds(GraphicsContext g, Vec2d topLeft2, Vec2d botRight) {
    WizDrawSystem draw = (WizDrawSystem) getSystem("DRAW");
    draw.drawInBounds(g, topLeft2, botRight);
  }
  
  public Dungeon getDungeon() {
    return d;
  }
  
  protected void onKeyPressed(KeyEvent e) {
    KeyCode k = e.getCode();
    if(k == KeyCode.UP) {
      upKeyDown = true;
    }
    if(k == KeyCode.DOWN) {
      downKeyDown = true;
    }
    if(k == KeyCode.LEFT) {
      leftKeyDown = true;
    }
    if(k == KeyCode.RIGHT) {
      rightKeyDown = true;
    }
  }

  protected void onKeyReleased(KeyEvent e) {
    KeyCode k = e.getCode();
    if(k == KeyCode.UP) {
      upKeyDown = false;
    }
    if(k == KeyCode.DOWN) {
      downKeyDown = false;
    }
    if(k == KeyCode.LEFT) {
      leftKeyDown = false;
    }
    if(k == KeyCode.RIGHT) {
      rightKeyDown = false;
    }
  }
  
  public Player getPlayer() {
    return p;
  }
  
  public Door getDoor() {
    return door;
  }

}
