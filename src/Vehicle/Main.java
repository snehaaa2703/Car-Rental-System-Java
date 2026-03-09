
package vehicle;

//Main class starts the application
public class Main {

    public static void main(String[] args) {
    	
    	 // Create object of CarRental class
        CarRental system = new CarRental();
        
        // Example cars can be inserted
        // system.addCar(new Car("C001", "Toyota", "Camry", 60));

     // Start the login system
        system.login();
    }
}
