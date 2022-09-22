package springboot.usertest.controller;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.List;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import springboot.usertest.dto.request.UserRequestDto;
import springboot.usertest.model.User;
import springboot.usertest.service.UserService;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    private static final int OK = HttpStatus.OK.value();
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;

    @BeforeEach
    void beforeEach() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void create_is_ok() {
        User user = new User(1L, "first.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(1998, 7, 25),
                "Address", "12345678");
        Mockito.when(userService.create(any(User.class))).thenReturn(user);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(toDto(user))
                .when()
                .post("/users")
                .then()
                .statusCode(OK)
                .body("id", Matchers.equalTo(1))
                .body("email", Matchers.equalTo("first.user@gmail.com"))
                .body("firstName", Matchers.equalTo("Name"))
                .body("lastName", Matchers.equalTo("Last name"))
                .body("birthDate", Matchers.equalTo("1998-07-25"))
                .body("address", Matchers.equalTo("Address"))
                .body("phone", Matchers.equalTo("12345678"));
    }

    @Test
    void update_is_ok() {
        User user = new User(1L, "first.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(1998, 7, 25),
                "Address", "12345678");
        User updateUser = new User(1L, "update.user@gmail.com",
                "First name", "Surname",
                LocalDate.of(1989, 8, 8),
                "address", "000000");
        Mockito.when(userService.getUserById(user.getId())).thenReturn(user);
        Mockito.when(userService.update(any(User.class))).thenReturn(updateUser);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(toDto(updateUser))
                .when()
                .put("/users/" + user.getId())
                .then()
                .statusCode(OK)
                .body("id", Matchers.equalTo(1))
                .body("email", Matchers.equalTo("update.user@gmail.com"))
                .body("firstName", Matchers.equalTo("First name"))
                .body("lastName", Matchers.equalTo("Surname"))
                .body("birthDate", Matchers.equalTo("1989-08-08"))
                .body("address", Matchers.equalTo("address"))
                .body("phone", Matchers.equalTo("000000"));
    }

    @Test
    void patch_is_ok() {
        User user = new User(1L, "first.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(1998, 7, 25),
                "Address", "12345678");
        User updateUser = new User(1L, "update.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(1989, 8, 8),
                "Address", "12345678");
        Mockito.when(userService.getUserById(user.getId())).thenReturn(user);
        Mockito.when(userService.update(any(User.class))).thenReturn(updateUser);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(toDto(updateUser))
                .when()
                .patch("/users/" + user.getId())
                .then()
                .statusCode(OK)
                .body("id", Matchers.equalTo(1))
                .body("email", Matchers.equalTo("update.user@gmail.com"))
                .body("birthDate", Matchers.equalTo("1989-08-08"));
    }

    @Test
    void delete_is_ok() {
        User user = new User(1L, "first.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(1998, 7, 25),
                "Address", "12345678");
        Mockito.when(userService.getUserById(user.getId())).thenReturn(user);
        RestAssuredMockMvc.when()
                .delete("/users/" + user.getId())
                .then()
                .statusCode(OK);
    }

    @Test
    void findUsersBetweenBirthDate_is_ok() {
        LocalDate before = LocalDate.of(1998, 1, 1);
        LocalDate after = LocalDate.of(1999, 1, 1);
        List<User> users = List.of(new User(1L, "first.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(1998, 7, 25),
                "Address", "12345678"));
        Mockito.when(userService.findUsersBetweenBirthDate(before, after)).thenReturn(users);
        RestAssuredMockMvc.given()
                .queryParam("before", "01.01.1998")
                .queryParam("after", "01.01.1999")
                .when()
                .get("/users/by-birthday")
                .then()
                .statusCode(OK)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].email", Matchers.equalTo("first.user@gmail.com"))
                .body("[0].firstName", Matchers.equalTo("Name"))
                .body("[0].lastName", Matchers.equalTo("Last name"))
                .body("[0].birthDate", Matchers.equalTo("1998-07-25"))
                .body("[0].address", Matchers.equalTo("Address"))
                .body("[0].phone", Matchers.equalTo("12345678"));
    }

    @Test
    void getAll_is_ok() {
        List<User> users = List.of(new User(1L, "first.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(1998, 7, 25),
                "Address", "12345678"), new User(2L, "second.user@gmail.com",
                "Name", "Last name",
                LocalDate.of(2000, 6, 7),
                "Address", "3456789"));
        Mockito.when(userService.getAll()).thenReturn(users);
        RestAssuredMockMvc.given()
                .when()
                .get("/users")
                .then()
                .statusCode(OK)
                .body("size()", Matchers.equalTo(2))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].email", Matchers.equalTo("first.user@gmail.com"))
                .body("[0].firstName", Matchers.equalTo("Name"))
                .body("[0].lastName", Matchers.equalTo("Last name"))
                .body("[0].birthDate", Matchers.equalTo("1998-07-25"))
                .body("[0].address", Matchers.equalTo("Address"))
                .body("[0].phone", Matchers.equalTo("12345678"))
                .body("[1].id", Matchers.equalTo(2))
                .body("[1].email", Matchers.equalTo("second.user@gmail.com"))
                .body("[1].firstName", Matchers.equalTo("Name"))
                .body("[1].lastName", Matchers.equalTo("Last name"))
                .body("[1].birthDate", Matchers.equalTo("2000-06-07"))
                .body("[1].address", Matchers.equalTo("Address"))
                .body("[1].phone", Matchers.equalTo("3456789"));
    }

    private UserRequestDto toDto(User user) {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail(user.getEmail());
        userRequestDto.setFirstName(user.getFirstName());
        userRequestDto.setLastName(user.getLastName());
        userRequestDto.setBirthDate(user.getBirthDate());
        userRequestDto.setAddress(user.getAddress());
        userRequestDto.setPhone(user.getPhone());
        return userRequestDto;
    }
}