package service;

import dto.UsrDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Map;

@Service
public interface UsrService {
    public UsrDto getMemberByLoginId(String userId);

    public Map<String, String> validateHandling(Errors errors);
}
