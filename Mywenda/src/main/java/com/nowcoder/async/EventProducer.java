package com.nowcoder.async;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.controller.CommentController;
import com.nowcoder.util.Jedistest;
import com.nowcoder.util.RedisKeyUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 向队列中插入
 */
@Service
public class EventProducer {
    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    Jedistest jedistest;

    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedistest.lpush(key, json);
            return true;
        } catch (Exception e) {
            logger.error("事件插入完成" + e.getMessage());
            return false;
        }

    }
}
