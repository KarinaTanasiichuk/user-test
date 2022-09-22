package springboot.usertest.dto.mapper;

import springboot.usertest.model.User;

public interface PatchRequestDtoMapper<D, T> {
    T mapToUpdateModel(D dto, User user);
}
