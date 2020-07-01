package dzikovskiy.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CountryByIpServiceTest {

    @Test
    void getCountryIsoCode() throws IOException, GeoIp2Exception {
        CountryByIpService countryByIpService = new CountryByIpService();
        String ip = "77.111.246.6";
        String ip2 = "77.111.246.6";
        String ip3 = "46.216.58.219";
        String countryCode = "US";
        String countryCode2 = "ZZ";
        String countryCode3 = "BY";

        assertEquals(countryCode,countryByIpService.getCountryIsoCode(ip));
        assertNotEquals(countryCode2,countryByIpService.getCountryIsoCode(ip2));
        assertEquals(countryCode3,countryByIpService.getCountryIsoCode(ip3));

    }
}