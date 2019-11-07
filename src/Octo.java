import processing.core.PImage;

import java.util.List;

public abstract class Octo extends Moveable {

    public Octo(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz,
                this.getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x,
                    this.getPosition().y + vert);
            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPosition();
            }
        }
        return newPos;
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Octo octo = transformHelper();

        if (octo != null){
            this.removeEntity(world, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(octo);
            octo.scheduleActions(scheduler, world, imageStore);

            return true;
        }
        return false;
    }

    protected abstract Octo transformHelper();

}
