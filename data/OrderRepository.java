package stores.dto;
import org.springframework.data.repository.CrudRepository;
import stores.TacoOrder;
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
}

