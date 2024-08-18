package carshop_service.mapper;

import carshop_service.dto.ClientDto;
import carshop_service.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDto clientToClientDto(Client client);
    List<ClientDto> listOfClientsToClientsDto(List<Client> client);
}
