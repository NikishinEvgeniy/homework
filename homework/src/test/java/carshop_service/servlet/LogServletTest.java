package carshop_service.servlet;

import carshop_service.dto.LogDto;
import carshop_service.entity.Log;
import carshop_service.handler.JasonHandler;
import carshop_service.service.LogService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogServletTest {
    @Mock
    private LogService logService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JasonHandler jasonHandler;

    @InjectMocks
    private LogServlet logServlet;

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
        LogDto logDtoOne = LogDto.builder()
                .clientId(1)
                .build();
        LogDto logDtoTwo = LogDto.builder()
                .clientId(2)
                .build();
        List<LogDto> allLogs = List.of(logDtoOne,logDtoTwo);
        byte[] successJson = "{ logs :[{ clientId : 1 },{ clientId : 2 }]}".getBytes();
        when(logService.getAllLogs()).thenReturn(allLogs);
        when(objectMapper.writeValueAsBytes(allLogs)).thenReturn(successJson);
        logServlet.doGet(request, response);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, successJson);
    }

    @Test
    public void testDoPostSuccess() throws Exception {
        Log logOne = Log.builder()
                .clientId(1)
                .build();
        String jsonBodyForUser = "{ clientId : 1 }";
        byte[] successJson = "{ status : success }".getBytes();
        when(jasonHandler.getJsonBody(request)).thenReturn(jsonBodyForUser);
        when(objectMapper.readValue(jsonBodyForUser, Log.class)).thenReturn(logOne);
        when(objectMapper.writeValueAsBytes(ArgumentMatchers.any())).thenReturn(successJson);
        logServlet.doPost(request, response);
        verify(logService).addLog(logOne);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(jasonHandler).sendTheJson(response, successJson);
    }

}
