package springboot.usertest.dto.mapper;

import org.springframework.stereotype.Component;
import springboot.usertest.dto.request.UserRequestDto;
import springboot.usertest.model.User;

@Component
public class UserRequestPatchDtoMapper implements PatchRequestDtoMapper<UserRequestDto, User> {
    @Override
    public User mapToUpdateModel(UserRequestDto dto, User user) {
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getBirthDate() != null) {
            user.setBirthDate(dto.getBirthDate());
        }
        if (dto.getAddress() != null) {
            user.setAddress(dto.getAddress());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
        return user;
    }
}
