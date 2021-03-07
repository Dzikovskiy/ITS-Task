package dzikovskiy.service;

import dzikovskiy.entities.Room;
import dzikovskiy.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public Optional<Room> update(Room room) {
        Room roomToSave;
        Optional<Room> roomFromDb = roomRepository.findById(room.getId());
        if (!roomFromDb.isPresent()) {
            return Optional.empty();
        }

        roomToSave = roomFromDb.get();
        roomToSave.setBulbState(room.isBulbState());
        roomToSave = roomRepository.save(roomToSave);

        return Optional.of(roomToSave);
    }

    public Collection<Room> findAll() {
        return (Collection<Room>) roomRepository.findAll();
    }
}
