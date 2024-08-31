package carshop.service.mapper;

import carshop.service.entity.Client;
import carshop.service.dto.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Интерфейс, нужный для маппинга
 */
@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto clientToClientDto(Client client);
    List<ClientDto> listOfClientsToClientsDto(List<Client> client);
}
