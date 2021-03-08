package dzikovskiy.service;

import dzikovskiy.entities.Room;
import dzikovskiy.repository.RoomRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Log4j2
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room save(Room room) {
        log.debug("Method save() called with room: " + room);
        return roomRepository.save(room);
    }

    public Optional<Room> findById(Long id) {
        log.debug("Method findById() called with id: " + id);
        return roomRepository.findById(id);
    }

    public Optional<Room> update(Room room) {
        log.debug("Method update() called with room: " + room);
        Room roomToSave;
        Optional<Room> roomFromDb = roomRepository.findById(room.getId());
        if (!roomFromDb.isPresent()) {
            log.debug("Room not found in database");
            return Optional.empty();
        }

        roomToSave = roomFromDb.get();
        roomToSave.setBulbState(room.isBulbState());
        roomToSave.setName(room.getName());
        roomToSave.setCountryCode(room.getCountryCode());
        roomToSave = roomRepository.save(roomToSave);

        return Optional.of(roomToSave);
    }

    public Collection<Room> findAll() {
        return (Collection<Room>) roomRepository.findAll();
    }
}
