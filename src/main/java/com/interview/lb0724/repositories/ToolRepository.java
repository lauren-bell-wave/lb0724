package com.interview.lb0724.repositories;

import com.interview.lb0724.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, String> {
}
