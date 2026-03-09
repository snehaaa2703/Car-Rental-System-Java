package vehicle;

//Rental class represents a car rental transaction
public class Rental {

    private int rentalId;
    private String carId;
    private String custId;
    private int days;

    // Constructor
    public Rental(int rentalId, String carId, String custId, int days) {

    	 // Validation
        if (carId == null || custId == null) {
            throw new IllegalArgumentException("Car ID and Customer ID cannot be null.");
        }

        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be greater than 0.");
        }

        this.rentalId = rentalId;
        this.carId = carId;
        this.custId = custId;
        this.days = days;
    }

 // Getter methods
    public int getrentalId()
    { 
    	return rentalId; 
    	}
    public String getcarId() 
    {
    	return carId;
    	}
    public String getcustId()
    { 
    	return custId; 
    	}
    public int getdays() 
    { 
    	return days; 
    	}
}
