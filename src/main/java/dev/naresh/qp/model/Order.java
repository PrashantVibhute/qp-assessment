package dev.naresh.qp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_confirmation")
public class Order {

  @Id private UUID id;

  @NotNull
  @Column(name = "amount")
  private double amount;

  @NotNull
  @Column(name = "description")
  private String description;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
}
