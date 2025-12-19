package uz.uzum.billsplitter.component.adapter;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import uz.uzum.billsplitter.dto.gsp.CreateUserResponseDto;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class GspAdapter {

    RestClient restClient;

    public CreateUserResponseDto  getUserInfo(Long id) {
        return restClient
            .get()
            .uri("/by-id/{id}", id)
            .retrieve()
            .body(CreateUserResponseDto.class);
    }
}