package org.learning.java.springlamiapizzeriarelazioni.repository;

import org.learning.java.springlamiapizzeriarelazioni.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
}
