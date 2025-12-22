package uz.uzum.billsplitter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzum.billsplitter.dto.gsp.CreateUserResponseDto;
import uz.uzum.billsplitter.dto.request.BillSplitterRequest;
import uz.uzum.billsplitter.dto.response.BillSplitterResponse;
import uz.uzum.billsplitter.service.BillSplitterService;
import uz.uzum.billsplitter.service.RequestFromGspService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bill-splitter/v1/split")
public class BillSplitterController {
    private final BillSplitterService billSplitterService;
    private final RequestFromGspService requestFromGspService;

    @PostMapping
    public ResponseEntity<BillSplitterResponse> splitBill(@Valid @RequestBody BillSplitterRequest request) {
        BillSplitterResponse response = billSplitterService.splitBill(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/gcp/{id}")
    public ResponseEntity<CreateUserResponseDto> testGcp(@PathVariable Long id) {
        return ResponseEntity.ok(requestFromGspService.getPersonDataById(id));
    }
}