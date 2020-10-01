public class Car {

    private String Brand;
    private String color;
    private int constructionYear;
    private int kilometersDriven;
    private int HorsePower;

    public Car(String Brand, String color, int constructionYear, int kilometersDriven, int HorsePower) {
        this.Brand = Brand;
        this.color = color;
        this.constructionYear = constructionYear;
        this.kilometersDriven = kilometersDriven;
        this.HorsePower = HorsePower;
    }

    public void drive() {
        this.kilometersDriven--;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(int constructionYear) {
        this.constructionYear = constructionYear;
    }

    public int getKilometersDriven() {
        return kilometersDriven;
    }

    public void setKilometersDriven(int kilometersDriven) {
        this.kilometersDriven = kilometersDriven;
    }

    public int getHorsePower() {
        return HorsePower;
    }

    public void setHorsePower(int horsePower) {
        HorsePower = horsePower;
    }
}
