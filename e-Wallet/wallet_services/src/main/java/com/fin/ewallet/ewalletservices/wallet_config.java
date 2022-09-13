package com.fin.ewallet.ewalletservices;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sun.org.apache.xpath.internal.operations.String;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.Properties;

@Configuration
public class wallet_config {


    @Bean
    Properties getKafkaProp(){
        Properties pro = new Properties();
        pro.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        pro.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        pro.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // below code to make it act as key/value pair in the
        pro.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        pro.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        pro.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);


        return pro;
    }

    @Bean
    ProducerFactory<String, String> getProducerFactory(){
        return new DefaultKafkaProducerFactory(getKafkaProp());
    }

    @Bean
    ConsumerFactory<String, String> getConsumerFactory(){return new DefaultKafkaConsumerFactory(getKafkaProp());
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory(){
        // if we want to create multiple threads in our listner thats why implementing concurrentkafkaListener;
    ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory();
    kafkaListenerContainerFactory.setConsumerFactory(getConsumerFactory());
    return kafkaListenerContainerFactory;

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
