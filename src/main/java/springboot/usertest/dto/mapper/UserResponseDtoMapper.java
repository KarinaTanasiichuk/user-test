package springboot.usertest.dto.mapper;

import org.springframework.stereotype.Component;
import springboot.usertest.dto.response.UserResponseDto;
import springboot.usertest.model.User;

@Component
public class UserResponseDtoMapper implements ResponseDtoMapper<UserResponseDto, User> {
    @Override
    public UserResponseDto mapToDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setEmail(user.getEmail());
        responseDto.setFirstName(user.getFirstName());
        responseDto.setLastName(user.getLastName());
        responseDto.setBirthDate(user.getBirthDate());
        responseDto.setAddress(user.getAddress());
        responseDto.setPhone(user.getPhone());
        return responseDto;
    }
}
