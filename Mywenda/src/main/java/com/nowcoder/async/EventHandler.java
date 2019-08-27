package com.nowcoder.async;

import java.util.List;

public interface EventHandler {

    //处理事件
    void doHandle(EventModel Model);


    //注册自己,处理自己关心的事件
    List<EventType> getSupportEventTypes();

}
