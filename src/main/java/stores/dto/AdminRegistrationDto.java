package stores.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AdminRegistrationDto {

    @NotEmpty(message = "Имя не должно быть пустым")
    @Pattern(regexp = "[a-zA-Zа-яА-Я ]+", message = "Полное имя должно содержать только буквы")
    private String userName;

    @NotEmpty(message = "Полное имя не должно быть пустым")
    @Pattern(regexp = "[a-zA-Zа-яА-Я ]+", message = "Полное имя должно содержать только буквы")
    private String fullName;

    @NotEmpty(message = "Улица не должна быть пустой")
    private String street;

    @NotEmpty(message = "Город не должен быть пустым")
    @Pattern(regexp = "[a-zA-Zа-яА-Я ]+", message = "Город должен содержать только буквы")
    private String city;

    @NotEmpty(message = "Zip не должен быть пустым")
    @Pattern(regexp = "\\d+", message = "Zip должен содержать только цифры")
    private String zip;

    @NotEmpty(message = "Номер телефона не должен быть пустым")
    @Pattern(regexp = "\\d+", message = "Номер телефона должен содержать только цифры")
    private String phoneNumber;

    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;

}

