package ConcurrentTraffic;

public class CarPark implements Runnable {

    private String Destinationpark;
    private int Carparksize;
    private int availablespots;
    private Vehicle[] parkedCars;
    private Road parkRoad;
    private int NoOfcars;
    TickCounter tickCounter = TickCounter.getInstance();

    public CarPark(String destinationPark , int carparksize , Road ParkRoad){
        this.Destinationpark = destinationPark;
        this.Carparksize = carparksize;
        this.availablespots = carparksize;
        this.parkedCars = new Vehicle[carparksize];
        this.parkRoad = ParkRoad;
        this.NoOfcars = 0;
    }

    @Override
    public void run(){
        int targetTime;
        TickCounter tickCounter = TickCounter.getInstance();
        while(tickCounter.getProgramrun()){
            targetTime = tickCounter.getTimeInInteger();
            targetTime = targetTime + (600); //20 mins is equal to 1200 seconds
            if (availablespots != 0)
            {
                if(parkRoad.roadIsEmpty()) {
                    Vehicle newVehicle = parkRoad.removeVehicleRoad();
                    newVehicle.setParkedtime(tickCounter.getSimTime());
                    addVehicleToPark(newVehicle);
                    try {
                        Thread.sleep(1200);  //1200 milliseconds means 12 seconds in simulated
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//

                } //else{
               //     System.out.println("No cars on road to " + Destinationpark + " Park");
               // }

            } else{
                //System.out.println("Car Park for " + Destinationpark + " is full");
            }
        }
        // Logic for admitting cars and updating available spaces

    }

    public synchronized void addVehicleToPark(Vehicle vehicle) {
        if (!isFull()) {
            parkedCars[Carparksize - availablespots] = vehicle;
            availablespots--;
            //System.out.println("Vehicle parked at " + this.Destinationpark + " " + vehicle.getEnterTime());
        } else {
            //System.out.println("Car park is full. Cannot admit more vehicles.");
        }
    }

    public int getNoOfcars(){
        return Carparksize - availablespots;
    }

    public synchronized boolean isFull() {
        return availablespots == 0;
    }

    public int getAvailablespots(){
        return availablespots;
    }

    public String getAverageJourneyTime(){
        long totaltime = 0;
        long averagetime;
        for(Vehicle parkedvehicle : parkedCars){
            if(parkedvehicle != null){
                totaltime = totaltime + parkedvehicle.getParkedtime();
            }
        }

        if((Carparksize - availablespots) == 0  )
        {
            return "00m00s";
        }
        averagetime = totaltime / (Carparksize - availablespots);
        long minutes = averagetime / 60; // Extract minutes
        long seconds = averagetime % 60; // Extract remaining seconds
        return String.format("%dm%02ds", minutes, seconds);
    }

    public void displayCarsParked()
    {
        for(Vehicle vehicle :  parkedCars){
            if (vehicle != null){
                System.out.println(" Car entry time " + vehicle.getEnterTime());
            }

        }
    }

}
