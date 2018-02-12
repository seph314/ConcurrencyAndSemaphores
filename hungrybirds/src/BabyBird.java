/**
 * Representation of a baby bird
 * <p>
 * The baby birds eat out of a common dish that initially contains W worms.
 * Each baby bird repeatedly takes a worm, eats it, sleeps for a while, takes another worm, and so on.
 * If the dish is empty, the baby bird who discovers the empty dish chirps real loud to awaken the parent bird.
 */
public class BabyBird extends Thread {

    /* contains our shared dish */
    private Dish dish;

    /* takes our dish as an argument */
    BabyBird(Dish dish) {
        this.dish = dish;
    }

    /**
     * Implements run
     * Each BabyBird tries to eat, then sleep for a while
     */
    @Override
    public void run() {
        try {
            dish.eat(this);
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
