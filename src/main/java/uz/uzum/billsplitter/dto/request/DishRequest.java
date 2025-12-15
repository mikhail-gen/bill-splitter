package uz.uzum.billsplitter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@SuperBuilder
@Jacksonized
public class DishRequest {

    @NotBlank(message = "Dish name cannot be blank")
    private String name;

    @NotNull(message = "Dish cost cannot be null")
    @PositiveOrZero(message = "Dish cost cannot be negative")
    private Double cost;
}