package stores.repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import stores.entity.DealType;
import stores.repository.DealTypeRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DealTypeRepositoryTest {
    @Autowired
    private DealTypeRepository dealTypeRepository;
    @Test
    public void testSaveDealType() {
        DealType dealType = new DealType();
        dealType.setType("SomeType");
        DealType savedDealType = dealTypeRepository.save(dealType);
        assertThat(savedDealType.getId()).isNotNull();
    }
    @Test
    public void testFindById() {
        DealType dealType = new DealType();
        dealType.setType("SomeType");
        DealType savedDealType = dealTypeRepository.save(dealType);
        Long dealTypeId = savedDealType.getId();
        DealType foundDealType = dealTypeRepository.findById(dealTypeId).orElse(null);
        assertThat(foundDealType).isNotNull();
        assertThat(foundDealType.getId()).isEqualTo(dealTypeId);
    }

    @Test
    public void testUpdateDealType() {
        DealType dealType = new DealType();
        dealType.setType("SomeType");
        DealType savedDealType = dealTypeRepository.save(dealType);
        Long dealTypeId = savedDealType.getId();
        savedDealType.setType("UpdatedType");
        dealTypeRepository.save(savedDealType);
        DealType updatedDealType = dealTypeRepository.findById(dealTypeId).orElse(null);
        assertThat(updatedDealType).isNotNull();
        assertThat(updatedDealType.getType()).isEqualTo("UpdatedType");
    }
    @Test
    public void testDeleteDealType() {
        DealType dealType = new DealType();
        dealType.setType("SomeType");
        DealType savedDealType = dealTypeRepository.save(dealType);
        Long dealTypeId = savedDealType.getId();
        dealTypeRepository.deleteById(dealTypeId);
        assertThat(dealTypeRepository.findById(dealTypeId)).isEmpty();
    }
}

