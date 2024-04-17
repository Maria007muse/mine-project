package stores.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import stores.entity.DealPlace;
import stores.repository.DealPlaceRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DealPlaceRepositoryTest {

    @Autowired
    private DealPlaceRepository dealPlaceRepository;

    @Test
    public void testSaveDealPlace() {
        DealPlace dealPlace = new DealPlace();
        dealPlace.setDealPlaceFull("Full Deal Place");
        dealPlace.setDealPlaceShort("Short Place");

        DealPlace savedDealPlace = dealPlaceRepository.save(dealPlace);
        assertThat(savedDealPlace.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        DealPlace dealPlace = new DealPlace();
        dealPlace.setDealPlaceFull("Full Deal Place");
        dealPlace.setDealPlaceShort("Short Place");

        DealPlace savedDealPlace = dealPlaceRepository.save(dealPlace);
        Long dealPlaceId = savedDealPlace.getId();

        DealPlace foundDealPlace = dealPlaceRepository.findById(dealPlaceId).orElse(null);
        assertThat(foundDealPlace).isNotNull();
        assertThat(foundDealPlace.getId()).isEqualTo(dealPlaceId);
    }

    @Test
    public void testUpdateDealPlace() {
        DealPlace dealPlace = new DealPlace();
        dealPlace.setDealPlaceFull("Full Deal Place");
        dealPlace.setDealPlaceShort("Short Place");

        DealPlace savedDealPlace = dealPlaceRepository.save(dealPlace);
        Long dealPlaceId = savedDealPlace.getId();

        savedDealPlace.setDealPlaceFull("Updated Deal Place");
        dealPlaceRepository.save(savedDealPlace);

        DealPlace updatedDealPlace = dealPlaceRepository.findById(dealPlaceId).orElse(null);
        assertThat(updatedDealPlace).isNotNull();
        assertThat(updatedDealPlace.getDealPlaceFull()).isEqualTo("Updated Deal Place");
    }

    @Test
    public void testDeleteDealPlace() {
        DealPlace dealPlace = new DealPlace();
        dealPlace.setDealPlaceFull("Full Deal Place");
        dealPlace.setDealPlaceShort("Short Place");

        DealPlace savedDealPlace = dealPlaceRepository.save(dealPlace);
        Long dealPlaceId = savedDealPlace.getId();

        dealPlaceRepository.deleteById(dealPlaceId);
        assertThat(dealPlaceRepository.findById(dealPlaceId)).isEmpty();
    }
}
