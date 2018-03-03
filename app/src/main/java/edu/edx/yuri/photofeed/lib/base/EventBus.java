package edu.edx.yuri.photofeed.lib.base;

/**
 * Created by yuri_ on 28/12/2017.
 */

public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
