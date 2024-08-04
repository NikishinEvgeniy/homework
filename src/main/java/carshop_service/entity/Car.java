package carshop_service.entity;

import java.util.Objects;

public class Car {
    private static int globalId;
    private int id;
    private String brand;
    private String model;
    private double price;
    private int yearOfRelease;
    private String condition;

    public Car(String brand, String model, double price, int yearOfRelease, String condition) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.yearOfRelease = yearOfRelease;
        this.condition = condition;
        id = globalId++;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && Double.compare(price, car.price) == 0 && yearOfRelease == car.yearOfRelease && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(condition, car.condition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, price, yearOfRelease, condition);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "----------------------------\n"+
                " Айди = " + id + "\n" +
                " Марка = " + brand + "\n" +
                " Модель = " + model + "\n" +
                " Цена = " + price + "\n" +
                " Год выпуска = " + yearOfRelease + "\n" +
                " Состояние = " + condition + "\n"+
               "----------------------------\n";
    }
}
