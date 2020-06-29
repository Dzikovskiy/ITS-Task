package dzikovskiy;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;


public class Application {

    public Application() throws IOException, GeoIp2Exception {

    }

    public static void main(String[] args) throws IOException, GeoIp2Exception {
        File database = new File("src/main/resources/DB/GeoLite2-Country.mmdb");
        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName("77.111.246.6");
        //  CityResponse response = dbReader.city(ipAddress);
        CountryResponse response = dbReader.country(ipAddress);
        String countryName = response.getCountry().getName();
//    String cityName = response.getCity().getName();
//    String postal = response.getPostal().getCode();
//    String state = response.getLeastSpecificSubdivision().getName();
        System.out.println("Country: "+ countryName );

    }

}
