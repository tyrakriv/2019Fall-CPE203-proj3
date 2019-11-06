import processing.core.PImage;

import java.util.List;

public abstract class AnimationEntity extends ActivityEntity{

    private final int animationPeriod;

    public AnimationEntity(String id, Point position, List<PImage> images, int resourceLimit, int actionPeriod, int resourceCount, int animationPeriod) {
        super(id, position, images, resourceLimit, actionPeriod, resourceCount);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod(){
        return animationPeriod;
    }
    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);

}
