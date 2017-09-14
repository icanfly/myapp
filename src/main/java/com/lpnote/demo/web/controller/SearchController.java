package com.lpnote.demo.web.controller;

import com.lpnote.demo.entity.FootTruckQuery;
import com.lpnote.demo.service.FootTruckService;
import com.lpnote.demo.web.support.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luopeng on 2017/9/13.
 */
@RestController
@RequestMapping("/foot-truck")
public class SearchController {

    @Autowired
    @Qualifier("onlineFootTruckService")
    private FootTruckService onlineFootTruckService;

    @Autowired
    @Qualifier("offlineFootTruckService")
    private FootTruckService offlineFootTruckService;

    @PostMapping("/search")
    public Result search(String source, FootTruckQuery query) {

        try {
            //验证及修正参数
            query.verify();

            if (StringUtils.equals("online", source)) {
                return Result.me().success(onlineFootTruckService.query(query));
            } else if (StringUtils.equals("offline", source)) {
                return Result.me().success(offlineFootTruckService.query(query));
            } else {
                throw new IllegalArgumentException("source:" + source);
            }

        } catch (Exception e) {
            return Result.me().exception(e);
        }
    }

}
