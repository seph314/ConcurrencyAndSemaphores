/**
 * Representation of a parent bird
 * <p>
 * Given are n baby birds and one parent bird.
 * The baby birds eat out of a common dish that initially contains W worms.
 * Each baby bird repeatedly takes a worm, eats it, sleeps for a while, takes another worm, and so on.
 * If the dish is empty, the baby bird who discovers the empty dish chirps real loud to awaken the parent bird.
 * The parent bird flies off and gathers W more worms, puts them in the dish, and then waits for the dish to be empty again.
 * This pattern repeats forever.
 */
public class ParentBird extends Thread {

    private Dish dish;

    ParentBird(Dish dish) {
        this.dish = dish;
    }

    @Override
    public void run() {
        dish.gatherWorms(this);
    }
}

