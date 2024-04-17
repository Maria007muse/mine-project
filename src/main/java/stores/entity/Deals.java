package stores.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table
public class Deals {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long typeId;
    private Long placeId;
    private Long currencyId;

    @NotBlank(message = "Тикер не может быть пустым.")
    @Size(min = 3, message = "Должно быть больше 3х символов")
    @Pattern(regexp = "^[A-Za-zА-Яа-я ]+$", message = "Тикер может содержать только буквы.")
    private String ticker;
    
    @NotBlank(message = "Номер заказа не может быть пустым.")
    @Pattern(regexp = "\\d{6}", message = "Номер заказа должен состоять из 6 цифр.")
    private String orderNumber;
    
    @NotBlank(message = "Номер сделки не может быть пустым.")
    @Pattern(regexp = "\\d{6}", message = "Номер сделки должен состоять из 6 цифр.")
    private String dealNumber;

    @NotNull(message = "Количество ценных бумаг сделки не может быть пустым.")
    @Max(value = 10000, message = "Максимальное значение для количества ценных бумаг - 10000")
    private Integer dealQuantity;

    @NotNull(message = "Цена сделки не может быть пустой.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена сделки должна быть больше 0")
    private Double  dealPrice;

    @NotNull(message = "Цена сделки не может быть пустой.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена сделки должна быть больше 0")
    private Double  dealTotalCost;

    @NotBlank(message = "Трейдер сделки не может быть пустым.")
    private String dealTrader;

    @NotNull(message = "Комиссия не может быть пустой.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена сделки должна быть больше 0")
    private Double  dealCommission;

}










