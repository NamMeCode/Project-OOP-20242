package hello2;

enum Level {
    LOW, MEDIUM, HIGH
}

public class Vehicle {
    protected String brand="Ford";
    int year=5;
    public Vehicle() {
        System.out.println("Parent Vehicle");
    }
    public void honk() {
        System.out.println("Tuut, tuut!");
    }
}

class Car extends Vehicle {
    private String modelName="Mustang";
    public Car() {
        //implicit call of Vehicle's constructor
        System.out.println("Child Car");
    }
    public static void main(String[] args) {
        Car myCar=new Car();
        myCar.honk();
        System.out.println(myCar.brand + ' ' + myCar.modelName);
        Level level=Level.HIGH;
        System.out.println(level);
        switch (level) {
            case HIGH:
                System.out.println("HIGH");
                break;
            case MEDIUM:
                System.out.println("MEDIUM");
                break;
            case LOW:
                System.out.println("LOW");
                break;
        }
        for(Level l: Level.values()) {
            System.out.println(l);
        }
    }
}