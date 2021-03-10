package dzikovskiy.controller;

import dzikovskiy.entities.Room;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
class RoomControllerTest {

    @MockBean
    private RoomController roomController;

    @Autowired
    private MockMvc mvc;


    @Test
    void getAllRoomsReturnsAllRooms() throws Exception {
        Room room1 = new Room();
        room1.setCountryCode("BY");
        room1.setName("Minsk");
        room1.setBulbState(false);
        room1.setId(1L);
        Room room2 = new Room();
        room2.setCountryCode("US");

        Collection<Room> allRooms = Arrays.asList(room1, room2);

        given(roomController.getAllRooms()).willReturn(allRooms);

        mvc.perform(get("/api/rooms")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].countryCode", is(room1.getCountryCode())))
                .andExpect(jsonPath("$[0].id", is(room1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(room1.getName())))
                .andExpect(jsonPath("$[0].bulbState", is(room1.isBulbState())));
    }

    @Test
    void createRoomShouldCreateRoom() throws Exception {
        Room room = new Room();
        room.setCountryCode("BY");
        room.setName("Minsk");
        room.setBulbState(false);
        room.setId(1L);

        MockHttpServletResponse response = mvc.perform(post("/api/rooms")
                .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"bulbState\": false,\n" +
                        "    \"countryCode\": \"BY\",\n" +
                        "    \"name\": \"Minsk\"\n" +
                        "}")
        ).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void updateRoom() throws Exception {
        Room room = new Room();
        room.setCountryCode("BY");
        room.setName("Minsk");
        room.setBulbState(false);
        room.setId(1L);

        MockHttpServletResponse response = mvc.perform(put("/api/rooms/1")
                .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"bulbState\": false,\n" +
                        "    \"countryCode\": \"BY\",\n" +
                        "    \"name\": \"Minsk\"\n" +
                        "}")
        ).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

}