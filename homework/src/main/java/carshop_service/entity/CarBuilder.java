package carshop_service.entity;

public interface CarBuilder {
    CarBuilder id(int id);
    CarBuilder brand(String brand);
    CarBuilder model(String model);
    CarBuilder price(double price);
    CarBuilder yearOfRelease(int yearOfRelease);
    CarBuilder condition(String condition);
    Car build();
}
