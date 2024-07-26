package com.interview.lb0724;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ToolController.class)
public class ToolControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    ToolController toolController;

    @MockBean
    private RentalService rentalService;

    @Test
    public void getToolWithoutDiscount() throws Exception {
        when(rentalService.rent("CHNS", 1,0, "06/06/20")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/tool/CHNS")
                .param("days", "1")
                .param("date", "06/06/20")
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getToolWithDiscount() throws Exception {
        when(rentalService.rent("CHNS", 1,10, "06/06/20")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tool/CHNS")
                        .param("days", "1")
                        .param("date", "06/06/20")
                        .param("discount", "10")
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getToolWithoutDate() throws Exception {
        when(rentalService.rent("CHNS", 1,10, "06/06/20")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tool/CHNS")
                        .param("days", "1")
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getToolWithoutDays() throws Exception {
        when(rentalService.rent("CHNS", 1,10, "06/06/20")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tool/CHNS")
                        .param("date", "06/06/20")
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
}
