package ch.dulce.cloud.stream.outboxdemo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "UPLOADED_FILES")
public class FileEntity implements Serializable {

  @Id @GeneratedValue private long id;

  @Column(nullable = false)
  private String filename;
}
