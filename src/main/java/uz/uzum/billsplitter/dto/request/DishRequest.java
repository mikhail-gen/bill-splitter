package uz.uzum.billsplitter.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DishRequest {

    @NotBlank(message = "Dish name cannot be blank")
    private String name;

    @NotNull(message = "Dish cost cannot be null")
    @PositiveOrZero(message = "Dish cost cannot be negative")
    private Double cost;
}