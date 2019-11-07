import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class Moveable extends AnimationEntity{

    public Moveable(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            if (this instanceof OctoNotFull) {
                ((OctoNotFull) this).setResourceCount(((OctoNotFull) this).getResourceCount() + 1);
                target.removeEntity(world, target);
                scheduler.unscheduleAllEvents(target);
            }
            if (this instanceof Crab) {
                target.removeEntity(world, target);
                scheduler.unscheduleAllEvents(target);
            }
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());

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

    protected abstract Point nextPosition(WorldModel world, Point destPos);
}
