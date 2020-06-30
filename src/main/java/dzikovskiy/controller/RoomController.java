package dzikovskiy.controller;

import dzikovskiy.Entities.Room;
import dzikovskiy.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public Collection<Room> getRooms(){
        System.out.println("Data sent");
        return (Collection<Room>) roomRepository.findAll();
    }

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
    public ResponseEntity<Room> createRoom(@RequestBody Room room) throws URISyntaxException {
        Room result = roomRepository.save(room);
        return ResponseEntity.ok().body(result);
    }
}
