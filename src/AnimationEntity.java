public interface AnimationEntity extends Entity{
    public int getAnimationPeriod();
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
}
