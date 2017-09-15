package com.lpnote.demo.service;

import com.lpnote.demo.entity.FootTruck;
import com.lpnote.demo.entity.FootTruckQuery;
import com.lpnote.demo.service.impl.LuceneSpatialService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by luopeng on 2017/9/15.
 */
public class LuceneSpatialServiceTest {

    private LuceneSpatialService service = new LuceneSpatialService();

    @Before
    public void init() throws Exception {
        service.setFileName("/6a9r-agq8-fortest.json");
        service.init();
    }

    @After
    public void destroy() throws IOException {
        service.destroy();
    }

    @Test
    public void testSearch() throws Exception {
        FootTruckQuery query = new FootTruckQuery();
        query.setLatitude(37.72d);
        query.setLongitude(-122.38d);
        query.setMeters(2000);
        query.setQuerySize(10);

        List<FootTruck> searchResult = service.search(query);

        assertTrue(!searchResult.isEmpty() && searchResult.size() == 2);
    }

    @Test
    public void testSearch_size() throws Exception {
        FootTruckQuery query = new FootTruckQuery();
        query.setLatitude(37.72d);
        query.setLongitude(-122.38d);
        query.setMeters(2000);
        query.setQuerySize(1);

        List<FootTruck> searchResult = service.search(query);

        assertTrue(!searchResult.isEmpty() && searchResult.size() == 1);
    }

    @Test
    public void testSearch_null() throws Exception {
        FootTruckQuery query = new FootTruckQuery();
        query.setLatitude(36.72d);
        query.setLongitude(-124.38d);
        query.setMeters(2000);
        query.setQuerySize(10);

        List<FootTruck> searchResult = service.search(query);

        assertTrue(searchResult.isEmpty());
    }

    @Test
    public void testSearch_meter() throws Exception {
        FootTruckQuery query = new FootTruckQuery();
        query.setLatitude(37.72d);
        query.setLongitude(-122.38d);
        query.setMeters(20000);
        query.setQuerySize(10);

        List<FootTruck> searchResult = service.search(query);

        assertTrue(!searchResult.isEmpty() && searchResult.size() == 3);
    }

}
