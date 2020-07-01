package dzikovskiy.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class CountryByIpService {

    private DatabaseReader dbReader;

    public CountryByIpService() throws IOException {
        File database = new File("src/main/resources/DB/GeoLite2-Country.mmdb");
        dbReader = new DatabaseReader.Builder(database).build();
    }

    public String getCountryIsoCode(String ip) throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        CountryResponse response = dbReader.country(ipAddress);
        return response.getCountry().getIsoCode();
    }
}
