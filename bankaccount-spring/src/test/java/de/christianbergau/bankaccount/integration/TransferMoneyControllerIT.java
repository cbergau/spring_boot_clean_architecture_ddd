package de.christianbergau.bankaccount.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.christianbergau.bankaccount.api.v1.request.TransferModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
public class TransferMoneyControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testTransferMoneyWithBadRequest() throws Exception {
        mockMvc.perform(post("/transfer")
                .content("{\"fromIban\":\"\", \"toIban\": \"\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testTransferMoneyWithValidRequest() throws Exception {
        TransferModel transferModel = TransferModel.builder()
                .amount(100.00)
                .fromIban("DE89 3704 0044 0532 0130 00")
                .toIban("DE89 3704 0044 0532 0130 11")
                .build();

        MvcResult r = mockMvc.perform(post("/transfer")
                .content(objectMapper.writeValueAsString(transferModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(r.getResponse().getContentAsString());
    }
}
