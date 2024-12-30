package com.eaa.consumer.config;

import com.eaa.consumer.dto.Greeting;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public Map<String, Object> stringConsumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "jt-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.eaa.consumer.dto"); //  Comma-delimited list of package patterns allowed for deserialization. * means deserialize all.
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false); //  (default true): You can set it to false to retain headers set by the serializer.
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.eaa.consumer.dto.Greeting"); //Fallback type for deserialization of values if no header information is present.
        return props;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(stringConsumerConfig());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setRecordFilterStrategy(record -> record.value().toString().contains("World"));
        return factory;
    }

    @Bean
    public Map<String, Object> getCommonConfigProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "jt-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.eaa.consumer.dto"); //  Comma-delimited list of package patterns allowed for deserialization. * means deserialize all.
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false); //  (default true): You can set it to false to retain headers set by the serializer.
        return props;
    }

    @Bean
    public ConsumerFactory<String, Greeting> greetingConsumerFactory() {
        Map<String, Object> props = getCommonConfigProperties();
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.eaa.consumer.dto.Greeting"); //Fallback type for deserialization of values if no header information is present.
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Greeting> greetingKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Greeting> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(greetingConsumerFactory());
        return factory;
    }


}
