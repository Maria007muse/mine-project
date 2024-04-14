package stores.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stores.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}