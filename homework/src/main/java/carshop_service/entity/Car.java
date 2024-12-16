package carshop_service.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


/**
 * Сущность car
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private int id;
    private String brand;
    private String model;
    private double price;
    private int yearOfRelease;
    private String condition;

    @JsonCreator
    public Car(
            @JsonProperty(value = "brand", required = true) String brand,
            @JsonProperty(value = "model", required = true) String model,
            @JsonProperty(value = "price", required = true) double price,
            @JsonProperty(value = "yearOfRelease", required = true) int yearOfRelease,
            @JsonProperty(value = "condition", required = true) String condition) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.yearOfRelease = yearOfRelease;
        this.condition = condition;
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
