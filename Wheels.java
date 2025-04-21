public class Wheels extends Car{
    String wheelsId;
    double pressure;

    public Wheels(int carNo, int maxSpeed, String color, String wheelsId, double pressure) {
        super(carNo, maxSpeed, color);
        this.wheelsId = wheelsId;
        this.pressure = pressure;
    }
}
