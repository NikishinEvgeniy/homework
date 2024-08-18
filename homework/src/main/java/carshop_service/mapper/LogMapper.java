package carshop_service.mapper;

import carshop_service.dto.LogDto;
import carshop_service.entity.Log;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LogMapper {

    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);

    LogDto logToLogDto(Log log);
    List<LogDto> listLogToListLogDto(List<Log> logs);
}
