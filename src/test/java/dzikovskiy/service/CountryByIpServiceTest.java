package dzikovskiy.service;

import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
class CountryByIpServiceTest {

    private CountryByIpService countryByIpService;

    @BeforeEach
    public void setUp() throws IOException {
        countryByIpService = new CountryByIpService();

    }

    @ParameterizedTest
    @CsvSource({"77.111.246.6,US", "46.216.38.234,BY"})
    void getCountryIsoCode_ShouldGenerateTheExpectedCountryCodePositiveTest(String ip, String countryCode) throws IOException, GeoIp2Exception {
        assertEquals(countryCode, countryByIpService.getCountryIsoCode(ip));
    }

    @ParameterizedTest
    @CsvSource({"77.111.246.6,BY", "77.111.246.6,ZZ", "46.216.38.234,US"})
    void getCountryIsoCode_ShouldGenerateTheExpectedCountryCodeNegativeTest(String ip, String countryCode) throws IOException, GeoIp2Exception {
        assertNotEquals(countryCode, countryByIpService.getCountryIsoCode(ip));
    }


    @ParameterizedTest
    @ValueSource(strings = {"127.0.0.1"})
    void getCountryIsoCode_ShouldThrowGeoIp2ExceptionForIpThatNotInDb(String ip) {
        Assertions.assertThrows(GeoIp2Exception.class, () -> {
            countryByIpService.getCountryIsoCode(ip);
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    void getCountryIsoCode_ShouldThrowExceptionForNullAndEmptyInputs(String ip) {
        Assertions.assertThrows(AddressNotFoundException.class, () -> {
            countryByIpService.getCountryIsoCode(ip);
        });
    }


}