package dev.naresh.qp.dao;

import dev.naresh.qp.model.Order;
import java.util.UUID;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<Order, UUID> {}
