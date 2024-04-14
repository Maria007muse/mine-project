package stores.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDealRepository extends JpaRepository<UserDeal, Long> {
    UserDeal findByUserIdAndDealId(Long userId, Long dealId);

    void deleteByDealId(Long dealId);
}

