package dzikovskiy.controller;

import dzikovskiy.Entities.Room;
import dzikovskiy.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @PutMapping("/room")
    public String changeBulbState(@RequestBody Room room) {

        Optional<Room> optionalRoom = roomRepository.findById(room.getId());
        Room roomToSave;
        if (optionalRoom.isPresent()) {
            roomToSave = optionalRoom.get();
        } else {
            return "room doesn't exists";
        }
        roomToSave.setBulbState(room.getBulbState());
        roomRepository.save(roomToSave);

        return "room updated";
    }

    @PostMapping("/room")
    public String createRoom(@RequestBody Room room) {
        roomRepository.save(room);
        return "";
    }
}
