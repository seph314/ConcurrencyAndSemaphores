import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Given are n honeybees and a hungry bear.
 * They share a pot of honey.
 * The pot is initially empty; its capacity is H portions of honey.
 * The bear sleeps until the pot is full, then eats all the honey and goes back to sleep.
 * Each bee repeatedly gathers one portion of honey and puts it in the pot; the bee who fills the pot awakens the bear.
 *
 * Develop and implement a multithreaded program to simulate the actions of the bear and honeybees.
 * Represent the bear and honeybees as concurrent threads (i.e. a "bear" thread and an array of "honeybee" threads), and the pot as a critical shared resource that can be accesses by at most one thread at a time.
 * Use only semaphores for synchronization.
 * Your program should print a trace of interesting simulation events.
 * Is your solution fair (w.r.t. honeybees)? Explain when presenting homework.
 */
public class HoneyDriver {

    public static void main(String[] args) {

        /* number of Honeybees */
        int numberOfHoneyBees;
        /* shared resource, initially empty */
        final Pot pot = new Pot(0);

        /* user interaction */
        System.out.println("How many HoneyBees do you want?");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Number of HoneyBees: ");
        numberOfHoneyBees = scanner.nextInt();

        /* create HoneyBees */
        List<HoneyBee> honeyBees = new ArrayList<>();
        for (int i=0; i<numberOfHoneyBees; i++){
            honeyBees.add(new HoneyBee(pot));
        }

        /* create Bear */
        Bear bear = new Bear(pot);

        /* stream Bees and Bears */
        while(true){
            honeyBees.parallelStream().forEach(HoneyBee::run);
            bear.run();
        }
    }
}
