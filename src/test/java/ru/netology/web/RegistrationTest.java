package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class RegistrationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldReturnPositiveMessage() {
        Generation generation = new Generation();
        String date = generation.dateGeneration(3);
        $("[placeholder='Город']").setValue("Петрозаводск");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").doubleClick().setValue(date);
        $("[name='name']").setValue("Петров Василий");
        $("[name='phone']").setValue("+79200000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + date));
    }

    @Test
    void shouldReturnNegativeMessageForErrorCity() {
        Generation generation = new Generation();
        String date = generation.dateGeneration(3);
        $("[placeholder='Город']").setValue("Париж");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").doubleClick().setValue(date);
        $("[name='name']").setValue("Петров Василий");
        $("[name='phone']").setValue("+79200000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldReturnNegativeMessageForErrorDate() {
        Generation generation = new Generation();
        String date = generation.dateGeneration(2);
        $("[placeholder='Город']").setValue("Петрозаводск");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").doubleClick().setValue(date);
        $("[name='name']").setValue("Петров Василий");
        $("[name='phone']").setValue("+79200000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }


    @Test
    void shouldReturnNegativeMessageForErrorName() {
        Generation generation = new Generation();
        String date = generation.dateGeneration(3);
        $("[placeholder='Город']").setValue("Петрозаводск");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").doubleClick().setValue(date);
        $("[name='name']").setValue("Petr Petrov");
        $("[name='phone']").setValue("+79200000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только " +
                "русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldReturnNegativeMessageForErrorPhone() {
        Generation generation = new Generation();
        String date = generation.dateGeneration(3);
        $("[placeholder='Город']").setValue("Петрозаводск");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").doubleClick().setValue(date);
        $("[name='name']").setValue("Петров Василий");
        $("[name='phone']").setValue("+792000000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldReturnNegativeMessageForCheckbox() {
        Generation generation = new Generation();
        String date = generation.dateGeneration(3);
        $("[placeholder='Город']").setValue("Петрозаводск");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").doubleClick().setValue(date);
        $("[name='name']").setValue("Петров Василий");
        $("[name='phone']").setValue("+79200000000");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldReturnPositiveMessageWithCalendar() {

        $("[placeholder='Город']").setValue("Пе");
        $$(".menu-item__control").last().click();
        $("[data-test-id=date] button").click();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d");
        String nextWeek = LocalDate.now().plusWeeks(1).format(dateFormat);
        String thisWeek = LocalDate.now().format(dateFormat);
        if (Integer.parseInt(thisWeek) > Integer.parseInt(nextWeek)) {
            $("div[data-step='1']").click();
        }
        $$(".calendar__day").find(exactText(nextWeek)).click();
        $("[name='name']").setValue("Петров Василий");
        $("[name='phone']").setValue("+79200000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }
}

