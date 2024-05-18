package ConcurrentTraffic;

public class Vehicle {
    private String destination;
    private String enterTime;
    private long parkedtime;


    public Vehicle(String destination, String entryTime) {
        this.destination = destination;
        this.enterTime = entryTime;
        this.parkedtime = 0;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public long getParkedtime() {
        return parkedtime;
    }

    public void setParkedtime(String parkedtime) {
        String[] parts = parkedtime.split("m|s"); // Split the string at 'm' and 's' to get minutes and seconds
        int minutes = Integer.parseInt(parts[0]); // Extract minutes part
        int seconds = Integer.parseInt(parts[1]); // Extract seconds part
        int totalTimeSeconds = minutes * 60 + seconds; // Convert minutes to seconds and add to seconds
        this.parkedtime = totalTimeSeconds;
    }

//    public String getDestinationForJunction() {
//        return destination;
//    }
}




