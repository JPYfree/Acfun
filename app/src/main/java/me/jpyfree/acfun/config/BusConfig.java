package me.jpyfree.acfun.config;

import com.squareup.otto.Bus;

/**
 * Created by Administrator on 2016/6/25.
 */
public class BusConfig {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }
}
