package ch.dulce.cloud.stream.outboxdemo;

import ch.dulce.cloud.stream.outboxdemo.domain.FileEntity;
import ch.dulce.cloud.stream.outboxdemo.flow.FileEntityGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OutboxDemoApplication implements CommandLineRunner {

  @Autowired private FileEntityGateway fileEntityGateway;

  public static void main(String[] args) {
    SpringApplication.run(OutboxDemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    FileEntity fileEntity = new FileEntity();
    fileEntity.setFilename("file1.txt");
    fileEntityGateway.placeFile(fileEntity);
  }
}
