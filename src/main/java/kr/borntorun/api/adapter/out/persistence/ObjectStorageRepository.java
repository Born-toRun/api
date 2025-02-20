package kr.borntorun.api.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.borntorun.api.domain.entity.ObjectStorageEntity;

public interface ObjectStorageRepository extends JpaRepository<ObjectStorageEntity, Long> {
}
