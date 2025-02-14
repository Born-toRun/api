package kr.borntorun.api.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.borntorun.api.domain.entity.CrewEntity;

public interface CrewRepository extends JpaRepository<CrewEntity, Integer> {
}
