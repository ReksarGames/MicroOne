package com.example.microone.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
/**
 * {@code @Configuration} class for setting up Kafka Streams in a Spring application.
 * It defines a bean for {@link KafkaStreams} that will be created and configured by the Spring container.
 */
@Configuration
public class KafkaStreamsConfig {

    /**
     * Retrieves the bootstrap servers' address from the Spring application properties.
     *
     * @return the bootstrap servers' address
     */
    @Value("${spring.kafka.admin.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Creates and configures a new instance of {@link KafkaStreams}.
     *
     * @return a new instance of {@link KafkaStreams}
     */
    @Bean
    public KafkaStreams kafkaStreams() {
        // Create a new instance of {@link Properties} to store Kafka Streams configuration.
        Properties props = new Properties();

        // Set the application ID for the Kafka Streams instance.
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "consumers");

        // Set the bootstrap servers' address for the Kafka Streams instance.
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // Create a new instance of {@link StreamsBuilder} to define the Kafka Streams topology.
        StreamsBuilder builder = new StreamsBuilder();

        // Define the Kafka Streams topology by consuming messages from the "input-topic",
        // transforming them by converting to uppercase, and producing the transformed messages to the "output-topic".
        builder.stream("message", Consumed.with(Serdes.String(), Serdes.String()))
                .mapValues(value -> value.toUpperCase())
                .to("transactional", Produced.with(Serdes.String(), Serdes.String()));

        // Build the Kafka Streams topology.
        Topology topology = builder.build();

        // Create and return a new instance of {@link KafkaStreams} with the defined topology and configuration.
        return new KafkaStreams(topology, props);
    }
}