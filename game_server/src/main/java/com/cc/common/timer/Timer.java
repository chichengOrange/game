package com.cc.common.timer;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * Created by AAS on 2018/3/21.
 */
//@Configuration  //打开注释 可开启定时器
@EnableScheduling
public class Timer {
    @Scheduled(cron = "0 0 17 ? * *")//下午17点跑
    public void  tiemrStart(){

        System.out.print(new Date());
    }
}
