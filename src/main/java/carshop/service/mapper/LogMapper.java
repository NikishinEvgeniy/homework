package carshop.service.mapper;

import carshop.service.dto.LogDto;
import carshop.service.entity.Log;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Интерфейс, нужный для маппинга
 */
@Mapper(componentModel = "spring")
public interface LogMapper {

    LogDto logToLogDto(Log log);
    List<LogDto> listLogToListLogDto(List<Log> logs);
}
