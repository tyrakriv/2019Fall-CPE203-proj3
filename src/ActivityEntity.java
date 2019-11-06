import processing.core.PImage;

import java.util.List;

public abstract class ActivityEntity extends Entity {

    private final int resourceLimit;
    private final int actionPeriod;
    private int resourceCount;

    public ActivityEntity(String id, Point position, List<PImage> images, final int resourceLimit, final int actionPeriod, int resourceCount) {
        super(id, position, images);
        this.resourceLimit = resourceLimit;
        this.actionPeriod = actionPeriod;
        this.resourceCount = resourceCount;
    }

    public int getResourceLimit() { return resourceLimit; }

    public int getActionPeriod() {
        return actionPeriod;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
}
