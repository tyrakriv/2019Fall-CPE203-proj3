import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class OctoFull extends Octo{

    private static int resourceLimit;
    private static int resourceCount;

    public OctoFull(String id, Point position,
                  List<PImage> images, int resourceLimit, int resourceCount,
                  int actionPeriod) {
        super(id, position, images, actionPeriod, 0);
        this.resourceCount = resourceCount;
        this.resourceLimit = resourceLimit;

    }

    public int getResourceLimit() { return resourceLimit; }

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    public void executeActivity(WorldModel world,
                                        ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), Atlantis.class);

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), scheduler))
        {
            //at atlantis trigger animation
            ((ActivityEntity)fullTarget.get()).scheduleActions(scheduler, world, imageStore);

            //transform to unfull
            transform(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    new ActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public Octo transformHelper(){
        OctoNotFull octo = new OctoNotFull(this.getId(), this.getResourceLimit(),
                this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
                this.getImages());
        return octo;
    }
}
