package stores.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stores.entity.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}