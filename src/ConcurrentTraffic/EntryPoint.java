package ConcurrentTraffic;

import java.time.Clock;
import java.util.Random;

public class EntryPoint implements Runnable {

    private String direction;
    private int carsPerHour;
    private String entrytime;
    private int carsCreated;

    private Clock clock;

    //Probability of Destinations
    private double UniversityProbability = 10.0;
    private double StatioProbability = 20.0;
    private double ShoppingProbability = 30.0;
    private double IndustrailProbability = 40.0;

    private int universitycars = 0;
    private int stationcars = 0;
    private int shoppingcentre = 0;
    private int industrailPark = 0;

    Road road;

    public EntryPoint(String direction , int carsPerHour , Road road){
        this.direction = direction;
        this.carsPerHour = carsPerHour;
        this.road = road;
        this.carsCreated = 0;
    }

    public String generateDestination(){
        Random random = new Random();
        double ranNum = random.nextDouble();
        ranNum = ranNum * 100;
        if (ranNum < UniversityProbability){
            universitycars++;
            return "University";
        }else if (ranNum < (UniversityProbability + StatioProbability)){
            stationcars++;
            return "Station";
        } else if (ranNum < (UniversityProbability + ShoppingProbability + StatioProbability )){
            shoppingcentre++;
            return "Shopping Centre";
        }else{
            industrailPark++;
            return "Industrial Park";
        }
    }

    @Override
    public void run() {
        Clock clock = Clock.systemDefaultZone();
        TickCounter tickCounter = TickCounter.getInstance();
        long startTime  = clock.millis();
        double tickspercar = (double) 360000 / this.carsPerHour;
        int count = 1;
        while (tickCounter.getProgramrun()) {
            long currentTime = clock.millis();
            if (currentTime - startTime >= tickspercar) {
                //test
                if(!road.roadIsEmpty()){
                    String carDestination = generateDestination();
                    entrytime = tickCounter.getSimTime();
                    Vehicle vehicle = new Vehicle(carDestination , entrytime);  //Created the vehicle object
                    carsCreated++;
                    road.addVehicleRoad(vehicle);
                    //System.out.println("Car generated " + count++ + "Destination: " + carDestination + " SimTime: " + entrytime);
                } else {
                    //System.out.println("Road is full , can't add");
                }
                //end test

                startTime = currentTime;
            }
        }
        //Logic to generate the vehicles at a predefined rate and add them to the road
    }


    public int getCarsCreated(){
        return carsCreated;
    }

    public void displayVehicleDestination(){
        System.out.println("            univeristy : " + universitycars);
        System.out.println("            station    : " + stationcars);
        System.out.println("       shopping centre : " + shoppingcentre);
        System.out.println("       industrail park : " + industrailPark);
    }
}
