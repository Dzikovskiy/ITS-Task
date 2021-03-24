package dzikovskiy.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {RequestService.class})
@ExtendWith(SpringExtension.class)
class RequestServiceTest {
    @Autowired
    private RequestService requestService;

    @ParameterizedTest
    @CsvSource({"X-Forwarded-For,46.216.38.234", "Proxy-Client-IP,46.216.38.234", "WL-Proxy-Client-IP,46.216.38.234"})
    public void testGetClientIpWithHeader(String header, String ip) {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader(header, ip);
        assertEquals(requestService.getClientIp(mockHttpServletRequest), ip);
    }

    @ParameterizedTest
    @CsvSource({"127.0.0.1", "0:0:0:0:0:0:0:1"})
    public void testGetClientIpWithLocalHost(String remoteAddr) throws UnknownHostException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRemoteAddr(remoteAddr);

        assertEquals(requestService.getClientIp(mockHttpServletRequest), InetAddress.getLocalHost().getHostAddress());
    }

}

