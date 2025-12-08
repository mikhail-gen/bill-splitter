package uz.uzum.billsplitter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DishRequest {
    private String name;
    private Double cost;
}