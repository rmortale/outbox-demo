package ch.dulce.cloud.stream.outboxdemo.flow;

import org.springframework.integration.dsl.IntegrationFlowAdapter;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.store.ChannelMessageStore;
import org.springframework.messaging.MessageHandler;

public class Outbox extends IntegrationFlowAdapter {

  private final MessageHandler businessHandler;
  private final MessageHandler messagePublisherHandler;
  private final ChannelMessageStore channelMessageStore;

  public Outbox(
      MessageHandler businessHandler,
      MessageHandler messagePublisherHandler,
      ChannelMessageStore channelMessageStore) {
    this.businessHandler = businessHandler;
    this.messagePublisherHandler = messagePublisherHandler;
    this.channelMessageStore = channelMessageStore;
  }

  @Override
  protected IntegrationFlowDefinition<?> buildFlow() {
    return from("outbox.input")
        .routeToRecipients(
            routes ->
                routes
                    .transactional()
                    .recipientFlow(businesData -> businesData.handle(businessHandler))
                    .recipientFlow(
                        messagingMiddleware ->
                            messagingMiddleware
                                .channel(c -> c.queue(channelMessageStore, "outbox"))
                                .handle(
                                    messagePublisherHandler,
                                    e ->
                                        e.poller(
                                            poller -> poller.fixedDelay(5000).transactional()))));
  }
}
