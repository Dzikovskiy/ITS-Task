package dzikovskiy.controller;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import dzikovskiy.entities.Room;
import dzikovskiy.service.CountryByIpService;
import dzikovskiy.service.RequestService;
import dzikovskiy.service.RoomService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Log4j2
public class RoomController {

    private final CountryByIpService countryByIpService;

    private final RequestService requestService;

    private final RoomService roomService;

    public RoomController(CountryByIpService countryByIpService, RequestService requestService, RoomService roomService) {
        this.countryByIpService = countryByIpService;
        this.requestService = requestService;
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public Collection<Room> getAllRooms() {
        return roomService.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id, HttpServletRequest request) {
        final String clientIp = requestService.getClientIp(request);
        Optional<Room> room = roomService.findById(id);
        log.debug("Method getRoom() called with room id: " + id + "and client ip:" + clientIp);

        if (!room.isPresent()) {
            log.debug("Room not found with id: " + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            if (countryByIpService.getCountryIsoCode(clientIp).equalsIgnoreCase(room.get().getCountryCode())) {
                Room roomFromOptional = room.get();
                log.debug("Room found for ip: " + clientIp + " with room id:" + id);
                return ResponseEntity.ok().body(roomFromOptional);
            } else {
                log.debug("Room enter forbidden for ip: " + clientIp + " and room id:" + id);
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (GeoIp2Exception e) {
            log.error("Caught GeoIp2Exception exception with given ip: " + clientIp + "\n" + e);
        } catch (IOException e) {
            log.error("Caught IO exception in getRoom() " + e);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@RequestBody Room room) {
        log.debug("Method updateRoom() called with room: " + room);
        Optional<Room> roomFromDb = roomService.update(room);
        if (!roomFromDb.isPresent()) {
            log.debug("Room with id: " + room.getId() + " not found in database");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.debug("Returned room: " + roomFromDb.get());
        return ResponseEntity.ok().body(roomFromDb.get());
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        log.debug("Method createRoom() called with room: " + room);
        Room result = roomService.save(room);
        URI location = URI.create(String.format("/room/%s", result.getId()));

        log.debug("Returned response with location: " + location + " and saved room: " + result);
        return ResponseEntity.created(location).body(result);
    }
}
