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
            and p.owner.id=:userId
            order by p.updatedAt desc
           """
    )
    List<Project>findAllAccessibleByUser(@Param("userId")Long userId);

    @Query("""
            select p from Project p
            left join fetch p.owner
            where p.id=:projectId
                and p.deletedAt is null
                and p.owner.id=:userId
           """
    )
    Optional<Project>findAccessibleProjectById(@Param("projectId")Long projectId,
                                               @Param("userId")Long userId);
}
