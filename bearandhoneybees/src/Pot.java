import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Given are n honeybees and a hungry bear.
 * They share a pot of honey.
 * The pot is initially empty; its capacity is H portions of honey.
 * The bear sleeps until the pot is full, then eats all the honey and goes back to sleep.
 * Each bee repeatedly gathers one portion of honey and puts it in the pot; the bee who fills the pot awakens the bear.
 * <p>
 * Develop and implement a multithreaded program to simulate the actions of the bear and honeybees.
 * Represent the bear and honeybees as concurrent threads (i.e. a "bear" thread and an array of "honeybee" threads), and the pot as a critical shared resource that can be accesses by at most one thread at a time.
 * Use only semaphores for synchronization.
 * Your program should print a trace of interesting simulation events.
 * Is your solution fair (w.r.t. honeybees)? Explain when presenting homework.
 */
public class Pot {

    private int fullPot = 20;
    private int portionsOfHoney;

    Pot(int portionsOfHoney) {
        this.portionsOfHoney = portionsOfHoney;
    }

    /* semaphores */
    private Semaphore fillPotSemaphore = new Semaphore(1);
    private Semaphore potIsFulSemaphore = new Semaphore(1);
    private Semaphore awakeSemaphore = new Semaphore(0);

    /**
     * Bees fills pot
     */
    public void fillPot(HoneyBee honeyBee) throws InterruptedException {

        if (portionsOfHoney < fullPot) {
            boolean permit = false;
            try {
                permit = fillPotSemaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    portionsOfHoney++;
                    System.out.println(honeyBee.getName() + " added a portion to the Pot");
                    System.out.println("Current portions of honey filled " + portionsOfHoney + " of " + fullPot);
                } else {
                    System.out.println(honeyBee.getName() + " could not add portion");
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                if (permit) {
                    fillPotSemaphore.release();
                    System.out.println(honeyBee.getName() + " released the eatSemaphore");
                }
            }
        } else {
            if (potIsFulSemaphore.tryAcquire(1,TimeUnit.SECONDS)){
                System.out.println(honeyBee.getName() + " BZZZzzzzz, Wakey wakey Bear! Dinner's ready!");
                awakeSemaphore.release(1);
            }
        }
    }


    public void eatAll(Bear bear){
        if(awakeSemaphore.tryAcquire()){
            System.out.println("\n\nI'm awake, I'm awake... says " + bear.getName() + ". That smells delicious! Good work little fellows.");
            portionsOfHoney = 0;
            System.out.println("That was good, but now the Pot has " + portionsOfHoney + " portions left\n\n");
            potIsFulSemaphore.release();
//            awakeSemaphore.release();
        }
    }
}
