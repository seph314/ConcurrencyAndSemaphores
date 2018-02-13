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
public class Dish{

    private int numberOfWorms;

    public Dish(int numberOfWorms) {
        this.numberOfWorms = numberOfWorms;
    }

    /**
     * Semaphore controls the number of birds that is permitted to eat at the same time (should be one)
     */
    private Semaphore eatSemaphore = new Semaphore(1);
    private Semaphore emptyDishSemaphore = new Semaphore(1);
    private Semaphore chirp = new Semaphore(0);


    /**
     * Only one baby bird at the time can eat
     */
    public void eat(BabyBird babyBird) throws InterruptedException {

        if (numberOfWorms > 0) {
            boolean permit = false;
            try {
                permit = eatSemaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
//                    System.out.println("eatSemaphore acquired by: " + babyBird.getName());
                    System.out.println(babyBird.getName() + " ate a worm!");
                    numberOfWorms--;
                    System.out.println("Number of worms left: " + numberOfWorms);

                } else {
                    System.out.println("Could not acquire Semaphore\n");
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                if (permit) {
                    System.out.println(babyBird.getName() + " released the eatSemaphore");
                    eatSemaphore.release();
                }
            }
        } else {
            boolean empty = emptyDishSemaphore.tryAcquire(1, TimeUnit.SECONDS);
            if (empty) {
                System.out.println(babyBird.getName() + " CHIRPS REEEAL LOUD! There is no more food!\n\n");
                chirp.release(1);
            }
        }
    }

    /**
     * Let parentBird work here...
     */
    public void gatherWorms(ParentBird parentBird) {
        if (chirp.tryAcquire()) {
            System.out.println("\n" + parentBird.getName() + " says: Dont freight little ones! More food is coming!");
            numberOfWorms = (int) (Math.random() * 10 + 10); /* get random number of new worms */
            System.out.println("I got you " + numberOfWorms + " more tasty worms, chirps!\n\n");
            emptyDishSemaphore.release(); /* release empty dish semaphore */
//            ai.getAndDecrement();
        }
//        System.out.println("ParentBird's name " + parentBird.getName());
    }

}
