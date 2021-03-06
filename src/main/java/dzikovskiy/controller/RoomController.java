package dzikovskiy.controller;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import dzikovskiy.entities.Room;
import dzikovskiy.repository.RoomRepository;
import dzikovskiy.service.CountryByIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CountryByIpService countryByIpService;

    //check country of client and of a room that he want to enter, if same return HttpStatus OK
    @GetMapping("/check-room-by-ip/{ip}/{id}")
    public ResponseEntity<?> checkRoomAvailabilityByIp(@PathVariable("ip") String ip, @PathVariable("id") Long id) throws IOException, GeoIp2Exception {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()) {
            if (room.get().getCountryCode().equals(countryByIpService.getCountryIsoCode(ip))) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        System.out.println(ip + " " + id + " " + countryByIpService.getCountryIsoCode(ip));
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/rooms")
    public Collection<Room> getAllRooms() {
        return (Collection<Room>) roomRepository.findAll();
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<?> getRoom(@PathVariable Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    //rest controller that accepts bulb state from server and set it to accepted room
    @PutMapping("/room/{id}")
    public ResponseEntity<?> changeBulbState(@RequestBody Room room) {

        Optional<Room> optionalRoom = roomRepository.findById(room.getId());
        Room roomToSave;
        if (optionalRoom.isPresent()) {
            roomToSave = optionalRoom.get();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        roomToSave.setBulbState(room.getBulbState());
        Room result = roomRepository.save(roomToSave);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/room")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) throws URISyntaxException {
        Room result = roomRepository.save(room);
        return ResponseEntity.ok().body(result);
    }
}
