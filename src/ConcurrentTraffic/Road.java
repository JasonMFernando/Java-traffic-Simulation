package ConcurrentTraffic;

public class Road {
    private int RoadSizeMax;
    private Vehicle[] vehicles;
    private int head;
    private int tail;
    private int currentRoadSize;
    private int roadNumber;
    private String roadName;

    private boolean roadAvailable;

    public Road(int roadNumber , int capacity , String roadName)
    {
        this.roadNumber = roadNumber;
        this.RoadSizeMax = capacity;
        this.vehicles = new Vehicle[this.RoadSizeMax];
        this.roadName = roadName;
    }

    public synchronized boolean checkRoadSpace() { //roadempty false , roadspace true
        int count = 0;
        for (Vehicle vehicle : this.vehicles) {
            if (vehicle != null) {
                count++;
            }
        }
        //System.out.println(count + " " + roadNumber);
        return count < RoadSizeMax;
        //return this.vehicles.length != 0;
    }

    public synchronized void addVehicleRoad(Vehicle vehicle) {

        if (currentRoadSize == vehicles.length){
            //System.out.println("Road is full , vehicles can't join currently");
        }else{
            vehicles[head] = vehicle;
            head = (head + 1) % vehicles.length;
            currentRoadSize++;
            //System.out.println("Added vehicle " + vehicle.getEnterTime() + " " + vehicle.getDestination() + "to the Road " + roadNumber);
        }
    }

    public synchronized void setRoadAvailable(boolean roadAvailable){
        this.roadAvailable = roadAvailable;
    }

    public boolean getRoadAvailable(){
        return roadAvailable;
    }

    public int getRoadNumber(){
        return this.roadNumber;
    }

    public String getRoadName(){
        return this.roadName;
    }

    public int getCarsOnRoad(){
        int vehcount = 0;
        for (Vehicle vehicle : this.vehicles) {
            if (vehicle != null) {
                vehcount++;
            }
        }
        //return vehicles.length;
        return vehcount;
    }

    public synchronized Vehicle removeVehicleRoad()
    {

        if(currentRoadSize == 0){
            //System.out.println("Road is empty , can't remove Vehicles");
            return null;
        }
        Vehicle VehicleRemoved = vehicles[tail];
        vehicles[tail] = null;
        tail = (tail + 1) % vehicles.length;
        currentRoadSize--;
        //System.out.println("Vehicle removed "+ VehicleRemoved.getEnterTime() + " " + VehicleRemoved.getDestination()+ " from road " + roadNumber);
        return VehicleRemoved;
    }

    public synchronized Vehicle getFirstVehicle()
    {
        if(currentRoadSize == 0){
            //System.out.println("Road is empty , can't remove Vehicles");
            return null;
        }

        return vehicles[tail];
    }

    public boolean roadIsEmpty(){
        int count = 0;
        for (Vehicle vehicle : this.vehicles) {
            if (vehicle != null) {
                count++;
            }
        }
        //System.out.println(count + " " + roadNumber);
        return count != 0;
    }

    //test
    public void DisplayVehiclesOnRoad()
    {
        for(Vehicle vehicle : vehicles){
            if (vehicle != null){
                System.out.println("Road " + roadNumber + "Destination " + vehicle.getDestination() + " " + vehicle.getEnterTime());
            }
        }
    }
}
