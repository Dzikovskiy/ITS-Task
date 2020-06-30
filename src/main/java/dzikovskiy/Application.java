package dzikovskiy;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException, GeoIp2Exception {
//        File database = new File("src/main/resources/DB/GeoLite2-Country.mmdb");
//        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
//        InetAddress ipAddress = InetAddress.getByName("77.111.246.6");
//        //  CityResponse response = dbReader.city(ipAddress);
//        CountryResponse response = dbReader.country(ipAddress);
//
//        String countryName = response.getCountry().getName();
////    String cityName = response.getCity().getName();
////    String postal = response.getPostal().getCode();
////    String state = response.getLeastSpecificSubdivision().getName();
//        System.out.println("Country: "+ countryName );

        SpringApplication.run(Application.class, args);
    }

}
