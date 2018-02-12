import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Represents a dish with bird food
 * <p>
 * Given are n baby birds and one parent bird.
 * The baby birds eat out of a common dish that initially contains W worms.
 * Each baby bird repeatedly takes a worm, eats it, sleeps for a while, takes another worm, and so on.
 * If the dish is empty, the baby bird who discovers the empty dish chirps real loud to awaken the parent bird.
 * The parent bird flies off and gathers W more worms, puts them in the dish, and then waits for the dish to be empty again.
 * This pattern repeats forever.
 */
public class Dish {

    int numberOfWorms;

    public Dish(int numberOfWorms) {
        this.numberOfWorms = numberOfWorms;
    }

    /**
     * Semaphore controls the number of birds that is permitted to eat at the same time (should be one)
     */
    private Semaphore eatSemaphore = new Semaphore(1);
    private Semaphore emptyDishSemaphore = new Semaphore(1);
    boolean empty = false; /* keeps track if dish is empty or not */


    /**
     * Only one baby bird at the time can eat
     */
    public void eat(BabyBird babyBird) throws InterruptedException {

//        while(!empty){
        if (numberOfWorms > 0) {
            boolean permit = false;
            try {
                permit = eatSemaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("eatSemaphore acquired by: " + babyBird.getName());
                    numberOfWorms--;
                    System.out.println("Number of worms left: " + numberOfWorms);

                } else {
                    System.out.println("Could not acquire Semaphore");
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                if (permit) {
                    eatSemaphore.release();
//                    eat(babyBird);

                }
            }
        } else {
            empty = emptyDishSemaphore.tryAcquire(1, TimeUnit.SECONDS); /* set empty to true (one BabyBird only) */
            if (empty) {
                System.out.println(babyBird.getName() + " chirps real loud! There is no more food!");
                new ParentBird(this).run();
                emptyDishSemaphore.release(); /* release empty dish semaphore */
            }
//            babyBird.run();

        }

//        }

    }


    /**
     * Let parentBird work here...
     */
    public void gatherWorms(ParentBird parentBird) {
        if (empty) {
            System.out.println(parentBird.getName() + " says: Dont freight little ones! More food is coming!");
            numberOfWorms = (int) (Math.random() * 10 + 10); /* get random number of new worms */
            System.out.println("I got you " + numberOfWorms + " more tasty worms, chirps!");
//            emptyDishSemaphore.release(); /* release empty dish semaphore */
            empty = false;

        }
    }
}
