package stores.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^[A-Za-zА-Яа-я ]+$", message = "Валюта может содержать только буквы.")
    private String currencyFull;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^[A-Za-zА-Яа-я ]+$", message = "Валюта может содержать только буквы.")
    private String currencyShort;
}