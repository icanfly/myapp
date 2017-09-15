package com.lpnote.demo.service;

import com.google.common.collect.Lists;
import com.lpnote.demo.common.util.JacksonMapper;
import com.lpnote.demo.entity.FootTruck;
import com.lpnote.demo.entity.FootTruckQuery;
import com.lpnote.demo.service.impl.OnlineFootTruckService;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by luopeng on 2017/9/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class OnlineFootTruckServiceTest {

    @Mock
    private OkHttpClient httpClient;

    @Mock
    private Call call;

    @Mock
    private ResponseBody body;

    @Mock
    private BufferedSource bufferedSource;

    private OnlineFootTruckService onlineFootTruckService;

    @Before
    public void init(){
        onlineFootTruckService = new OnlineFootTruckService();
        onlineFootTruckService.setOkHttpClient(httpClient);
    }

    @Test
    public void testSearch() throws IOException {
        List<FootTruck> footTruckList = buildFootTrucks();
        String jsonData = JacksonMapper.serialize(footTruckList);

        Request request = new Request.Builder().url("http://www.test.com/url/for/test").build();

        Response response = new Response.Builder().code(200).body(body).protocol(Protocol.HTTP_1_1).message("ok").request(request).build();

        when(httpClient.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);

        when(body.source()).thenReturn(bufferedSource);
        when(bufferedSource.readString(any(Charset.class))).thenReturn(jsonData);

        FootTruckQuery query = new FootTruckQuery();
        query.setQuerySize(10);
        query.setMeters(1000);
        query.setLongitude(134.3d);
        query.setLatitude(23.2d);

        List<FootTruck> result = onlineFootTruckService.query(query);

        assertArrayEquals(result.toArray(),footTruckList.toArray());

        verify(httpClient).newCall(any(Request.class));
        verify(call).execute();
        verify(body).source();
        verify(bufferedSource).readString(any(Charset.class));

    }

    private List<FootTruck> buildFootTrucks() {
        List<FootTruck> retList = Lists.newLinkedList();
        FootTruck ft = new FootTruck();
        ft.setExpirationDate("2027-09-15");
        ft.setAddress("Chongqing China");
        ft.setApplicant("LP's Truck");
        ft.setDayshours("09:00 - 18:00");
        ft.setItems("some item for testing");
        ft.setLatitude(23.4d);
        ft.setLongitude(132.3d);
        retList.add(ft);
        return retList;
    }

}
