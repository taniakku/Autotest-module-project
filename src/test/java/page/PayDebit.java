package page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.DELETE;
import io.qameta.allure.Step;

public class PayDebit {

    private SelenideElement payButton = $(withText("Купить"));
    private SelenideElement header = $(withText("Оплата по карте"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement cardHolderField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvvNumberField = $("[placeholder='999']");
    private SelenideElement nextButton = $(withText("Продолжить"));
    private SelenideElement wrongFormatError = $(withText("Неверный формат"));
    private SelenideElement successfulNotification = $(".notification_status_ok");
    private SelenideElement unsuccessfulNotification = $(".notification_status_error");
    private SelenideElement requiredFieldError = $(byText("Поле обязательно для заполнения")).parent().$(".input__sub");
    private SelenideElement cardInvalidDatesError = $(withText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredDatesError = $(withText("Истёк срок действия карты"));
    private SelenideElement popUpNotification = $(".notification");


    public PayDebit() {
        header.shouldBe(visible).shouldHave(exactText("Оплата по карте"));
    }

    private void clearField() {
        cardNumberField.sendKeys(CONTROL + "A", DELETE);
        monthField.sendKeys(CONTROL + "A", DELETE);
        yearField.sendKeys(CONTROL + "A", DELETE);
        cardHolderField.sendKeys(CONTROL + "A", DELETE);
        cvvNumberField.sendKeys(CONTROL + "A", DELETE);
    }

    @Step("Форма заполнена данными")
    public void sendData(String card, String month, String year, String name, String cvv) {
        clearField();
        cardNumberField.setValue(card);
        monthField.setValue(month);
        yearField.setValue(year);
        cardHolderField.setValue(name);
        cvvNumberField.setValue(cvv);
        nextButton.click();
    }

    @Step("Заполняем форму валидными данными: карта одобрена")
    public void successfulSendingForm(String card, String month, String year, String name, String cvv) {
        sendData(card, month, year, name, cvv);
        successfulNotification.shouldBe(visible, Duration.ofSeconds(13)).shouldHave(exactText("Успешно\n" + "Операция одобрена Банком."));
    }

    @Step("Заполняем форму невалидными данными карты: карта просрочена")
    public void unsuccessfulSendingForm(String card, String month, String year, String name, String cvv) {
        sendData(card, month, year, name, cvv);
        unsuccessfulNotification.shouldBe(visible, Duration.ofSeconds(16)).shouldHave(exactText("Ошибка\n" + "Ошибка! Банк отказал в проведении операции."));
    }

    @Step("Заполняем форму невалидными данными: неверный формат")
    public void formatError(String card, String month, String year, String name, String cvv) {
        sendData(card, month, year, name, cvv);
        wrongFormatError.shouldBe(visible);
    }

    @Step("Не заполняем форму")
    public void emptyFieldError(String card, String month, String year, String name, String cvv) {
        sendData(card, month, year, name, cvv);
        requiredFieldError.shouldBe(visible);
    }

    @Step("Заполняем форму невалидными данными: невалидная дата")
    public void cardInvalidDatesError(String card, String month, String year, String name, String cvv) {
        sendData(card, month, year, name, cvv);
        cardInvalidDatesError.shouldBe(visible);
    }

    @Step("Заполняем форму невалидными данными: карта просрочена")
    public void cardExpiredDatesError(String card, String month, String year, String name, String cvv) {
        sendData(card, month, year, name, cvv);
        cardExpiredDatesError.shouldBe(visible);
    }

    public void popUpNotification() {
        popUpNotification.shouldBe(visible, Duration.ofSeconds(13));
    }

}
