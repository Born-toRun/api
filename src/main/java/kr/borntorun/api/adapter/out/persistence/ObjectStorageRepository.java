package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kr.borntorun.api.domain.entity.ObjectStorageEntity;


public interface ObjectStorageRepository extends JpaRepository<ObjectStorageEntity, Integer> {

  List<ObjectStorageEntity> findAllByIdInAndIsDeletedFalse(final List<Integer> fileIds);
}
