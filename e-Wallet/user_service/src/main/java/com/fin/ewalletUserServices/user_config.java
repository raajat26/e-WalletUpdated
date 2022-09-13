package com.fin.ewalletUserServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;
@EnableTransactionManagement
@Configuration
public class user_config {

    @Bean
    public LettuceConnectionFactory getConnectionFactory() {
        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration("redis-10741.c305.ap-south-1-1.ec2.cloud.redislabs.com", 10741);
        rsc.setPassword("fiGBtUuSqxcBzQQ2ECItTVHzMDXsJd5i");
        LettuceConnectionFactory ltcf = new LettuceConnectionFactory(rsc);
        return ltcf;
    }

    @Bean
    public RedisTemplate<String, Object> getTemplate() {
        RedisTemplate<String, Object> temp = new RedisTemplate();
        temp.setValueSerializer(new JdkSerializationRedisSerializer());
        temp.setKeySerializer(new StringRedisSerializer());
        temp.setHashKeySerializer(new StringRedisSerializer());
        temp.setHashValueSerializer(new StringRedisSerializer());
        temp.setConnectionFactory(this.getConnectionFactory());
        return temp;
    }


    @Bean
    Properties getKafkaProp(){
        Properties pro = new Properties();
        pro.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        pro.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        pro.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return pro;
    }

    @Bean
    ProducerFactory<String, String> getProducerFactory(){
        return new DefaultKafkaProducerFactory(getKafkaProp());
    }

    @Bean
    KafkaTemplate<String, String> getKafkaTemplateFromProducerFactory(){
        return new KafkaTemplate<>(getProducerFactory());
    }

    @Bean
    ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
