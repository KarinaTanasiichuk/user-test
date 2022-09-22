package springboot.usertest.dto.mapper;

import org.springframework.stereotype.Component;
import springboot.usertest.dto.request.UserRequestDto;
import springboot.usertest.model.User;

@Component
public class UserRequestDtoMapper implements RequestDtoMapper<UserRequestDto, User> {
    @Override
    public User mapToModel(UserRequestDto requestDto) {
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setBirthDate(requestDto.getBirthDate());
        user.setAddress(requestDto.getAddress());
        user.setPhone(requestDto.getPhone());
        return user;
    }
}
