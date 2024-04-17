package stores.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import stores.entity.Currency;
import stores.repository.CurrencyRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void testSaveCurrency() {
        Currency currency = new Currency();
        currency.setCurrencyFull("Full Currency");
        currency.setCurrencyShort("Short Currency");

        Currency savedCurrency = currencyRepository.save(currency);
        assertThat(savedCurrency.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        Currency currency = new Currency();
        currency.setCurrencyFull("Full Currency");
        currency.setCurrencyShort("Short Currency");

        Currency savedCurrency = currencyRepository.save(currency);
        Long currencyId = savedCurrency.getId();

        Currency foundCurrency = currencyRepository.findById(currencyId).orElse(null);
        assertThat(foundCurrency).isNotNull();
        assertThat(foundCurrency.getId()).isEqualTo(currencyId);
    }

    @Test
    public void testUpdateCurrency() {
        Currency currency = new Currency();
        currency.setCurrencyFull("Full Currency");
        currency.setCurrencyShort("Short Currency");

        Currency savedCurrency = currencyRepository.save(currency);
        Long currencyId = savedCurrency.getId();

        savedCurrency.setCurrencyFull("Updated Currency");
        currencyRepository.save(savedCurrency);

        Currency updatedCurrency = currencyRepository.findById(currencyId).orElse(null);
        assertThat(updatedCurrency).isNotNull();
        assertThat(updatedCurrency.getCurrencyFull()).isEqualTo("Updated Currency");
    }

    @Test
    public void testDeleteCurrency() {
        Currency currency = new Currency();
        currency.setCurrencyFull("Full Currency");
        currency.setCurrencyShort("Short Currency");

        Currency savedCurrency = currencyRepository.save(currency);
        Long currencyId = savedCurrency.getId();

        currencyRepository.deleteById(currencyId);
        assertThat(currencyRepository.findById(currencyId)).isEmpty();
    }
}
