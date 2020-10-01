public class Program {

    public static void main(String[] args) {

        Car bmw = new Car("BMW", "Schwarz", 2020, 100000, 300);


        Car mercedes = new Car("Mercedes", "Gelb", 2012, 236000, 230);
        mercedes.drive();
        System.out.println("Baujahr vom Auto: " + mercedes.getKilometersDriven());


    }
}
