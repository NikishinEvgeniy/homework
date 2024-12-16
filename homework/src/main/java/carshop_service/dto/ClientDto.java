package carshop_service.dto;

import lombok.*;

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
