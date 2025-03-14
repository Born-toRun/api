package kr.borntorun.api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.borntorun.api.domain.entity.MarathonEntity;

public interface MarathonRepository extends JpaRepository<MarathonEntity, Long> {

	@Query(
	  "SELECT m FROM MarathonEntity m " +
		"LEFT JOIN FETCH m.marathonBookmarkEntities mb " +
		"WHERE (m.location IN :locations OR :locations IS NULL) AND " +
		"(m.course IN :courses OR :courses IS NULL)")
	List<MarathonEntity> findAllByLocationInAndCourseIn(List<String> locations, List<String> courses);
}
