package ConcurrentTraffic;

import java.time.Instant;

public class Clock {
    private int currentTick;
    private boolean tickCont;
    private int endTick;

    public Clock(int endTick){
        this.currentTick= 0;
        tickCont = false;
        this.endTick = endTick;
    }

    public boolean isTickCont() {
        return tickCont;
    }

    public void setTickCont(boolean endTick)
    {
        this.tickCont = endTick;
    }

    public synchronized  void tick(){
        Instant nextTickTime = Instant.now();  // Initialize with current time
        int tickIntervalMillis = 1000;  // Tick every second
        if (Instant.now().isAfter(nextTickTime) && tickCont)  { //This Simulates a second
            currentTick++;
            System.out.println("currenttick " + currentTick);
            if(currentTick > endTick){
                tickCont = false;
            }
            nextTickTime = nextTickTime.plusMillis(tickIntervalMillis);  //Increment to the next target tick
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized  void cartick(int carsperhour){
        Instant nextTickTime = Instant.now();  // Initialize with current time
        long tickIntervalMillis = (endTick*1000)/carsperhour;  // Tick every second
        int count = 0;
        nextTickTime = nextTickTime.plusMillis(tickIntervalMillis);
        while (count <= 0) {
            if (Instant.now().isAfter(nextTickTime)) {
                //System.out.println("generate car " + count);
                count++;
                currentTick++;
                if(currentTick > endTick){
                    tickCont = false;
                }
                nextTickTime = nextTickTime.plusMillis(tickIntervalMillis);  // Schedule the next tick
            }

            try {
                Thread.sleep(1);  // Avoid excessive CPU usage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public synchronized  int getCurrentTick(){
        return currentTick;
    }
}
