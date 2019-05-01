package com.thebund1st.daming.redis;

/**
 * RestTemplate has different .delete(K k) signature in 1.x and 2.x.
 * This interface is used to decouple the Redis Adapter with RestTemplate.delete(K k) method.
 *
 * @since 0.9.5
 */
public interface DeleteFromRedis {
    void delete(String key);
}
