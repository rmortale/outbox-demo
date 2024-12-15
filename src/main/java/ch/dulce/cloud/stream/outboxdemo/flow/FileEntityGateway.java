package ch.dulce.cloud.stream.outboxdemo.flow;

import ch.dulce.cloud.stream.outboxdemo.domain.FileEntity;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface FileEntityGateway {

  @Gateway(requestChannel = "outbox.input")
  void placeFile(FileEntity file);
}
