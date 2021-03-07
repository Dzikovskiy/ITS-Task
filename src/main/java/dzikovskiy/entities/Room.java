package dzikovskiy.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "room")
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean bulbState = false;
    private String countryCode;
    private String name;

    public void changeBulbState() {
        setBulbState(!bulbState);
    }
}
