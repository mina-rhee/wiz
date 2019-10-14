package engine.dungeon;

import java.util.ArrayList;
import java.util.Random;

import debugger.collisions.AABShape;
import debugger.support.Vec2f;
import template.Vec2d;
import template.Vec2i;

public class SpacePartition {
  
  private static Random r; 
 
  //given, overlap < minDepth/2, hallWidth < overlap
  
  public static Dungeon createRandomDungeon(Vec2i size, int minDepth, int overlap,
      int hallWidth, long seed) {
    assert(overlap < (minDepth + 1)/2);
    assert(hallWidth < overlap);
    r = new Random(seed);
    Dungeon d = new Dungeon(new Vec2i(0,0), size, false);
    partition(d, minDepth, 0);
    roomFill(d, overlap);
    connect(d, hallWidth, minDepth);
    //ArrayList<Room> rooms = getRooms(d);
    return d;
  }

  private static void partition(Dungeon d, int minDepth, int dDepth) {
    if(dDepth > 2 && r.nextDouble() > .7) {
      return;
    } else {
      Vec2i size = d.getSize();
      Vec2i tl = d.getTopLeft();
      boolean dir = d.getSplit();
      if(dir) {
        if(size.y < minDepth * 2) {
          return;
        } else {
          int split = (int) (r.nextDouble() * (size.y - minDepth * 2) + minDepth);
          d.setLeft(new Dungeon(tl, new Vec2i(size.x, split), d, !dir));
          d.setRight(new Dungeon(new Vec2i(tl.x, tl.y + split), 
              new Vec2i(size.x, size.y - split), d, !dir));
          partition(d.getLeft(), minDepth, dDepth + 1);
          partition(d.getRight(), minDepth, dDepth + 1);
        }
      } else {
        if(size.x < minDepth * 2) {
          return;
        } else {
          int split = (int) (r.nextDouble() * (size.x - minDepth * 2) + minDepth);
          d.setLeft(new Dungeon(tl, new Vec2i(split, size.y), d, !dir));
          d.setRight(new Dungeon(new Vec2i(tl.x + split, tl.y), 
              new Vec2i(size.x - split, size.y), d, !dir));
          partition(d.getLeft(), minDepth, dDepth + 1);
          partition(d.getRight(), minDepth, dDepth + 1);
        }
      }
    }
  }
  
  private static void roomFill(Dungeon d, int overlap) {
    if(d.getLeft() == null) {
      Vec2i s = d.getSize();
      Vec2i tl = d.getTopLeft();
   
      //choose side length and location of the room
      //x 
      int xMin = (s.x + 1)/2 + overlap;
      int xSide = (int) (r.nextDouble() * (s.x - 2 - xMin) + xMin);
      int tlx = (int) ((r.nextDouble() * (s.x - 2 - xSide)) + tl.x + 1);
      
      //y
      int yMin = (s.y + 1)/2 + overlap;
      int ySide = (int) (r.nextDouble() * (s.y - 2 - yMin) + yMin);
      int tly = (int) ((r.nextDouble() * (s.y - 2 - ySide)) + tl.y + 1);
      
      d.setSpace(new Room(new Vec2f(tlx, tly), 
          new Vec2f(xSide, ySide)));
    } else {
      roomFill(d.getLeft(), overlap);
      roomFill(d.getRight(), overlap);
    }
  }
  
  private static void connect(Dungeon d, int width, int minDepth) {
    if(d.getLeft() != null) {
      ArrayList<Dungeon> lDungeons = getRoomDungeons(d.getLeft());
      ArrayList<Dungeon> rDungeons = getRoomDungeons(d.getRight());
      
      if(d.getSplit()) {
        
        //filter out rooms to only those on the edge of the split
        int splitLine = d.getRight().getTopLeft().y;
        lDungeons.removeIf(r -> r.getTopLeft().y + r.getSize().y != splitLine);
        rDungeons.removeIf(r -> r.getTopLeft().y != splitLine);
        
        ArrayList<Room> lRooms = new ArrayList<>();
        ArrayList<Room> rRooms = new ArrayList<>();
        for(Dungeon dun : lDungeons) {
          lRooms.add((Room) dun.getSpace());
        }
        for(Dungeon dun : rDungeons) {
          rRooms.add((Room) dun.getSpace());
        }
        
        Hallway h = null;
        //start searching for a place to put a hallway,
        //starting from the middle
        Vec2i bound = new Vec2i(d.getTopLeft().x, d.getSize().x + d.getTopLeft().x);
    
        searchloop:
        for(int a = 0; a < bound.y - width - bound.x; a++) {
          int i = a + (bound.y + bound.x)/2;
          if(i >= bound.y - width) {
            i = i - bound.y + width + bound.x;
          }
          for(Room lRoom : lRooms) {
            int ltl = (int) lRoom.getShape().getTopLeft().x;
            if(ltl <= i && ltl + lRoom.getShape().getSize().x >= i + width) {
              for(Room rRoom : rRooms) {
                int rtl = (int) rRoom.getShape().getTopLeft().x;
                if(rtl <= i && rtl + rRoom.getShape().getSize().x >= i + width) {
                  Vec2i tl = new Vec2i(i, (int) (lRoom.getShape().getTopLeft().y + 
                      lRoom.getShape().getSize().y));
                  Vec2i size = new Vec2i(width, 
                      (int) rRoom.getShape().getTopLeft().y - tl.y);
                  h = new Hallway(tl, size, lRoom, rRoom, false);
                  lRoom.addHallway(h);
                  rRoom.addHallway(h);
                  break searchloop;
                }
              }
            }
          }
        }
        assert(h != null);
        if(h == null) {
          System.out.println("bad");
        }
        d.setSpace(h);
      } else {
      //filter out rooms to only those on the edge of the split
        int splitLine = d.getRight().getTopLeft().x;
        
        lDungeons.removeIf(r -> r.getTopLeft().x + r.getSize().x != splitLine);
        rDungeons.removeIf(r -> r.getTopLeft().x != splitLine);
        
        ArrayList<Room> lRooms = new ArrayList<>();
        ArrayList<Room> rRooms = new ArrayList<>();
        for(Dungeon dun : lDungeons) {
          lRooms.add((Room) dun.getSpace());
        }
        for(Dungeon dun : rDungeons) {
          rRooms.add((Room) dun.getSpace());
        }
        
        Hallway h = null;
        //start searching for a place to put a hallway,
        //starting from the middle
        Vec2i bound = new Vec2i(d.getTopLeft().y, d.getSize().y + d.getTopLeft().y);
        
        searchloop:
          for(int a = 0; a < bound.y - width - bound.x; a++) {
          int i = a + (bound.y + bound.x)/2;
          if(i >= bound.y - width) {
            i = i - bound.y + width + bound.x;
          }
          for(Room lRoom : lRooms) {
            int ltl = (int) lRoom.getShape().getTopLeft().y;
            if(ltl <= i && ltl + lRoom.getShape().getSize().y >= i + width) {
              for(Room rRoom : rRooms) {
                int rtl = (int) rRoom.getShape().getTopLeft().y;
                if(rtl <= i && rtl + rRoom.getShape().getSize().y >= i + width) {
                  Vec2i tl = new Vec2i((int) (lRoom.getShape().getTopLeft().x + 
                      lRoom.getShape().getSize().x), i);
                  Vec2i size = new Vec2i((int) rRoom.getShape().getTopLeft().x 
                      - tl.x, width);
                  h = new Hallway(tl, size, lRoom, rRoom, true);
                  lRoom.addHallway(h);
                  rRoom.addHallway(h);
                  break searchloop;
                }
              }
            }
          }
        }
        assert(h != null);
        if(h == null) {
          System.out.println("bad");
        }
        d.setSpace(h);
      }
      connect(d.getLeft(), width, minDepth);
      connect(d.getRight(), width, minDepth);
    }
  }
  
  private static ArrayList<Dungeon> getRoomDungeons(Dungeon d) {
    if(d.getLeft() == null) {
      ArrayList<Dungeon> r = new ArrayList<>();
      r.add(d);
      return r;
    } else {
      ArrayList<Dungeon> left = getRoomDungeons(d.getLeft());
      ArrayList<Dungeon> right = getRoomDungeons(d.getRight());
      left.addAll(right);
      return left;
    }
  }
  
  public static void fillArray(Dungeon d, int[][] grid) {
    AABShape s = d.getSpace().getShape();
    for(int x = (int) s.getMinX(); x < s.getMaxX(); x++) {
      for(int y = (int) s.getMinY(); y < s.getMaxY(); y++) {
        grid[x][y] = 1;
      }
    }
    if(d.getLeft() != null) {
      fillArray(d.getLeft(), grid);
      fillArray(d.getRight(), grid);
    }
  }
  
  public static Vec2d getSpawnPos(Dungeon d) {
    if(d.getLeft() == null) {
      AABShape b = d.getSpace().getShape();
      Vec2d tl = b.getTopLeft().toVec2d();
      Vec2d s = b.getSize().toVec2d();
      return tl.plus(s.smult(.1));
    } else {
      return getSpawnPos(d.getLeft());
    }
  }
  
  public static Vec2d getDoorPos(Dungeon d) {
    if(d.getRight() == null) {
      AABShape b = d.getSpace().getShape();
      Vec2d tl = b.getTopLeft().toVec2d();
      Vec2d s = b.getSize().toVec2d();;
      return tl.plus(s.smult(.1));
    } else {
      if(r.nextBoolean()) {
        return getDoorPos(d.getRight());
      } else {
        return getDoorPos(d.getLeft());
      }
    }
  }
  
}
