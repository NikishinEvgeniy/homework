package carshop.service.dto;

import lombok.*;
/**
 * Data Transfer Object, а именно Client. Нужен для обмена информацией со view
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private int id;
    private int countOfBuy;
    private String name;
    private String surname;

}
