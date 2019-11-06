import processing.core.PImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class OctoFull extends AnimationEntity{

    public OctoFull(String id, Point position,
                  List<PImage> images, int resourceLimit, int resourceCount,
                  int actionPeriod) {
        super(id, position, images, resourceLimit, actionPeriod, resourceCount, 0);

    }

    public Point nextPositionOcto(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz,
                this.getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x,
                    this.getPosition().y + vert);
            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = this.getPosition();
            }
        }
        return newPos;
    }
    public void executeActivity(WorldModel world,
                                        ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), Atlantis.class);

        if (fullTarget.isPresent() &&
                world.moveToFull(this, fullTarget.get(), scheduler))
        {
            //at atlantis trigger animation
            ((ActivityEntity)fullTarget.get()).scheduleActions(scheduler, world, imageStore);

            //transform to unfull
            transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    new ActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        OctoNotFull octo = new OctoNotFull(this.getId(), this.getResourceLimit(),
                this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
                this.getImages());

        this.removeEntity(world, this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(octo);
        ((ActivityEntity)octo).scheduleActions(scheduler, world, imageStore);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), getActionPeriod());
        scheduler.scheduleEvent(this, new AnimationAction(this, 0), (getAnimationPeriod()));
    }
}
