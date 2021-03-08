package dzikovskiy.controller;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import dzikovskiy.entities.Room;
import dzikovskiy.service.CountryByIpService;
import dzikovskiy.service.RequestService;
import dzikovskiy.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private CountryByIpService countryByIpService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public Collection<Room> getAllRooms() {
        return roomService.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id, HttpServletRequest request) {
        Optional<Room> room = roomService.findById(id);
        if (!room.isPresent()) {
            System.out.println("room not found with id " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            if (countryByIpService.getCountryIsoCode(requestService.getClientIp(request)).equalsIgnoreCase(room.get().getCountryCode())) {
                Room roomFromOptional = room.get();
                System.out.println("room found for ip " + requestService.getClientIp(request) + " id:" + id);
                return ResponseEntity.ok().body(roomFromOptional);
            } else {
                System.out.println("room enter forbidden for ip " + requestService.getClientIp(request) + " id:" + id);
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
        }

//        System.out.println(request.getHeader("X-FORWARDED-FOR") + "one");
//        System.out.println(request.getRemoteAddr() + "two");
//        System.out.println(requestService.getClientIp(request) + " three");

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //rest controller that accepts bulb state from server and set it to accepted room
    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@RequestBody Room room) {
        Optional<Room> roomFromDb = roomService.update(room);
        if (!roomFromDb.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(roomFromDb.get());
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room result = roomService.save(room);
        URI location = URI.create(String.format("/room/%s", result.getId()));
        return ResponseEntity.created(location).body(result);
    }
}
