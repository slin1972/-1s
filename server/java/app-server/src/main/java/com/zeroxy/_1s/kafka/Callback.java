package com.zeroxy._1s.kafka;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SEELE on 2017/5/14.
 */
public interface Callback {


    static final Map<Long, Callback> callbacks = new HashMap<>();

    void run(Object obj);
}
