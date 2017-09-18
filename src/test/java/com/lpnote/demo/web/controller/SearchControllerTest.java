package com.lpnote.demo.web.controller;

import com.google.common.collect.Lists;
import com.lpnote.demo.TestBase;
import com.lpnote.demo.common.util.JacksonMapper;
import com.lpnote.demo.entity.FootTruck;
import com.lpnote.demo.entity.FootTruckQuery;
import com.lpnote.demo.service.FootTruckService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertArrayEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class SearchControllerTest extends TestBase {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Qualifier("onlineFootTruckService")
    private FootTruckService onlineFootTruckService;

    @MockBean
    @Qualifier("offlineFootTruckService")
    private FootTruckService offlineFootTruckService;

    @Test
    public void testSearch_online() throws Exception {
        List<FootTruck> footTruckList = Lists.newLinkedList();
        buildFootTrucks(footTruckList);

        given(this.onlineFootTruckService.query(any(FootTruckQuery.class))).willReturn(footTruckList);

        String result = mvc.perform(MockMvcRequestBuilders.post("/foot-truck/search")
                .param("latitude", "25")
                .param("longitude", "140")
                .param("querySize", "10")
                .param("meters", "1000")
                .param("source", "online")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<Map<String, Object>> list = (List<Map<String, Object>>) JacksonMapper.deserialize2Map(result).get("data");

        String realData = JacksonMapper.serialize(list);

        assertArrayEquals(JacksonMapper.deserialize2FootTruckList(realData).toArray(), footTruckList.toArray());
    }

    @Test
    public void testSearch_offline() throws Exception {
        List<FootTruck> footTruckList = Lists.newLinkedList();
        buildFootTrucks(footTruckList);

        given(this.offlineFootTruckService.query(any(FootTruckQuery.class))).willReturn(footTruckList);

        String result = mvc.perform(MockMvcRequestBuilders.post("/foot-truck/search")
                .param("latitude", "25")
                .param("longitude", "140")
                .param("querySize", "10")
                .param("meters", "1000")
                .param("source", "offline")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        List<Map<String, Object>> list = (List<Map<String, Object>>) JacksonMapper.deserialize2Map(result).get("data");

        String realData = JacksonMapper.serialize(list);

        assertArrayEquals(JacksonMapper.deserialize2FootTruckList(realData).toArray(), footTruckList.toArray());
    }

    private void buildFootTrucks(List<FootTruck> footTruckList) {

        FootTruck ft = new FootTruck();
        ft.setLongitude(123);
        ft.setLatitude(23);
        ft.setItems("some times for test");
        ft.setDayshours("9:00-18:00");
        ft.setApplicant("LP's Truck");
        ft.setAddress("China Chongqing");
        ft.setExpirationDate("2027-09-15");

        footTruckList.add(ft);

    }

}
