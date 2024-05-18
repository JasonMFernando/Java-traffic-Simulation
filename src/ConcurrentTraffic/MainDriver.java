package ConcurrentTraffic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

public class MainDriver {

    public MainDriver(){
    }

    public static void main(String[] args){

        Logger.clearLogFile();

        int[] CarPerHourRates = new int[3]; //North , East , South
        int[] junctionTimings = new int[4]; // For junctions , A , B , C , D

        ReadFileSetValue("Scenario4.txt" , CarPerHourRates , junctionTimings);


        TickCounter tickCounter = TickCounter.getInstance();
        //Road Objects
        Road road1 = new Road(1 , 60 , "South");
        Road road2 = new Road(2 , 15 , "A to Industrail");
        Road road3 = new Road(3 , 7 , "A to B");
        Road road4 = new Road(4 , 7 , "B to A");
        Road road5 = new Road(5 , 30 , "East");
        Road road6 = new Road(6 , 10 , "B to C");
        Road road7 = new Road(7 , 10 , "C to B");
        Road road8 = new Road(8 , 50 , "North");
        Road road9  = new Road(9 , 7 , "C to Shopping Centre");
        Road road10 = new Road(10 , 10 , " C to D");
        Road road11 = new Road(11 , 15 , "D to University");
        Road road12 = new Road(12 , 15 , "D to Station");


        //Entrypoint objects
        EntryPoint epNorth = new EntryPoint("North" , CarPerHourRates[0] , road8);
        EntryPoint epEast = new EntryPoint("East" , CarPerHourRates[1] , road5);
        EntryPoint epSouth = new EntryPoint("South" , CarPerHourRates[2] , road1);





        Thread epNorthThread = new Thread(epNorth);
        Thread epEastThread = new Thread(epEast);
        Thread epSouthThread = new Thread(epSouth);


        Road[] entryRoutes1 = {road1, road4};
        Road[] exitRoutes1 = {road2, road3};
        Junction junctionA = new Junction("A", entryRoutes1, exitRoutes1, junctionTimings[0]);

        Road[] entryRoutes2 = {road3, road5 , road7};
        Road[] exitRoutes2 = {road4, road6};
        Junction junctionB = new Junction("B", entryRoutes2, exitRoutes2, junctionTimings[1]);

        Road[] entryRoutes3 = {road6, road8};
        Road[] exitRoutes3 = {road7, road9 , road10};
        Junction junctionC = new Junction("C", entryRoutes3, exitRoutes3, junctionTimings[2]);

        Road[] entryRoutes4 = {road10};
        Road[] exitRoutes4 = {road11, road12};
        Junction junctionD = new Junction("D", entryRoutes4, exitRoutes4, junctionTimings[3]);

        CarPark university = new CarPark("University" , 100 , road12);
        CarPark station = new CarPark("Station" , 150 , road11);
        CarPark shoppingcentre = new CarPark("Shopping Centre" , 400 , road9);
        CarPark industrialPark = new CarPark("Industrial Park" , 1000 , road2);

        Thread junctionAThread = new Thread(junctionA);
        Thread junctionBThread = new Thread(junctionB);
        Thread junctionCThread = new Thread(junctionC);
        Thread junctionDThread = new Thread(junctionD);

        Thread universityPark = new Thread(university);
        Thread stationPark = new Thread(station);
        Thread shoppingcentrePark = new Thread(shoppingcentre);
        Thread industrialParkPark = new Thread(industrialPark);


        epNorthThread.start();
        epEastThread.start();
        epSouthThread.start();


        junctionAThread.start();
        junctionBThread.start();
        junctionCThread.start();
        junctionDThread.start();

        universityPark.start();
        stationPark.start();
        shoppingcentrePark.start();
        industrialParkPark.start();

        int targetTime;
        targetTime = tickCounter.getTimeInInteger();
        targetTime = targetTime + (600); //10 mins is equal to 600 seconds

        while(tickCounter.getProgramrun()){
            if(tickCounter.getTimeInInteger() >= targetTime){
                //System.out.println("hello every 10 seconds");
                String msg1 = "Time : " + tickCounter.getSimTime() + " University:      " + university.getAvailablespots() + "Spaces";
                String msg2 = "              Station:         " + station.getAvailablespots() + "Spaces";
                String msg3 = "              Shopping Centre: " + shoppingcentre.getAvailablespots() + " Spaces";
                String msg4 = "              Industrail Park: " + industrialPark.getAvailablespots() + " Spaces";

                Logger.log(msg1);
                Logger.log(msg2);
                Logger.log(msg3);
                Logger.log(msg4);

                System.out.println("Time : " + tickCounter.getSimTime() + " University:      " + university.getAvailablespots() + "Spaces");
                System.out.println("              Station:         " + station.getAvailablespots() + "Spaces");
                System.out.println("              Shopping Centre: " + shoppingcentre.getAvailablespots() + " Spaces");
                System.out.println("              Industrail Park: " + industrialPark.getAvailablespots() + " Spaces");

                msg1 = "";
                msg2 = "";
                msg3 = "";
                msg4 = "";
                targetTime = targetTime + 600;
            }
            try {
                Thread.sleep(1000); // Wait for 1 second before checking again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (tickCounter.getProgramrun()) {
            try {
                Thread.sleep(1000); // Wait for 1 second before checking again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(200); // Wait for 1 second before checking again
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String msg5 = "";
        System.out.println("Program end");
        System.out.println("North created " + epNorth.getCarsCreated() + "cars");
        epNorth.displayVehicleDestination();

        System.out.println("East created " + epEast.getCarsCreated() + "cars");
        epEast.displayVehicleDestination();

        System.out.println("South created " + epSouth.getCarsCreated() + "cars");
        epSouth.displayVehicleDestination();

        int VehicleQueued = 0;
        VehicleQueued = road1.getCarsOnRoad() + road2.getCarsOnRoad() + road3.getCarsOnRoad() + road4.getCarsOnRoad() + road5.getCarsOnRoad() + road6.getCarsOnRoad() + road7.getCarsOnRoad() + road8.getCarsOnRoad() + road9.getCarsOnRoad() + road10.getCarsOnRoad() + road11.getCarsOnRoad() + road12.getCarsOnRoad() + junctionA.getRoadlessvehicles() + junctionB.getRoadlessvehicles() + junctionC.getRoadlessvehicles() + junctionD.getRoadlessvehicles();
        System.out.println("Vehicles on queue " + VehicleQueued);

//        road1.DisplayVehiclesOnRoad();
//        road2.DisplayVehiclesOnRoad();
//        road3.DisplayVehiclesOnRoad();
//        road4.DisplayVehiclesOnRoad();
//        road5.DisplayVehiclesOnRoad();
//        road6.DisplayVehiclesOnRoad();
//        road7.DisplayVehiclesOnRoad();
//        road8.DisplayVehiclesOnRoad();
//        road9.DisplayVehiclesOnRoad();
//        road10.DisplayVehiclesOnRoad();
//        road11.DisplayVehiclesOnRoad();
//        road12.DisplayVehiclesOnRoad();

        System.out.println("University: " + university.getNoOfcars() + " Cars parked , average journey time " + university.getAverageJourneyTime());
        System.out.println("Station: " + station.getNoOfcars() + " Cars parked , average journey time " + station.getAverageJourneyTime());
        System.out.println("Shopping Centre: " + shoppingcentre.getNoOfcars() + " Cars parked , average journey time " + shoppingcentre.getAverageJourneyTime());
        System.out.println("Industrail Park: " + industrialPark.getNoOfcars() + " Cars parked , average journey time " + industrialPark.getAverageJourneyTime());

        msg5 = "Program end \n" + "North created " + epNorth.getCarsCreated() + "cars \n" + "East created " + epEast.getCarsCreated() + "cars \n" + "South created " + epSouth.getCarsCreated() + "cars \n" + "Vehicles on queue " + VehicleQueued + "\n University: " + university.getNoOfcars() + " Cars parked , average journey time " + university.getAverageJourneyTime() + "\n Station: " + station.getNoOfcars() + " Cars parked , average journey time " + station.getAverageJourneyTime() + " \n Shopping Centre: " + shoppingcentre.getNoOfcars() + " Cars parked , average journey time " + shoppingcentre.getAverageJourneyTime() + " \n Industrail Park: " + industrialPark.getNoOfcars() + " Cars parked , average journey time " + industrialPark.getAverageJourneyTime();
        Logger.log(msg5);
        //industrialPark.displayCarsParked();



//        Clock clock = new Clock(10);
//        int count = 1;
//        while(!clock.isTickCont()){
//            System.out.println("Generate car " + count );
//            clock.cartick(550);
//            clock.tick();
//            count++;
//        }


    }

    public static void ReadFileSetValue(String filename, int[] carRates, int[] junctionTimes) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String msg = "Reading from the " + filename + "file";
            System.out.println(msg);
            Logger.log(msg);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    switch (parts[0]) {
                        case "North":
                            carRates[0] = Integer.parseInt(parts[1]);
                            break;
                        case "East":
                            carRates[1] = Integer.parseInt(parts[1]);
                            break;
                        case "south":
                            carRates[2] = Integer.parseInt(parts[1]);
                            break;
                        case "A":
                            junctionTimes[0] = Integer.parseInt(parts[1]);
                            break;
                        case "B":
                            junctionTimes[1] = Integer.parseInt(parts[1]);
                            break;
                        case "C":
                            junctionTimes[2] = Integer.parseInt(parts[1]);
                            break;
                        case "D":
                            junctionTimes[3] = Integer.parseInt(parts[1]);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void printParkingStatus(String time){

    }

     static void tick(int tickcount) {
        Instant nextTickTime = Instant.now();  // Initialize with current time
        long tickIntervalMillis = tickcount;  // Tick every second
         int count = 0;
         nextTickTime = nextTickTime.plusMillis(tickIntervalMillis);
        while (count <= 0) {
            if (Instant.now().isAfter(nextTickTime)) {
                System.out.println("generate car " + count);
                count++;
                nextTickTime = nextTickTime.plusMillis(tickIntervalMillis);  // Schedule the next tick
            }

            try {
                Thread.sleep(1);  // Avoid excessive CPU usage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}


