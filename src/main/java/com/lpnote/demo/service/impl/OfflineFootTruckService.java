package com.lpnote.demo.service.impl;

import com.lpnote.demo.entity.FootTruck;
import com.lpnote.demo.entity.FootTruckQuery;
import com.lpnote.demo.service.FootTruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 餐车服务(使用离线数据)
 * Created by luopeng on 2017/9/13.
 */
@Service("offlineFootTruckService")
public class OfflineFootTruckService implements FootTruckService{

    @Autowired
    private LuceneSpatialService luceneSpatialService;

    @Override
    public List<FootTruck> query(FootTruckQuery query) throws Exception {
        return luceneSpatialService.search(query);
    }

    public void setLuceneSpatialService(LuceneSpatialService luceneSpatialService) {
        this.luceneSpatialService = luceneSpatialService;
    }
}
