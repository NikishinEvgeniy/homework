package carshop_service.entity;

public class StandartCarBuilder implements CarBuilder {

    private int id;
    private String brand;
    private String model;
    private double price;
    private int yearOfRelease;
    private String condition;

    @Override
    public CarBuilder id(int id) {
        this.id = id;
        return this;
    }

    @Override
    public CarBuilder brand(String brand) {
        this.brand = brand;
        return this;
    }

    @Override
    public CarBuilder model(String model) {
        this.model = model;
        return this;
    }

    @Override
    public CarBuilder price(double price) {
        this.price = price;
        return this;
    }

    @Override
    public CarBuilder yearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
        return this;
    }

    @Override
    public CarBuilder condition(String condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public Car build() {
        return new Car(this.id,this.brand,this.model,this.price,this.yearOfRelease,this.condition);
    }
}
