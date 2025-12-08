package uz.uzum.billsplitter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestBill {
    private String name;
    private Double individualTotal;
    private Double sharedTotal;
    private Double commission;
    private Double finalTotal;
}