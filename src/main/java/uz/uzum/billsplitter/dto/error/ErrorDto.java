package uz.uzum.billsplitter.dto.error;

import lombok.Builder;
import uz.uzum.billsplitter.constant.enums.ErrorType;

import java.util.List;

@Builder
public record ErrorDto(
    int code,
    String message,
    ErrorType type,
    List<String> validationErrors) {}