package carshop.service.controller;

import carshop.service.configuration.ApplicationWebTestConfiguration;
import carshop.service.dto.LogDto;
import carshop.service.entity.Log;
import carshop.service.handler.JsonHandler;
import carshop.service.service.LogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ya.lab.loggable_starter.constant.LogAction;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogController.class)
@Import(ApplicationWebTestConfiguration.class)
public class LogControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JsonHandler jsonHandler;
    @MockBean
    private LogService logService;
    @Autowired
    private ObjectMapper objectMapper;
    private final String contentType = "application/json";

    @Test
    @DisplayName("GET(/api/logs) -> получение логов (JSON) из базы данных")
    public void getAllLogsTest() throws Exception {
        List<LogDto> logs = List.of(LogDto.builder().action(LogAction.REGISTRATION_ACTION).build());
        Mockito.when(logService.getAllLogs()).thenReturn(logs);
        this.mockMvc.perform(get("/api/logs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("GET(/api/logs/export) -> экспорт логов (JSON) из базы данных в файл")
    public void getLogTest() throws Exception {
        Mockito.doNothing().when(logService).exportLogsInTextFile();
        this.mockMvc.perform(get("/api/logs/export"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("POST(/api/logs) -> добавление лога (JSON) в базу данных")
    public void addLogTest() throws Exception {
        Log log = Log.builder()
                .action(LogAction.REGISTRATION_ACTION)
                .build();
        doNothing().when(logService).addLog(log);
        this.mockMvc.perform(
                        post("/api/logs")
                                .content(objectMapper.writeValueAsString(log))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }
}
