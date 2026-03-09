package vehicle;

//Car class represents a single car in the system
public class Car {

	 // Private variables (Encapsulation)
    private String carId;
    private String brand;
    private String model;
    private double basePrice;
    private boolean isAvailable;

    // Constructor to create a new Car object
    public Car(String carId, String brand, String model, double basePrice) {

    	// Validation to avoid null values
        if (carId == null || brand == null || model == null) {
            throw new IllegalArgumentException("Car ID, brand, and model cannot be null.");
        }

     // validation Price must be positive
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Base price must be positive.");
        }

        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePrice = basePrice;
        this.isAvailable = true;
    }

     // Constructor with availability option
    public Car(String carId, String brand, String model, double basePrice, boolean isAvailable) {

        if (carId == null || brand == null || model == null) {
            throw new IllegalArgumentException("Car ID, brand, and model cannot be null.");
        }

        if (basePrice <= 0) {
            throw new IllegalArgumentException("Base price must be positive.");
        }

        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePrice = basePrice;
        this.isAvailable = isAvailable;
    }

//    getter methods to access the private variables
    public String getCarId()
    {
        return carId;
    }
    public String getBrand()
    {
        return brand;
    }
    public String getModel()
    {
        return model;
    }
    public double getBasePrice()
    {
        return basePrice;
    }

}
