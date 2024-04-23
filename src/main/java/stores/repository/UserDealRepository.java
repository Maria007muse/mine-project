package stores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stores.entity.UserDeal;

@Repository
public interface UserDealRepository extends JpaRepository<UserDeal, Long> {
    UserDeal findByUserIdAndDealId(Long userId, Long dealId);
    void deleteByDealId(Long dealId);
}

