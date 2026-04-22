package com.koushik.projects.lovable_clone.repository;

import com.koushik.projects.lovable_clone.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
