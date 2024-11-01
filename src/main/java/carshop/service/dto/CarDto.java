package carshop.service.dto;

import lombok.*;

/**
 * Data Transfer Object, а именно Car. Нужен для обмена информацией со view
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

    private int id;
    private String brand;
    private String model;
    private double price;
    private int yearOfRelease;
    private String condition;

}
