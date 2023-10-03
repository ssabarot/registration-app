package ssabarot.springboot.registrationapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ssabarot.springboot.registrationapp.model.Gender;
import ssabarot.springboot.registrationapp.model.User;
import ssabarot.springboot.registrationapp.repository.UserRepository;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("IT getAllUsers Success")
    public void ti_getAllUsers() throws Exception {
        // given
        User user1 = User.builder().id(13L).name("gdubreuil").birthdate(LocalDate.of(2003, 8, 12)).country("France").phoneNumber("+33658749141").gender(Gender.MALE).build();
        User user2 = User.builder().id(9L).name("tloumy").birthdate(LocalDate.of(1999, 4, 9)).country("France").phoneNumber("+33758449149").gender(Gender.OTHER).build();
        User user3 = User.builder().id(5L).name("rtourot").birthdate(LocalDate.of(1987, 2, 21)).country("France").phoneNumber("0658779171").gender(Gender.FEMALE).build();

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        userRepository.saveAll(userList);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);
        MvcResult mvcResult = result.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        User[] usersResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User[].class);
        List<User> userListResult = Arrays.asList(usersResult);

        assertThat(userListResult).isNotEmpty();
        assertEquals(userListResult, userList);
    }

    @Test
    @DisplayName("IT getUserById Success")
    public void ti_getUserById() throws Exception {
        // given
        User user = User.builder().id(14L).name("rthomas").birthdate(LocalDate.of(2003, 8, 12)).country("France").phoneNumber("+33658749141").gender(Gender.MALE).build();

        userRepository.save(user);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);
        MvcResult mvcResult = result.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        User userResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        assertThat(userResult).isNotNull();
        assertEquals(userResult, user);
    }

    @Test
    @DisplayName("IT getUserById Fail")
    public void ti_getUserById_not_found() throws Exception {
        // given
        User user = User.builder().id(14L).name("rthomas").birthdate(LocalDate.of(2003, 8, 12)).country("France").phoneNumber("+33658749141").gender(Gender.MALE).build();

        userRepository.save(user);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/users/" + 21L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    @DisplayName("IT createUser Success")
    public void it_createUser() throws Exception {
        // given
        LocalDate birthdate = LocalDate.of(1998, 7,24);

        User user = User.builder().id(12L).name("tbonnet").birthdate(birthdate).country("France").phoneNumber("+33658749141").gender(Gender.MALE).build();

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users")
                .content(convertToJson(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        MvcResult mvcResult = result.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        User userToCreate = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        assertThat(userToCreate).usingRecursiveComparison().ignoringFields("id").isEqualTo(user);
    }

    @Test
    @DisplayName("IT createUser Success with only required fields")
    public void it_createUser_only_required_fields() throws Exception {
        // given
        LocalDate birthdate = LocalDate.of(1975, 7,24);

        User user = User.builder().id(4L).name("ldurand").birthdate(birthdate).country("France").build();

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users")
                .content(convertToJson(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        MvcResult mvcResult = result.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        User userToCreate = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        assertThat(userToCreate).usingRecursiveComparison().ignoringFields("id").isEqualTo(user);
    }

    @Test
    @DisplayName("IT createUser Success with country in lower case")
    public void it_createUser_with_country_in_lower_case() throws Exception {
        // given
        LocalDate birthdate = LocalDate.of(1984, 8,6);

        User user = User.builder().id(3L).name("dthomas").birthdate(birthdate).country("france").phoneNumber("+33658749141").gender(Gender.MALE).build();

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users")
                .content(convertToJson(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        MvcResult mvcResult = result.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        User userToCreate = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        assertThat(userToCreate).usingRecursiveComparison().ignoringFields("id").isEqualTo(user);
    }

    @Test
    @DisplayName("IT createUser Fail - Empty body")
    void it_createUser_no_content() throws Exception {
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("IT createUser Fail - missing field name")
    void it_createUser_missing_name() throws Exception {
        // given
        LocalDate birthdate = LocalDate.of(1998, 7,24);

        User user = User.builder().id(7L).birthdate(birthdate).country("France").phoneNumber("+33658749141").gender(Gender.MALE).build();

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users")
                .content(convertToJson(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        String error = result.andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn().getResolvedException().getMessage();
        assertTrue(StringUtils.contains(error, "The name is required."));
    }

    @Test
    @DisplayName("IT createUser Fail - user not residing in France")
    void it_createUser_not_residing_in_france() throws Exception {
        // given
        LocalDate birthdate = LocalDate.of(1998, 7,24);

        User user = User.builder().id(12L).name("tbonnet").birthdate(birthdate).country("Spain").phoneNumber("+33658749141").gender(Gender.MALE).build();

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users")
                .content(convertToJson(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        String error = result.andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn().getResolvedException().getMessage();
        assertTrue(StringUtils.contains(error, "The user must be residing in France."));
    }

    @Test
    @DisplayName("IT createUser Fail - user under 18yo")
    void it_createUser_user_under_18() throws Exception {
        // given
        LocalDate birthdate = LocalDate.of(2019, 7,24);

        User user = User.builder().id(12L).name("tbonnet").birthdate(birthdate).country("France").phoneNumber("+33658749141").gender(Gender.MALE).build();

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users")
                .content(convertToJson(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        String error = result.andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn().getResolvedException().getMessage();
        assertTrue(StringUtils.contains(error, "The birth date must be greater or equal than 18."));
    }

    @Test
    @DisplayName("IT createUser Fail - invalid phone number")
    void it_createUser_invalid_phone_number() throws Exception {
        // given
        LocalDate birthdate = LocalDate.of(1999, 7,24);

        User user = User.builder().id(12L).name("tbonnet").birthdate(birthdate).country("France").phoneNumber("+34658749141").gender(Gender.MALE).build();

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users")
                .content(convertToJson(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        String error = result.andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn().getResolvedException().getMessage();
        assertTrue(StringUtils.contains(error, "The phone number should start with '+33' or '0' and followed by 9 digits."));
    }

    private String convertToJson(final Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
