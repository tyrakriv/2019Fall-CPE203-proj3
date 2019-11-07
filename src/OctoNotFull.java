import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class OctoNotFull extends Octo {

    public OctoNotFull(String id,
                    Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(this.getPosition(), Fish.class);


        if (!notFullTarget.isPresent() ||
                !moveTo(world, notFullTarget.get(), scheduler) ||
                !transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    new ActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public Octo transformHelper() {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            OctoFull octo = new OctoFull(this.getId(), this.getPosition(), this.getImages(),
                    this.getActionPeriod()); //((AnimationEntity)this).getAnimationPeriod()); // two resource limits?
            return octo;
        }
        return null;
    }
}
