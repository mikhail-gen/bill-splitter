package uz.uzum.billsplitter.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import uz.uzum.billsplitter.component.adapter.GspAdapter;
import uz.uzum.billsplitter.dto.gsp.CreateUserResponseDto;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RequestFromGspServiceImpl implements RequestFromGspService {

    GspAdapter gspAdapter;

    @Override
    public CreateUserResponseDto getPersonDataById(Long id) {
        CreateUserResponseDto user =
            gspAdapter.getUserInfo((long) id);
        return user;
    }
}