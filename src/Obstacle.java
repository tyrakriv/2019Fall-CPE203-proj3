import processing.core.PImage;

import java.util.List;

public class Obstacle extends Entity {

    public Obstacle(String id, Point position,
                       List<PImage> images)
    {
        super(id, position, images);
    }


//    public int getActionPeriod() {
//        return actionPeriod;
//    }
//
//    public int getResourceCount() {
//        return resourceCount;
//    }
//
//    public void setResourceCount(int resourceCount) {
//        this.resourceCount = resourceCount;
//    }
//
//    public Point getPosition() {
//        return position;
//
//    }
//    public void setPosition(Point position) {
//        this.position = position;
//    }
//
//    public List<PImage> getImages() { return images; }
//    public int getImageIndex() { return imageIndex; }
//    public int getResourceLimit() { return resourceLimit; }
//
//    public String getId() { return id; }
//
//    public void nextImage()
//    {
//        this.imageIndex = (this.imageIndex + 1) % this.images.size();
//    }
//
//    public PImage getCurrentImage()
//    {
//        return getImages().get(getImageIndex());
//    }

//    public void removeEntity(WorldModel world, Entity entity)
//    {
//        removeEntityAt(world, entity.getPosition());
//    }
//
//    public void removeEntityAt(WorldModel world, Point pos)
//    {
//        if (world.withinBounds(pos)
//                && world.getOccupancyCell(pos) != null)
//        {
//            Entity entity = world.getOccupancyCell(pos);
//
//         /* this moves the entity just outside of the grid for
//            debugging purposes */
//            this.position = new Point(-1, -1);
//            world.getEntities().remove(this);
//            world.setOccupancyCell( pos, null);
//        }
//    }
}
