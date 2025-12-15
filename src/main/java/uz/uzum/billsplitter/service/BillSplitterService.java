package uz.uzum.billsplitter.service;

import uz.uzum.billsplitter.dto.request.BillSplitterRequest;
import uz.uzum.billsplitter.dto.response.BillSplitterResponse;

public interface BillSplitterService {

    BillSplitterResponse splitBill(BillSplitterRequest request);
}