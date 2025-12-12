package uz.uzum.billsplitter.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class BillSplitterRequest {

    @NotEmpty(message = "Guest list cannot be empty")
    @Valid
    private List<GuestRequest> guests;

    @NotNull(message = "Shared dishes list cannot be null")
    @Valid
    private List<DishRequest> sharedDishes;

    @NotNull(message = "Commission rate is required")
    @Min(value = 0, message = "Commission rate cannot be negative")
    @Max(value = 100, message = "Commission rate cannot exceed 100")
    private Double commissionRate;
}