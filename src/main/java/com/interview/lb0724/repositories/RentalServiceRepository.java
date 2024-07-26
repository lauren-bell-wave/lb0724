package com.interview.lb0724.repositories;

import com.interview.lb0724.entities.RentalTerms;
import com.interview.lb0724.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalServiceRepository extends JpaRepository<RentalTerms, String> {
}
