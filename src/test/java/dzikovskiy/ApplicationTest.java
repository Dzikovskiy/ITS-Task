package dzikovskiy;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Test
    void IpCountryTest() throws IOException, GeoIp2Exception {
        String ip = "77.111.246.6";
        String ip2 = "46.216.58.219";
        String country = "United States";
        String country2 = "Belarus";
        String dbLocation = "src/main/resources/DB/GeoLite2-Country.mmdb";

        File database = new File(dbLocation);
        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName(ip);
        CountryResponse response = dbReader.country(ipAddress);
        String countryName = response.getCountry().getName();

        ipAddress = InetAddress.getByName(ip2);
        response = dbReader.country(ipAddress);
        String countryName2 = response.getCountry().getName();

        assertEquals(country,countryName);
        assertEquals(country2,countryName2);

    }
}