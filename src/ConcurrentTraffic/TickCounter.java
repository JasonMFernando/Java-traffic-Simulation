package ConcurrentTraffic;
import java.time.Clock;

public class TickCounter extends Thread{
    private static TickCounter instance;
    private String SimTime;
    private int Tick;
    private int minutes = 0;
    private  int seconds = 0;
    private String simTime;

    private boolean programRun = true;

    public static TickCounter getInstance()
    {
        if(instance == null){
            instance = new TickCounter();
            instance.start();
        }
        return instance;
    }

    @Override
    public void run(){
        Clock clock = Clock.systemDefaultZone();
        long startTime = System.currentTimeMillis();
        int count = 1;

        while (programRun) {
            long currentTime = System.currentTimeMillis();

            if (currentTime - startTime >= 100) {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
                this.simTime = String.format("%02dm%02ds", minutes, seconds);
                startTime = currentTime;
                count++;
            }
            if(count > 3600) //100 = 10s , 600 60s
            {
                programRun= false;
            }
        }
    }

    public boolean getProgramrun(){
        return this.programRun;
    }

    public String getSimTime(){
        return this.simTime;
    }

    public int getTimeInInteger(){
        return (minutes * 60) + seconds;
    }
}
