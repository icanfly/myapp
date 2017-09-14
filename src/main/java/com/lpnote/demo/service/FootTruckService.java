package com.lpnote.demo.service;

import com.lpnote.demo.entity.FootTruck;
import com.lpnote.demo.entity.FootTruckQuery;

import java.io.IOException;
import java.util.List;

/**
 * Created by luopeng on 2017/9/13.
 */
public interface FootTruckService {

    List<FootTruck> query(FootTruckQuery query) throws Exception;

}
