package uz.uzum.billsplitter.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@AllArgsConstructor
@SuperBuilder
@Jacksonized
public class GuestRequest {

    @NotBlank(message = "Guest name cannot be blank")
    private String name;

    @NotNull(message = "Dish list cannot be null")
    @Valid
    private List<DishRequest> dishes;
}