package dzikovskiy.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

@Service
public class CountryByIpService {

    private final DatabaseReader dbReader;

    public CountryByIpService() throws IOException {
        InputStream stream = new ClassPathResource("/GeoLite2-Country.mmdb").getInputStream();
        File database = File.createTempFile("GeoLite2-Country-temp", ".mmdb");
        try {
            FileUtils.copyInputStreamToFile(stream, database);
        } finally {
            IOUtils.closeQuietly(stream);
        }
        dbReader = new DatabaseReader.Builder(database).build();
    }

    public String getCountryIsoCode(String ip) throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        CountryResponse response = dbReader.country(ipAddress);
        return response.getCountry().getIsoCode();
    }
}
