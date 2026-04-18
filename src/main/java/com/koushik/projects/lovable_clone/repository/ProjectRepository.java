package com.koushik.projects.lovable_clone.repository;

import com.koushik.projects.lovable_clone.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("""
            SELECT p FROM Project p
            where p.deletedAt is null
            and exists(
                select 1 from ProjectMember pm 
                where pm.id.projectId = p.id 
                and pm.id.userId = :userId
            )
            order by p.updatedAt desc
           """
    )
    List<Project>findAllAccessibleByUser(@Param("userId")Long userId);

    @Query("""
            select p from Project p
            where p.id=:projectId
                and p.deletedAt is null
                and exists(
                    select 1 from ProjectMember pm
                    where pm.id.projectId = :projectId
                    and pm.id.userId = :userId
            )
           """
    )
    Optional<Project>findAccessibleProjectById(@Param("projectId")Long projectId,
                                               @Param("userId")Long userId);
}
