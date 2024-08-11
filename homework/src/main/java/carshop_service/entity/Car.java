package carshop_service.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Car {

    private int id;
    private String brand;
    private String model;
    private double price;
    private int yearOfRelease;
    private String condition;

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
