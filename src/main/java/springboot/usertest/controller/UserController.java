package springboot.usertest.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboot.usertest.dto.mapper.UserRequestDtoMapper;
import springboot.usertest.dto.mapper.UserRequestPatchDtoMapper;
import springboot.usertest.dto.mapper.UserResponseDtoMapper;
import springboot.usertest.dto.request.UserRequestDto;
import springboot.usertest.dto.response.UserResponseDto;
import springboot.usertest.model.User;
import springboot.usertest.service.UserService;
import springboot.usertest.util.DateTimePatternUtil;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserRequestDtoMapper requestDtoMapper;
    private final UserResponseDtoMapper responseDtoMapper;
    private final UserRequestPatchDtoMapper requestPatchDtoMapper;

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll().stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return responseDtoMapper.mapToDto(user);
    }

    @PostMapping
    public UserResponseDto create(@RequestBody @Valid UserRequestDto requestDto) {
        User user = userService.create(requestDtoMapper.mapToModel(requestDto));
        return responseDtoMapper.mapToDto(user);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id,
                                  @RequestBody @Valid UserRequestDto requestDto) {
        User user = requestDtoMapper.mapToModel(requestDto);
        user.setId(userService.getUserById(id).getId());
        return responseDtoMapper.mapToDto(userService.update(user));
    }

    @PatchMapping("/{id}")
    public UserResponseDto patch(@PathVariable Long id,
                                 @RequestBody @Valid UserRequestDto requestDto) {
        User user = userService.getUserById(id);
        user = requestPatchDtoMapper.mapToUpdateModel(requestDto, user);
        return responseDtoMapper.mapToDto(userService.update(user));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/by-birthday")
    public List<UserResponseDto> findUsersBetweenBirthDate(
            @RequestParam @DateTimeFormat(pattern = DateTimePatternUtil.DATE_PATTERN)
            LocalDate before,
            @RequestParam
            @DateTimeFormat(pattern = DateTimePatternUtil.DATE_PATTERN) LocalDate after) {
        return userService.findUsersBetweenBirthDate(before, after).stream()
                .map(responseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
