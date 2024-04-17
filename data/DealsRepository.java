package stores.dto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stores.Deals;

@Repository
public interface DealsRepository extends CrudRepository<Deals, Long> {
}
