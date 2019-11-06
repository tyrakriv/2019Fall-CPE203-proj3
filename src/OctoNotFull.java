import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class OctoNotFull extends AnimationEntity {

    private int resourceLimit;
    private int resourceCount;

    public OctoNotFull(String id, int resourceLimit,
                    Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        super(id, position, images, actionPeriod, animationPeriod);
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

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(this.getPosition(), Fish.class);


        if (!notFullTarget.isPresent() ||
                !moveToNotFull(world, notFullTarget.get(), scheduler) ||
                !transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    new ActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            OctoFull octo = new OctoFull(this.getId(), this.getPosition(), this.getImages(),
                    this.getResourceLimit(), this.getResourceLimit(), this.getActionPeriod()); //((AnimationEntity)this).getAnimationPeriod()); // two resource limits?

            this.removeEntity(world, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(octo);
            ((ActivityEntity)octo).scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }
    public boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            this.setResourceCount(this.getResourceCount() + 1);
            target.removeEntity(world, target);
            scheduler.unscheduleAllEvents(target);

            return true;
        } else {
            Point nextPos = this.nextPositionOcto(world, target.getPosition());

                if (!this.getPosition().equals(nextPos)) {
                    Optional<Entity> occupant = world.getOccupant(nextPos);
                    if (occupant.isPresent()) {
                        scheduler.unscheduleAllEvents(occupant.get());
                    }

                    world.moveEntity(this, nextPos);

            }
            return false;
        }
    }
}

