package dzikovskiy.Entities;

import javax.persistence.*;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean bulbState = false;
    private String countryCode;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBulbState() {
        return bulbState;
    }

    public void setBulbState(boolean bulbState) {
        this.bulbState = bulbState;
    }

    public boolean getBulbState() {
        return bulbState;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changeBulbState(){
        setBulbState(!bulbState);
    }
}
