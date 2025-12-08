package uz.uzum.billsplitter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import uz.uzum.billsplitter.service.BillSplitterService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BillSplitterController.class)
class ExceptionHandlerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BillSplitterService billSplitterService;

    @Test
    void shouldReturn400ForZeroGuests() throws Exception {
        when(billSplitterService.splitBill(any()))
            .thenThrow(new uz.uzum.billsplitter.exception.ZeroGuestsException());

        mockMvc.perform(post("/api/v1/bill-splitter")
                .contentType("application/json")
                .content("{}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("At least one guest must be provided."));
    }
}