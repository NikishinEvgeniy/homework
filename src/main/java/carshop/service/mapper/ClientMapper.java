package carshop.service.mapper;

import carshop.service.entity.Client;
import carshop.service.dto.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Интерфейс, нужный для маппинга
 */
@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDto clientToClientDto(Client client);
    List<ClientDto> listOfClientsToClientsDto(List<Client> client);
}
