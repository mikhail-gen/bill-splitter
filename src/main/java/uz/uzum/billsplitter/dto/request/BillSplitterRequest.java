package uz.uzum.billsplitter.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BillSplitterRequest {
    private List<GuestRequest> guests;
    private List<DishRequest> sharedDishes;
    private Double commissionRate;
}