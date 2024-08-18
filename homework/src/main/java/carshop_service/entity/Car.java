package carshop_service.entity;

<<<<<<< Updated upstream
import java.util.Objects;

=======
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


/**
 * Сущность car
 */
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> Stashed changes
public class Car {
    private static int globalId;
    private int id;
    private String brand;
    private String model;
    private double price;
    private int yearOfRelease;
    private String condition;

<<<<<<< Updated upstream
    public Car(String brand, String model, double price, int yearOfRelease, String condition) {
=======
    @JsonCreator
    public Car(
            @JsonProperty(value = "brand", required = true) String brand,
            @JsonProperty(value = "model", required = true) String model,
            @JsonProperty(value = "price", required = true) double price,
            @JsonProperty(value = "yearOfRelease", required = true) int yearOfRelease,
            @JsonProperty(value = "condition", required = true) String condition) {
>>>>>>> Stashed changes
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.yearOfRelease = yearOfRelease;
        this.condition = condition;
<<<<<<< Updated upstream
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

=======
    }

    public static CarBuilder builder() {
        return new CarBuilder();
    }
>>>>>>> Stashed changes
    @Override
    public String toString() {
        return "----------------------------\n" +
                " Айди = " + id + "\n" +
                " Марка = " + brand + "\n" +
                " Модель = " + model + "\n" +
                " Цена = " + price + "\n" +
                " Год выпуска = " + yearOfRelease + "\n" +
                " Состояние = " + condition + "\n" +
                "----------------------------\n";
    }

    public static class CarBuilder {
        private int id;
        private String brand;
        private String model;
        private double price;
        private int yearOfRelease;
        private String condition;

        CarBuilder() {
        }

        public CarBuilder id(int id) {
            this.id = id;
            return this;
        }

        public CarBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public CarBuilder model(String model) {
            this.model = model;
            return this;
        }

        public CarBuilder price(double price) {
            this.price = price;
            return this;
        }

        public CarBuilder yearOfRelease(int yearOfRelease) {
            this.yearOfRelease = yearOfRelease;
            return this;
        }

        public CarBuilder condition(String condition) {
            this.condition = condition;
            return this;
        }

        public Car build() {
            return new Car(this.id, this.brand, this.model, this.price, this.yearOfRelease, this.condition);
        }

        public String toString() {
            return "Car.CarBuilder(id=" + this.id + ", brand=" + this.brand + ", model=" + this.model + ", price=" + this.price + ", yearOfRelease=" + this.yearOfRelease + ", condition=" + this.condition + ")";
        }
    }
}
