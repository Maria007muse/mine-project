package stores.repository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import stores.entity.Deals;
import stores.repository.DealsRepository;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DealsRepositoryTest {

    @Autowired
    private DealsRepository dealsRepository;

    @Test
    public void testSaveDeal() {
        Deals deal = new Deals();
        deal.setTypeId(1L);
        deal.setPlaceId(1L);
        deal.setCurrencyId(1L);
        deal.setTicker("AAPL");
        deal.setOrderNumber("123456");
        deal.setDealNumber("654321");
        deal.setDealQuantity(100);
        deal.setDealPrice(150.0);
        deal.setDealTotalCost(15000.0);
        deal.setDealTrader("John Doe");
        deal.setDealCommission(50.0);

        Deals savedDeal = dealsRepository.save(deal);
        assertThat(savedDeal.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        Deals deal = new Deals();
        deal.setTypeId(1L);
        deal.setPlaceId(1L);
        deal.setCurrencyId(1L);
        deal.setTicker("AAPL");
        deal.setOrderNumber("123456");
        deal.setDealNumber("654321");
        deal.setDealQuantity(100);
        deal.setDealPrice(150.0);
        deal.setDealTotalCost(15000.0);
        deal.setDealTrader("John Doe");
        deal.setDealCommission(50.0);

        Deals savedDeal = dealsRepository.save(deal);
        Long dealId = savedDeal.getId();

        Deals foundDeal = dealsRepository.findById(dealId).orElse(null);
        assertThat(foundDeal).isNotNull();
        assertThat(foundDeal.getId()).isEqualTo(dealId);
    }

    @Test
    public void testUpdateDeal() {
        Deals deal = new Deals();
        deal.setTypeId(1L);
        deal.setPlaceId(1L);
        deal.setCurrencyId(1L);
        deal.setTicker("AAPL");
        deal.setOrderNumber("123456");
        deal.setDealNumber("654321");
        deal.setDealQuantity(100);
        deal.setDealPrice(150.0);
        deal.setDealTotalCost(15000.0);
        deal.setDealTrader("John Doe");
        deal.setDealCommission(50.0);

        Deals savedDeal = dealsRepository.save(deal);
        Long dealId = savedDeal.getId();

        savedDeal.setDealPrice(160.0);
        dealsRepository.save(savedDeal);

        Deals updatedDeal = dealsRepository.findById(dealId).orElse(null);
        assertThat(updatedDeal).isNotNull();
        assertThat(updatedDeal.getDealPrice()).isEqualTo(160.0);
    }

    @Test
    public void testDeleteDeal() {
        Deals deal = new Deals();
        deal.setTypeId(1L);
        deal.setPlaceId(1L);
        deal.setCurrencyId(1L);
        deal.setTicker("AAPL");
        deal.setOrderNumber("123456");
        deal.setDealNumber("654321");
        deal.setDealQuantity(100);
        deal.setDealPrice(150.0);
        deal.setDealTotalCost(15000.0);
        deal.setDealTrader("John Doe");
        deal.setDealCommission(50.0);

        Deals savedDeal = dealsRepository.save(deal);
        Long dealId = savedDeal.getId();
        dealsRepository.deleteById(dealId);
        assertThat(dealsRepository.findById(dealId)).isEmpty();
    }
}



