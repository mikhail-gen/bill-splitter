package uz.uzum.billsplitter.service;

import uz.uzum.billsplitter.dto.gsp.CreateUserResponseDto;

public interface RequestFromGspService {

    CreateUserResponseDto getPersonDataById(Long id);
}