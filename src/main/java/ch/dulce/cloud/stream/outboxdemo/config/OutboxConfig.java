package ch.dulce.cloud.stream.outboxdemo.config;

import ch.dulce.cloud.stream.outboxdemo.flow.Outbox;
import jakarta.persistence.EntityManager;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.integration.jdbc.store.channel.PostgresChannelMessageStoreQueryProvider;
import org.springframework.integration.jpa.dsl.Jpa;
import org.springframework.integration.jpa.dsl.JpaUpdatingOutboundEndpointSpec;
import org.springframework.integration.jpa.outbound.JpaOutboundGateway;
import org.springframework.integration.jpa.support.PersistMode;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.kafka.dsl.KafkaProducerMessageHandlerSpec;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.integration.store.ChannelMessageStore;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class OutboxConfig {

  @Bean
  public JdbcChannelMessageStore jdbcChannelMessageStore(DataSource dataSource) {
    JdbcChannelMessageStore jdbcChannelMessageStore = new JdbcChannelMessageStore(dataSource);
    jdbcChannelMessageStore.setChannelMessageStoreQueryProvider(
        new PostgresChannelMessageStoreQueryProvider());
    return jdbcChannelMessageStore;
  }

  @Bean
  public KafkaProducerMessageHandlerSpec<?, ?, ?> ordersKafkaProducer(
      KafkaTemplate<?, ?> kafkaTemplate) {
    return Kafka.outboundChannelAdapter(kafkaTemplate).topic("orders");
  }

  @Bean
  public JpaUpdatingOutboundEndpointSpec ordersJpaHandler(EntityManager entityManager) {
    return Jpa.outboundAdapter(entityManager).persistMode(PersistMode.PERSIST);
  }

  @Bean
  public Outbox outbox(
      JpaOutboundGateway ordersJpaHandler,
      KafkaProducerMessageHandler<?, ?> kafkaMessageHandler,
      ChannelMessageStore channelMessageStore) {
    return new Outbox(ordersJpaHandler, kafkaMessageHandler, channelMessageStore);
  }
}
