package com.interview.lb0724;

import com.google.gson.Gson;
import com.interview.lb0724.entities.RentalAgreement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class ToolControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    @Autowired
    private View error;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void getToolInvalidToolCode() throws Exception {
        String error = mockMvc.perform(get("/tool/FAKE")
                .param("days", "1")
                .param("date", "05/05/20")
        ).andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertEquals("ToolCode isn't valid or does not exist.", error);
    }

    @Test
    void getToolInvalidDays() throws Exception {
        String error = mockMvc.perform(get("/tool/FAKE")
                        .param("days", "-1")
                        .param("date", "05/05/20")
                ).andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertEquals("Days to rent must be 1 or greater.", error);
    }

    @Test
    void getToolInvalidDiscountLow() throws Exception {
        String error = mockMvc.perform(get("/tool/FAKE")
                        .param("days", "1")
                        .param("date", "05/05/20")
                        .param("discount", "-10")
                ).andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertEquals("Discount percentage must be between 0 and 100.", error);
    }

    @Test
    void getToolTestCase1() throws Exception {
        String error = mockMvc.perform(get("/tool/JAKR")
                        .param("days", "5")
                        .param("date", "09/03/15")
                        .param("discount", "101")
                ).andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertEquals("Discount percentage must be between 0 and 100.", error);
    }

    @Test
    void getToolInvalidDate() throws Exception {
        String error = mockMvc.perform(get("/tool/JAKR")
                        .param("days", "1")
                        .param("date", "05/056/20")
                        .param("discount", "80")
                ).andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertEquals("Text '05/056/20' could not be parsed at index 5", error);
    }

    @Test
    void getToolTestCase2() throws Exception {
        String response = mockMvc.perform(get("/tool/LADW")
                        .param("days", "3")
                        .param("date", "07/02/20")
                        .param("discount", "10")
                ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        RentalAgreement responseDto
                = new Gson().fromJson(response, RentalAgreement.class);
        assertEquals(2, responseDto.getDaysToCharge());
        assertEquals("07/05/20", responseDto.getReturnDate());
    }

    @Test
    void getToolTestCase3() throws Exception {
        String response = mockMvc.perform(get("/tool/CHNS")
                        .param("days", "5")
                        .param("date", "07/02/15")
                        .param("discount", "25")
                ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        RentalAgreement responseDto
                = new Gson().fromJson(response, RentalAgreement.class);
        assertEquals(3, responseDto.getDaysToCharge());
        assertEquals("07/07/15", responseDto.getReturnDate());
    }

    @Test
    void getToolTestCase4() throws Exception {
        String response = mockMvc.perform(get("/tool/JAKD")
                        .param("days", "6")
                        .param("date", "09/03/15")
                        .param("discount", "0")
                ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        RentalAgreement responseDto
                = new Gson().fromJson(response, RentalAgreement.class);
        assertEquals(2, responseDto.getDaysToCharge());
        assertEquals("09/09/15", responseDto.getReturnDate());
    }


    @Test
    void getToolTestCase5() throws Exception {
        String response = mockMvc.perform(get("/tool/JAKR")
                        .param("days", "9")
                        .param("date", "07/02/15")
                ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        RentalAgreement responseDto
                = new Gson().fromJson(response, RentalAgreement.class);
        assertEquals(6, responseDto.getDaysToCharge());
        assertEquals("07/11/15", responseDto.getReturnDate());
    }

    @Test
    void getToolTestCase6() throws Exception {
        String response = mockMvc.perform(get("/tool/JAKR")
                        .param("days", "4")
                        .param("date", "07/02/20")
                        .param("discount", "50")
                ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        RentalAgreement responseDto
                = new Gson().fromJson(response, RentalAgreement.class);
        assertEquals(1, responseDto.getDaysToCharge());
        assertEquals("07/06/20", responseDto.getReturnDate());
    }


}
