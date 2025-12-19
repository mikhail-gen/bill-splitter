package uz.uzum.billsplitter.dto.gsp;

import java.time.LocalDate;

public record CreateUserResponseDto(
    Long id,
    String name,
    String address,
    String phoneNumber,
    String email,
    String photoUrl,
    String pinfl,
    Integer age,
    String gender,
    String documentType,
    LocalDate issueDate,
    LocalDate expiryDate,
    String citizenship
) {}
