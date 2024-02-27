package dev.naresh.qp.model;

import dev.naresh.qp.constant.ProductCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotEmpty
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @NotNull
  @Column(name = "price", nullable = false)
  private double price;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false)
  private ProductCategory category;

  @NotNull
  @Column(name = "quantity", nullable = false)
  private int quantity;

  @NotNull
  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
