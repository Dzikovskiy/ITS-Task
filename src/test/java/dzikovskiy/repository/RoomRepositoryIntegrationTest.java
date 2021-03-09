package dzikovskiy.repository;

import dzikovskiy.entities.Room;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
class RoomRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void whenFindById_ReturnRoom() {
        // given
        Room room = new Room();
        Room room2 = new Room();
        room = entityManager.persistAndFlush(room);
        room2 = entityManager.persistAndFlush(room2);
        room.setId(1L);
        room2.setId(2L);

        // when
        Optional<Room> found = roomRepository.findById(room.getId());
        Optional<Room> found2 = roomRepository.findById(room2.getId());
        // then
        assertEquals(found.get().getId(), room.getId());
        assertEquals(found2.get().getId(), room2.getId());

    }
}