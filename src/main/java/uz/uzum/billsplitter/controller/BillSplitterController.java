package uz.uzum.billsplitter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzum.billsplitter.dto.request.BillSplitterRequest;
import uz.uzum.billsplitter.dto.response.BillSplitterResponse;
import uz.uzum.billsplitter.service.BillSplitterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bill-splitter/v1/split")
public class BillSplitterController {
    private final BillSplitterService billSplitterService;

    @PostMapping
    public ResponseEntity<BillSplitterResponse> splitBill(@Valid @RequestBody BillSplitterRequest request) {
        BillSplitterResponse response = billSplitterService.splitBill(request);

        return ResponseEntity.ok(response);
    }
}