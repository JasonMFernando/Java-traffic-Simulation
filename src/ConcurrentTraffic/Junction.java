package ConcurrentTraffic;

import java.util.Objects;

public class Junction implements Runnable{

    private String JunctionName;
    private Road[] EntryRoutes;
    private Road[] ExistRoutes;
    private boolean TimeGreen;
    private int timeToChange;
    private Vehicle shiftVehicle;
    private int exitRoadNumber;
    private int i =0;
    private Road currentRoad;
    TickCounter tickCounter = TickCounter.getInstance();
    private boolean showmsg;

    private int roadlessvehicles;

    private Logger log = new Logger();
    private String msg = "";

    public Junction(String junctionName , Road[] entryRoutes , Road[] exitRoutes , int timeToChange){
        this.JunctionName = junctionName;
        this.EntryRoutes = entryRoutes;
        this.ExistRoutes = exitRoutes;
        this.timeToChange = timeToChange;
        this.roadlessvehicles = 0;
        this.showmsg = true;
    }

    @Override
    public void run()
    {
        int targetTime;
        int perminuteTime;
        int noCarLetThrough = 0;
        int nocarperminute = 0;
        String Roadname = "";
        while(tickCounter.getProgramrun())
        {
            currentRoad = EntryRoutes[i];
            //noCarLetThrough = 0;
            nocarperminute = 0;
            if(currentRoad.roadIsEmpty())
            {    //Loop through the different roads true means not empty , false means empty
                //System.out.println("came in 1");
                targetTime = tickCounter.getTimeInInteger();
                perminuteTime = tickCounter.getTimeInInteger();
                targetTime = targetTime + (timeToChange);
                perminuteTime = perminuteTime + 60; //Every 1 minute
                while(currentRoad.roadIsEmpty())
                {
                    //System.out.println("came in 2  , remove junction " + JunctionName);
                    if (nocarperminute <= 12) {     //making sure only 12 cars pass
                        shiftVehicle = currentRoad.removeVehicleRoad();
                        if (shiftVehicle != null) {
                            //System.out.println("came in 3");
                            exitRoadNumber = getExitRoadNumberForDestination(this.JunctionName, shiftVehicle.getDestination()); //Got the exitRoadNumber from the junction while passing the Junction Name and Destination
                            //while(true) {       //Delete this later on
                            for (Road exitRoute : ExistRoutes) {
                                //System.out.println("Checking exit road " + exitRoute.getRoadNumber());
                                if (exitRoute.getRoadNumber() == exitRoadNumber) { //&& (exitRoadNumber != currentRoad.getRoadNumber())

                                    if (exitRoadNumber != currentRoad.getRoadNumber()) {

                                        //System.out.println("came in 4");
                                        while (!exitRoute.checkRoadSpace()) { //wait for a short duration before checking again , waits for the road to be free
                                            //System.out.println("came in 5 " + exitRoute.getCarsOnRoad() + " on road " + exitRoute.getRoadNumber());

                                            if (showmsg && (currentRoad.getCarsOnRoad()) != 0) {
                                                showmsg = false;
                                                String msg = "";
                                                msg = "Time: " + tickCounter.getSimTime() + " - " + JunctionName + ": " + noCarLetThrough + " cars , " + currentRoad.getCarsOnRoad() + " cars waiting on road " + currentRoad.getRoadNumber() + " GRIDLOCK";
                                                System.out.println("Time: " + tickCounter.getSimTime() + " - " + JunctionName + ": " + noCarLetThrough + " cars , " + currentRoad.getCarsOnRoad() + " cars waiting on road " + currentRoad.getRoadNumber() + " GRIDLOCK");
                                                noCarLetThrough = 0;
                                                Logger.log(msg);
                                            }
                                            try {
                                                Thread.sleep(100);  //sleep for 100 milliseconds
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            if (!tickCounter.getProgramrun()) { //excess vehicle are counted in the queue
                                                //System.out.println("added vehicle last moment" + shiftVehicle.getDestination() + " " + shiftVehicle.getEnterTime());
                                                roadlessvehicles++;
                                                //currentRoad.addVehicleRoad(shiftVehicle);
                                                break;
                                            }
                                        }

                                        showmsg = true;
                                        //if (exitRoute.getRoadNumber() == 8 || exitRoute.getRoadNumber() == 10) { //PLEASE DELETE LATER ONLY FOR TESTING PURPOSES
                                        //exitRoute.addVehicleRoad(shiftVehicle);

                                        //}
                                        if (tickCounter.getProgramrun()) {
                                            Roadname = exitRoute.getRoadName();
                                            exitRoute.addVehicleRoad(shiftVehicle);
                                            try {
                                                Thread.sleep(1000);  //sleep for 1000 milliseconds
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            //System.out.println("Added vehicle from " + JunctionName + " to road " + exitRoute.getRoadNumber());
                                            noCarLetThrough++;
                                            nocarperminute++;
                                        }

                                        //System.out.println("noCarLetThrough " + noCarLetThrough + " from junction " + JunctionName);
//                                try {
//                                    Thread.sleep(1000);  //sleep for 100 milliseconds
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
                                    } else {
                                        exitRoute.addVehicleRoad(shiftVehicle);
                                        //System.out.println("Added vehicle from " + JunctionName + " to road " + exitRoute.getRoadNumber());
                                    }
                                }
                            }
                            //test
                            while (tickCounter.getTimeInInteger() <= targetTime && tickCounter.getProgramrun()) {
                                //System.out.println("stuck here " + tickCounter.getTimeInInteger() + " " + targetTime);
                                try {
                                    Thread.sleep(100);  //sleep for 100 milliseconds
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            //System.out.println("Time: " + tickCounter.getSimTime() + " - " + JunctionName + ": " + noCarLetThrough + " cars , " + currentRoad.getCarsOnRoad() + " cars waiting on road " + currentRoad.getRoadNumber());
                            //end test
                            if (tickCounter.getTimeInInteger() >= targetTime) {

                                msg = "Time: " + tickCounter.getSimTime() + "Junction " + JunctionName + ": " + noCarLetThrough + " cars through from " + Roadname + " , " + currentRoad.getCarsOnRoad() + " cars waiting on road " + currentRoad.getRoadNumber();
                                System.out.println("Time: " + tickCounter.getSimTime() + "Junction " + JunctionName + ": " + noCarLetThrough + " cars through from " + Roadname + " , " + currentRoad.getCarsOnRoad() + " cars waiting on road " + currentRoad.getRoadNumber());
                                Logger.log(msg);
                                msg = "";
                                noCarLetThrough = 0;
                                break;
                            }
                            if (tickCounter.getTimeInInteger() >= perminuteTime) {
                                perminuteTime = perminuteTime + 60;
                            }
                        }
                        //}
                        if (!tickCounter.getProgramrun()) {
                            break;
                        }
                    }
                    if (tickCounter.getTimeInInteger() >= perminuteTime) {
                        perminuteTime = perminuteTime + 60;
                        nocarperminute = 0;
                    }
                    if (tickCounter.getTimeInInteger() >= targetTime) {
                        msg = "Time: " + tickCounter.getSimTime() + "Junction " + JunctionName + ": " + noCarLetThrough + " cars through from " + Roadname + " , " + currentRoad.getCarsOnRoad() + " cars waiting on road " + currentRoad.getRoadNumber();
                        System.out.println("Time: " + tickCounter.getSimTime() + "Junction " + JunctionName + ": " + noCarLetThrough + " cars through from " + Roadname + " , " + currentRoad.getCarsOnRoad() + " cars waiting on road " + currentRoad.getRoadNumber());
                        Logger.log(msg);
                        msg = "";
                        noCarLetThrough = 0;
                        break;
                    }
                }
            }


            i++;
            //System.out.println("Time: " + tickCounter.getSimTime() + " - " + JunctionName + ": " + noCarLetThrough + " cars , " + currentRoad.getCarsOnRoad() + " cars waiting on road " + currentRoad.getRoadNumber());
            //System.out.println(i + " " + EntryRoutes.length);
            if(i >= EntryRoutes.length){ //Loop it to go back to the Beginning road
                i=0;
            }

        }

    }

    public int getExitRoadNumberForDestination(String junctionName , String destination){
        //This contains the Logic of the Road map , and the numbers they are returning are the RoadNumbers for which ExitRoad they should go out of
        if(junctionName == "A"){
            if(destination == "Industrial Park"){
                return 2;
            } else{
                return 3;
            }
        } else if (junctionName == "B"){
            if(destination == "Industrial Park"){
                return 4;
            } else {
                return 6;
            }
        } else if (junctionName == "C"){
            if(destination == "Industrial Park"){
                return 7;
            } else if (destination == "Shopping Centre"){
                return 9;
            } else {
                return 10;
            }
        } else {
            if (destination == "University"){
                return 12;
            } else {
                return 11;
            }
        }
    }

    public int getRoadlessvehicles(){
        return roadlessvehicles;
    }
        // Logic to control traffic lights and manage cars passing through
}
