package manueh.marvel_themod.core.enums;

public class Tier {
  private final int maxSpeed;
  
  private final int xzRange;
  
  private final int yRange;
  
  public Tier(int maxSpeed, int xzRange, int yRange) {
    this.maxSpeed = maxSpeed;
    this.xzRange = xzRange;
    this.yRange = yRange;
  }
  
  public int getMaxSpeed() {
    return this.maxSpeed;
  }
  
  public int getXZRange() {
    return this.xzRange;
  }
  
  public int getYRange() {
    return this.yRange;
  }
}
