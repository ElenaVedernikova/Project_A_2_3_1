package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    final DataGenerator generator = new DataGenerator();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

    }

    @Test
    void shouldReturnPositiveMessage() {
        DataGenerator generation = new DataGenerator();
        String date = generation.generateDate(3);
        $("[placeholder='Город']").setValue(generator.generateCity("ru"));
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").doubleClick().setValue(date);
        $("[name='name']").setValue(generator.generateName("ru"));
        $("[name='phone']").setValue(generator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно! Встреча успешно запланирована на " + date));
        String dateNext = generation.generateDate(4);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").doubleClick().setValue(dateNext);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification']").shouldHave(text("У вас уже запланирована " +
                "встреча на другую дату. Перепланировать?"));
        $(".notification_visible .button").click();
        $("[data-test-id='success-notification']").shouldHave(exactText("Успешно! Встреча успешно запланирована на "
                + dateNext));
    }
}

