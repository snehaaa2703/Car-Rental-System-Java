package vehicle;

//Customer class stores customer details
public class Customer {

    private String custId;
    private String name;

    // Constructor to create customer
    public Customer(String custId, String name) {

    	 // Validation to prevent null values
        if (custId == null || name == null) {
            throw new IllegalArgumentException("Customer ID and name cannot be null.");
        }

        // Name should not be empty
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }

        this.custId = custId;
        this.name = name;
    }
    
 // Getter methods
    public String getCustId()
    {
        return custId;
    }

    public String getName()
    {
        return name;
    }
}
