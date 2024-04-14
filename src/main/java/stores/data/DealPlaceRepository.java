package stores.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stores.DealPlace;

@Repository
public interface DealPlaceRepository extends CrudRepository<DealPlace, Long> {
}
