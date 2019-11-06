import processing.core.PImage;

import java.util.List;

public abstract class AnimationEntity extends ActivityEntity{

    private final int animationPeriod;
    private int imageIndex;

    public AnimationEntity(String id, Point position, List<PImage> images, int resourceLimit, int actionPeriod, int resourceCount, int animationPeriod) {
        super(id, position, images, resourceLimit, actionPeriod, resourceCount);
        this.animationPeriod = animationPeriod;
        this.imageIndex = 0;
    }

    public int getAnimationPeriod(){
        return animationPeriod;
    }
    public int getImageIndex() { return imageIndex; }

    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);

    public void nextImage()
    {
        this.imageIndex = (this.imageIndex + 1) % this.getImages().size();
    }

}
