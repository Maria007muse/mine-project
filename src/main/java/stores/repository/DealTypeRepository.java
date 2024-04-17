package stores.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stores.entity.DealType;

@Repository
public interface DealTypeRepository extends CrudRepository<DealType, Long> {
}
