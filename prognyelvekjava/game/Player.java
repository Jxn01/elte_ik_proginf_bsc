package game;

public class Player{
    private String name;
    private String ip;
    private int hp;
    private game.utils.Vehicle vehicle = null;

    public Player() {
    }

    public Player(String name, String ip, int hp, game.utils.Vehicle vehicle) {
        this.name = name;
        this.ip = ip;
        this.hp = hp;
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        String isThereVehicle = "";
        if(vehicle == null){
            isThereVehicle = "The player has no vehicle!";
        }else{
            isThereVehicle = "The player has a vehicle, it's license plate is: "+vehicle.getLicense();
        }

        return "The player's:\nName: "+name+"\nIP-address: "+ip+"\nHP: "+hp+"%\nVehicle: "+isThereVehicle;
    }
}