package com.lpnote.demo.service.impl;

import com.lpnote.demo.common.util.JacksonMapper;
import com.lpnote.demo.entity.FootTruck;
import com.lpnote.demo.entity.FootTruckQuery;
import com.lpnote.demo.service.FootTruckService;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 餐车服务(在线实时数据)
 * Created by luopeng on 2017/9/13.
 */
@Service("onlineFootTruckService")
public class OnlineFootTruckService implements FootTruckService {

    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public List<FootTruck> query(FootTruckQuery query) throws IOException {

        HttpUrl.Builder urlBuilder = new HttpUrl.Builder();
        urlBuilder.scheme("https").host("data.sfgov.org").addPathSegments("/resource/6a9r-agq8.json");
        urlBuilder.addQueryParameter("status", "APPROVED");
        urlBuilder.addQueryParameter("$select", "applicant,address,fooditems,latitude,longitude,dayshours,expirationdate,"
                                                + "distance_in_meters(location, 'POINT(" + query.getLongitude() + " " + query.getLatitude()
                                                + ")') AS range");
        urlBuilder.addQueryParameter("$where", "expirationdate > '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                                               + "' AND within_circle(location," + query.getLatitude() + ","
                                               + query.getLongitude() + "," + query.getMeters() + ")");
        urlBuilder.addQueryParameter("$order", "range");
        urlBuilder.addQueryParameter("$limit", String.valueOf(query.getQuerySize()));

        Request request = new Request.Builder().url(urlBuilder.build()).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            return JacksonMapper.deserialize2FootTruckList(response.body().string());
        }

        throw new IOException(response.code() + ":" + response.message());
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }
}
