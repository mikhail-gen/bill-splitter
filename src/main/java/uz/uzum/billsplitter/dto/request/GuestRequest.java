package uz.uzum.billsplitter.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class GuestRequest {

    @NotBlank(message = "Guest name cannot be blank")
    private String name;

    @NotNull(message = "Dish list cannot be null")
    @Valid
    private List<DishRequest> dishes;
}