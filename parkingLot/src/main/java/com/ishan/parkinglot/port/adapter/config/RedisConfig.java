package com.ishan.parkinglot.port.adapter.config;

import com.ishan.parkinglot.port.adapter.service.DefaultSpotSyncService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    return new JedisConnectionFactory();
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    return template;
  }

  @Bean
  public StringRedisTemplate stringRedisTemplate() {
    return new StringRedisTemplate(jedisConnectionFactory());
  }

  @Bean
  public RedisMessageListenerContainer container(JedisConnectionFactory jedisConnectionFactory,
      MessageListenerAdapter listenerAdapter) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(jedisConnectionFactory);
    container.addMessageListener(listenerAdapter, new ChannelTopic(DefaultSpotSyncService.SYNC_CHANNEL));
    return container;
  }

  @Bean
  public MessageListenerAdapter listenerAdapter(DefaultSpotSyncService messageReceiver) {
    return new MessageListenerAdapter(messageReceiver);
  }

}
