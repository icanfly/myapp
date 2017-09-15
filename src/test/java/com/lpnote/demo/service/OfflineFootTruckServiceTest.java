package com.lpnote.demo.service;

import com.google.common.collect.Lists;
import com.lpnote.demo.entity.FootTruck;
import com.lpnote.demo.entity.FootTruckQuery;
import com.lpnote.demo.service.impl.LuceneSpatialService;
import com.lpnote.demo.service.impl.OfflineFootTruckService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by luopeng on 2017/9/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class OfflineFootTruckServiceTest {

    @Mock
    private LuceneSpatialService spatialService;

    private OfflineFootTruckService offlineFootTruckService;

    @Before
    public void init(){
        offlineFootTruckService = new OfflineFootTruckService();
        offlineFootTruckService.setLuceneSpatialService(spatialService);
    }

    @Test
    public void testSearch() throws Exception {
        List<FootTruck> footTruckList = Lists.newLinkedList();
        when(spatialService.search(any(FootTruckQuery.class))).thenReturn(footTruckList);

        FootTruckQuery query = new FootTruckQuery();
        query.setQuerySize(10);
        query.setMeters(1000);
        query.setLongitude(134.3d);
        query.setLatitude(23.2d);
        List<FootTruck> result = offlineFootTruckService.query(query);

        assertArrayEquals(result.toArray(),footTruckList.toArray());

        verify(spatialService).search(any(FootTruckQuery.class));
    }

}
