package game.objects;

import engine.objects.CollidibleObject;
import engine.objects.DrawableObject;
import engine.objects.GameObject;
import template.Vec2d;

public abstract class WizObject extends GameObject implements CollidibleObject, DrawableObject {
  
  public abstract Vec2d getPosition();
}
