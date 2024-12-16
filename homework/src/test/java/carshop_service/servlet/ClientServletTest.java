package carshop_service.servlet;

import carshop_service.dto.ClientDto;
import carshop_service.entity.Client;
import carshop_service.exception.DataBaseEmptyException;
import carshop_service.exception.DuplicateEntityException;
import carshop_service.exception.NoSuchEntityException;
import carshop_service.handler.JasonHandler;
import carshop_service.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

public class ClientServletTest {
    @Mock
    private ClientService clientService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JasonHandler jasonHandler;

    @InjectMocks
    private ClientServlet clientServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private ByteArrayOutputStream responseOutputStream;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        responseOutputStream = new ByteArrayOutputStream();
        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {}

            @Override
            public void write(int b) throws IOException {
                responseOutputStream.write(b);
            }
        });
    }

    @Test
    public void testDoGetSuccess() throws Exception {
        ClientDto clientOne = ClientDto.builder()
                .name("name1")
                .build();
        ClientDto clientTwo = ClientDto.builder()
                .name("name2")
                .build();
        List<ClientDto> allClients = List.of(clientOne,clientTwo);
        byte[] successJson = "{ allClients :[{ name : name1 },{ name : name2 }]}".getBytes();
        when(clientService.getAllClients()).thenReturn(allClients);
        when(objectMapper.writeValueAsBytes(allClients)).thenReturn(successJson);
        clientServlet.doGet(request, response);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, successJson);
    }

    @Test
    public void testDoGetDataBaseEmptyException() throws Exception {
        when(clientService.getAllClients()).thenThrow(new DataBaseEmptyException("Clients"));
        byte[] jsonBody = "errorJson".getBytes();
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(jsonBody);
        clientServlet.doGet(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        verify(jasonHandler).sendTheJson(response, jsonBody);
    }

    @Test
    public void testDoPutSuccess() throws Exception {
        Client clientOne = Client.builder()
                .name("name1")
                .build();
        String jsonBodyForUser = "{ name : name1 }";
        byte[] successJson = "{ status : success }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Client.class)).thenReturn(clientOne);
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(successJson);
        clientServlet.doPut(request, response);
        verify(clientService).updateClient(clientOne);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, successJson);
    }

    @Test
    public void testDoPutNoSuchEntityException() throws Exception {
        Client clientOne = Client.builder()
                .name("name1")
                .build();
        String jsonBodyForUser = "{ name : name1 }";
        byte[] errorJson = "{ status : error , message : Client not found }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Client.class)).thenReturn(clientOne);
        doThrow(new NoSuchEntityException("Client not found")).when(clientService).updateClient(clientOne);
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(errorJson);
        clientServlet.doPut(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        verify(jasonHandler).sendTheJson(response, errorJson);
    }

    @Test
    public void testDoPostSuccess() throws Exception {
        Client clientOne = Client.builder()
                .name("name1")
                .build();
        String jsonBodyForUser = "{ name : name1 }";
        byte[] successJson = "{ status : success }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Client.class)).thenReturn(clientOne);
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(successJson);
        clientServlet.doPost(request, response);
        verify(clientService).addClient(clientOne);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, successJson);
    }

    @Test
    public void testDoPostDuplicateEntityException() throws Exception {
        Client clientOne = Client.builder()
                .name("name1")
                .build();
        String jsonBodyForUser = "{ name : name1 }";
        byte[] errorJson = "{ status : error , message : Client already exists }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Client.class)).thenReturn(clientOne);
        doThrow(new DuplicateEntityException("Client already exists")).when(clientService).addClient(clientOne);
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(errorJson);
        clientServlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        verify(jasonHandler).sendTheJson(response, errorJson);
    }
}
