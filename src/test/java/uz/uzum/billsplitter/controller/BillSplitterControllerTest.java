package uz.uzum.billsplitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uz.uzum.billsplitter.dto.request.BillSplitterRequest;
import uz.uzum.billsplitter.dto.response.BillSplitterResponse;
import uz.uzum.billsplitter.dto.response.GuestBill;
import uz.uzum.billsplitter.service.BillSplitterService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BillSplitterController.class)
class BillSplitterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BillSplitterService billSplitterService;

    @Test
    void postBillSplitter_shouldReturnOk() throws Exception {
        GuestBill bill = new GuestBill("Alice", 100.0, 50.0, 15.0, 165.0);
        BillSplitterResponse response = new BillSplitterResponse(List.of(bill));

        when(billSplitterService.splitBill(any())).thenReturn(response);

        BillSplitterRequest request = new BillSplitterRequest();
        request.setCommissionRate(10.0);

        mockMvc.perform(post("/api/v1/bill-splitter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.guests[0].name").value("Alice"))
            .andExpect(jsonPath("$.guests[0].finalTotal").value(165.0));
    }
}