package dzikovskiy.controller;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import dzikovskiy.Entities.Room;
import dzikovskiy.Repository.RoomRepository;
import dzikovskiy.service.CountryByIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CountryByIpService countryByIpService;

    @GetMapping("/check-room-by-ip/{ip}/{id}")
    public ResponseEntity<?> checkRoomAvailabilityByIp(@PathVariable("ip") String ip, @PathVariable("id") Long id) throws IOException, GeoIp2Exception {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()) {
            if (room.get().getCountryCode().equals(countryByIpService.getCountryIsoCode(ip))) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        System.out.println(ip+" "+ id+" "+countryByIpService.getCountryIsoCode(ip));
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/rooms")
    public Collection<Room> getRooms() {
        System.out.println("rooms sent");
        //System.out.println("Rooms sent");
        return (Collection<Room>) roomRepository.findAll();
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<?> getRoom(@PathVariable Long id) {
        Optional<Room> room = roomRepository.findById(id);
        System.out.println("room sent");
        return room.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

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
