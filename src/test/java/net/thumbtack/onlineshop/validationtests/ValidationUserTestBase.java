package net.thumbtack.onlineshop.validationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.request.*;
import net.thumbtack.onlineshop.dto.response.FailedDtoResponse;
import net.thumbtack.onlineshop.dto.response.FailedItemDtoResponse;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ValidationUserTestBase {

    protected static Properties properties;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeClass
    public static void setup() throws IOException {
        AppProperties.initSettings();
        properties = new Properties();
        properties.load(ValidationUserTestBase.class.getClassLoader().getResourceAsStream("ValidationMessages.properties"));
    }

    protected void createAndRegisterInvalidAdmin(String firstName, String lastName, String patronymic,
                                                 String position, String login, String password, AppErrorCode... errorCodes) throws Exception {
        AdminDtoRequest adminDtoRequest = new AdminDtoRequest(firstName, lastName, patronymic, login, password, position);
        postInvalidDtoRequest("/api/admins", adminDtoRequest, errorCodes);
    }

    protected void updateInvalidAdmin(String firstName, String lastName, String patronymic,
                                      String position, String oldPassword, String newPassword, AppErrorCode... errorCodes) throws Exception {
        UpdateAdminDtoRequest updateAdminDtoRequest = new UpdateAdminDtoRequest(firstName, lastName, patronymic, oldPassword, newPassword, position);
        putInvalidDtoRequest("/api/admins", updateAdminDtoRequest, errorCodes);
    }

    protected void createAndRegisterInvalidClient(String firstName, String lastName, String patronymic, String email, String address, String phone,
                                                  String login, String password, AppErrorCode... errorCodes) throws Exception {
        ClientDtoRequest clientDtoRequest = new ClientDtoRequest(firstName, lastName, patronymic, login, password, email, address, phone);
        postInvalidDtoRequest("/api/clients", clientDtoRequest, errorCodes);
    }

    protected void updateInvalidClient(String firstName, String lastName, String patronymic, String email, String address, String phone,
                                       String oldPassword, String newPassword, AppErrorCode... errorCodes) throws Exception {
        UpdateClientDtoRequest updateClientDtoRequest = new UpdateClientDtoRequest(firstName, lastName, patronymic, oldPassword, newPassword, email, address, phone);
        putInvalidDtoRequest("/api/clients", updateClientDtoRequest, errorCodes);
    }

    private MvcResult postInvalidDtoRequest(String url, UserDtoRequest request, AppErrorCode... errorCodes) throws Exception {
        MvcResult result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();
        FailedDtoResponse failedDtoResponse = objectMapper.readValue(result.getResponse().getContentAsString(), FailedDtoResponse.class);
        checkFailedDtoResponse(failedDtoResponse, errorCodes);
        return result;
    }

    private MvcResult putInvalidDtoRequest(String url, UpdateUserDtoRequest request, AppErrorCode... errorCodes) throws Exception {
        MvcResult result = mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();
        FailedDtoResponse failedDtoResponse = objectMapper.readValue(result.getResponse().getContentAsString(), FailedDtoResponse.class);
        checkFailedDtoResponse(failedDtoResponse, errorCodes);
        return result;
    }


    private void checkFailedDtoResponse(FailedDtoResponse failedDtoResponse, AppErrorCode... errorCodes) {
        List<AppErrorCode> errorCodeList = new ArrayList<>();
        assertEquals(errorCodes.length, failedDtoResponse.getErrors().size());

        for (FailedItemDtoResponse failedItem : failedDtoResponse.getErrors()) {
            errorCodeList.add(failedItem.getErrorCode());
        }
        for (AppErrorCode errorCode : errorCodes) {
            assertTrue(errorCodeList.contains(errorCode));
        }
    }

}
