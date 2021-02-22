package ru.netology.web;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;


public class RegistrationTest {

    @Test
    void shouldRegisterCardDelivery() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] input").setValue("Анна Белоусова");
        $("[data-test-id=phone] input").setValue("+79266858100");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно! Встреча успешно забронирована на " + LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern(("dd.MM.yyyy")))));
    }

    @Test
    void shouldNotFillCity() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] input").setValue("Анна Белоусова");
        $("[data-test-id=phone] input").setValue("+7987459034");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldNotFillDate() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        $("[data-test-id=name] input").setValue("Анна Белоусова");
        $("[data-test-id=phone] input").setValue("+7987459034");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date]").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldNotFillName() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+7987459034");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid").shouldHave(exactText("Фамилия и имя Поле обязательно для заполнения"));

    }

    @Test
    void shouldNotFillPhone() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] input").setValue("Анна Белоусова");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid").shouldHave(exactText("Мобильный телефон Поле обязательно для заполнения"));

    }

    @Test
    void shouldNotSubmitAgreement() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] input").setValue("Анна Белоусова");
        $("[data-test-id=phone] input").setValue("+7985469745");
        $(byText("Забронировать")).click();
        $("[data-test-id=agreement]").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }

    @Test
    void shouldSetWrongCity() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Чайковский");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] input").setValue("Анна Белоусова");
        $("[data-test-id=phone] input").setValue("+79266858100");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid").shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void shouldSetWrongDate() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(LocalDate.now().plusDays(-3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] input").setValue("Анна Белоусова");
        $("[data-test-id=phone] input").setValue("+79266858100");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date]").shouldHave(exactText("Заказ на выбранную дату невозможен"));

    }

    @Test
    void shouldSetWrongName() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] input").setValue("Elena Elenina");
        $("[data-test-id=phone] input").setValue("+79266858100");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid").shouldHave(exactText("Фамилия и имя Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void shouldSetWrongPhone() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        $("[data-test-id=date] input").setValue(LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] input").setValue("Анна Белоусова");
        $("[data-test-id=phone] input").setValue("79266858100");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid").shouldHave(exactText("Мобильный телефон Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

}
