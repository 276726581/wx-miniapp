package com.jaspercloud.shipkeeper.geo;

import com.jaspercloud.shipkeeper.dto.LocationInfoDTO;
import com.jaspercloud.shipkeeper.service.LocationService;
import com.jaspercloud.shipkeeper.support.geohash.WGS84Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeoServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test() throws Exception {
        LocationService locationService = webApplicationContext.getBean(LocationService.class);
        LocationInfoDTO locationInfo = locationService.getLocationInfo(new WGS84Point(30.0312783511, 120.8607459068));
        System.out.println();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/goods/geo/list")
                .param("latitude", "30.2626285651")
                .param("longitude", "120.1557846002")
                .param("layer", "1")
                .param("count", "30");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        System.out.println();
    }
}
