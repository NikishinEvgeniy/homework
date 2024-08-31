package carshop.service.controller;

import carshop.service.configuration.ApplicationWebTestConfiguration;
import carshop.service.dto.ClientDto;
import carshop.service.entity.Client;
import carshop.service.handler.JsonHandler;
import carshop.service.service.ClientService;
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

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClientController.class)
@Import(ApplicationWebTestConfiguration.class)
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JsonHandler jsonHandler;
    @MockBean
    private ClientService clientService;
    @Autowired
    private ObjectMapper objectMapper;
    private final String contentType = "application/json";

    @Test
    @DisplayName("GET(/api/clients) -> получение клиентов (JSON) из базы данных")
    public void getAllClientsTest() throws Exception {
        List<ClientDto> clients = List.of(ClientDto.builder().name("TEST").build());
        Mockito.when(clientService.getAllClients()).thenReturn(clients);
        this.mockMvc.perform(get("/api/clients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("GET(/api/clients/{id}) -> получение клиента (JSON) из базы данных")
    public void getClientTest() throws Exception {
        int id = 10;
        ClientDto clientTest = ClientDto.builder().name("TEST").id(id).build();
        Mockito.when(clientService.getClient(id)).thenReturn(clientTest);
        this.mockMvc.perform(get("/api/clients/{id}",id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("POST(/api/clients) -> добавление клиента (JSON) в базу данных")
    public void addClientTest() throws Exception {
        Client client = Client.builder()
                .name("NAME")
                .build();
        doNothing().when(clientService).addClient(client);
        this.mockMvc.perform(
                        post("/api/clients")
                                .content(objectMapper.writeValueAsString(client))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @DisplayName("PUT(/api/clients) -> обновление клиента (JSON) в базе данных")
    public void updateClientTest() throws Exception {
        Client client = Client.builder()
                .name("name")
                .build();
        doNothing().when(clientService).updateClient(client);
        this.mockMvc.perform(
                        put("/api/clients")
                                .content(objectMapper.writeValueAsString(client))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }
}
