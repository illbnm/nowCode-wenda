package com.nowcoder.service;

import com.nowcoder.util.Jedistest;
import com.nowcoder.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    Jedistest jedistest;

    /**
     * 获取用户喜欢的状态
     * 喜欢返回 1
     * 不喜欢   -1
     * 其他 0
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public long getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if (jedistest.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        return jedistest.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
    }

    /**
     *  获取点赞人数
     * @param entityType
     * @param entityId
     * @return
     */
    public long getLikeCount(int entityType, int entityId) {
        String likekey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        return jedistest.scard(likekey);
    }


    /**
     * 返回点赞结果
     * 一个用户只能LIke 或者DisLike
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedistest.sadd(likeKey, String.valueOf(userId));

        //删去Dislike
        String disLikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedistest.srem(disLikeKey, String.valueOf(userId));
        return jedistest.scard(likeKey);
    }

    /**
     * 获取取消赞的结果
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public long dislike(int userId, int entityType, int entityId) {
        String disLikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedistest.sadd(disLikeKey, String.valueOf(userId));

        //删去Dislike
        String LikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedistest.srem(LikeKey, String.valueOf(userId));
        return jedistest.scard(disLikeKey);
    }

}
