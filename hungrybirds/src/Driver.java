import java.util.Scanner;

/**
* H U N G R Y   B I R D S   P R O B L E M
*
* Given are n baby birds and one parent bird.
* The baby birds eat out of a common dish that initially contains W worms.
* Each baby bird repeatedly takes a worm, eats it, sleeps for a while, takes another worm, and so on.
* If the dish is empty, the baby bird who discovers the empty dish chirps real loud to awaken the parent bird.
* The parent bird flies off and gathers W more worms, puts them in the dish, and then waits for the dish to be empty again.
* This pattern repeats forever.
*
* Develop and implement a multithreaded program to simulate the actions of the birds.
* Represent the birds as concurrent threads (i.e. array of "babyBird" threads and a "parentBird" thread), and the dish as a critical shared resource that can be accessed by at most one bird at a time.
* Use only semaphores for synchronization.
* Your program should print a trace of interesting simulation events.
* Is your solution fair? Explain when presenting homework.
*/

public class Driver {

    public static void main(String[] args) {

        /* contains number of baby birds (set by user) */
        int numberOfBabyBirds;

        /* create a shared dish */
        final Dish dish = new Dish(10);

        /* user interaction */
        System.out.println("How many baby birds do you want?");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Number of baby birds: ");
        numberOfBabyBirds = scanner.nextInt();

        /* create BabyBird threads */
        for (int i=0; i < numberOfBabyBirds; i++){
            new BabyBird(dish).run();
        }

        /* create ParentBird thread */
//        new ParentBird(dish).run();
    }
}
