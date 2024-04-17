package stores.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stores.entity.DealPlace;

@Repository
public interface DealPlaceRepository extends CrudRepository<DealPlace, Long> {
}
