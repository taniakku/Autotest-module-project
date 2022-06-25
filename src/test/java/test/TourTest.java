package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import data.DataGenerator;
import page.StartTourPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;

public class TourTest {
    StartTourPage startPage;

    @BeforeAll
    static void setupAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void browserSetUp() {
        open("http://localhost:8080/");
        startPage = new StartTourPage();
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Позитивные сценарии")
    @Story("Активная карта, валидные данные")
    @Description("Заполнение и отправка формы с действующей картой и валидными данными")
    void shouldPayForTheTourWithValidCard() {
        var debitPaymentPage = startPage.debitBuy();
        var approvedPayment = DataGenerator.cardApproved();
        debitPaymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getOwner(), approvedPayment.getAuthCode());

    }

    @Test
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Позитивные сценарии")
    @Story("Активная карта, граничные значения")
    @Description("Действующая карта и валидные данные: текущий месяц")
    void shouldPayForTheTourWithValidCardCurrentMonth() {
        var debitPaymentPage = startPage.debitBuy();
        var approvedPayment = DataGenerator.cardApprovedThisMonthThisYear();
        debitPaymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getOwner(), approvedPayment.getAuthCode());

    }

    @Test
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта, граничные значения")
    @Description("Действующая карта и невалидные данные: прошлый месяц")
    void shouldPayForTheTourWithValidCardPreviousMonth() {
        var debitPaymentPage = startPage.debitBuy();
        var approvedPayment = DataGenerator.cardApprovedPreviousMonthThisYear();
        debitPaymentPage.cardInvalidDatesError(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getOwner(), approvedPayment.getAuthCode());

    }

    @Test
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Позитивные сценарии")
    @Story("Активная карта, граничные значения")
    @Description("Действующая карта и невалидные данные: следующий месяц")
    void shouldPayForTheTourWithValidCardNextMonth() {
        var debitPaymentPage = startPage.debitBuy();
        var approvedPayment = DataGenerator.cardApprovedNextMonthThisYear();
        debitPaymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getOwner(), approvedPayment.getAuthCode());

    }

    @Test
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Позитивные сценарии")
    @Story("Неактивная карта")
    @Description("Заполнение и отправка формы с недействующей картой")
    void shouldPayForTheTourWithInvalidCard() {
        var debitPaymentPage = startPage.debitBuy(); //тут баг - банк должен отказать, т.к. карта должна быть отклонена
        var declinedPayment = DataGenerator.cardDeclined();
        debitPaymentPage.unsuccessfulSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(), declinedPayment.getYear(),
                declinedPayment.getOwner(), declinedPayment.getAuthCode());
    }


    @Test
    @Epic("Переключение вкладок на странице покупки тура")
    @Feature("Позитивные сценарии")
    @Story("Навигация в приложении")
    @Description("Переключение на страницу оплаты тура в кредит и обратно")
    void shouldSwitchPages() {
        var debitPaymentPage = startPage.debitBuy();
        var creditPage = startPage.creditBuy();
        var newPaymentPage = startPage.debitBuy();
    }

    @Test
    @Epic("Покупка по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта, заполнение данных")
    @Description("Заполнение и отправка формы с именем на кириллице")
    void shouldPayForTheTourInvalidDataName() { 
        var debitPaymentPage = startPage.debitBuy();
        var declinedPaymentBecauseOfName = DataGenerator.anyCardInvalidName();
        debitPaymentPage.formatError(declinedPaymentBecauseOfName.getCardNumber(), declinedPaymentBecauseOfName.getMonth(), declinedPaymentBecauseOfName.getYear(),
                declinedPaymentBecauseOfName.getOwner(), declinedPaymentBecauseOfName.getAuthCode());

    }

    @Test
    @Epic("Покупка по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта, заполнение данных")
    @Description("Заполнение и отправка формы с именем из цифр + больше 30 символов")
    void shouldPayForTheTourInvalidDataNameWithDigits() { 
        var debitPaymentPage = startPage.debitBuy();
        var declinedPaymentBecauseOfName = DataGenerator.anyCardInvalidNameWithDigits();
        debitPaymentPage.formatError(declinedPaymentBecauseOfName.getCardNumber(), declinedPaymentBecauseOfName.getMonth(), declinedPaymentBecauseOfName.getYear(),
                declinedPaymentBecauseOfName.getOwner(), declinedPaymentBecauseOfName.getAuthCode());

    }

    @Test
    @Epic("Покупка по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта, заполнение данных")
    @Description("Заполнение и отправка формы с несуществующими месяцем и годом")
    void shouldPayForTheTourInvalidDataMonthYear() {
        var debitPaymentPage = startPage.debitBuy();
        var declinedPaymentBecauseOfDates = DataGenerator.anyCardInvalidMonthYear();
        debitPaymentPage.cardInvalidDatesError(declinedPaymentBecauseOfDates.getCardNumber(), declinedPaymentBecauseOfDates.getMonth(), declinedPaymentBecauseOfDates.getYear(),
                declinedPaymentBecauseOfDates.getOwner(), declinedPaymentBecauseOfDates.getAuthCode());

    }

    @Test
    @Epic("Покупка по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта, заполнение данных")
    @Description("Заполнение и отправка формы с активной, но просроченной картой: прошлый год")
    void shouldPayForTheTourWithExpiredCard() {
        var debitPaymentPage = startPage.debitBuy();
        var declinedPaymentBecauseOfExpired = DataGenerator.anyCardExpiredYear();
        debitPaymentPage.cardExpiredDatesError(declinedPaymentBecauseOfExpired.getCardNumber(), declinedPaymentBecauseOfExpired.getMonth(), declinedPaymentBecauseOfExpired.getYear(),
                declinedPaymentBecauseOfExpired.getOwner(), declinedPaymentBecauseOfExpired.getAuthCode());

    }

    @Test
    @Epic("Покупка по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Отсутствие данных в форме")
    @Description("Отправка пустой формы")
    void shouldSendEmptyForm() {
        var debitPaymentPage = startPage.debitBuy();
        var declinedPaymentBecauseOfEmptyForm = DataGenerator.empty();
        debitPaymentPage.emptyFieldError(declinedPaymentBecauseOfEmptyForm.getCardNumber(), declinedPaymentBecauseOfEmptyForm.getMonth(), declinedPaymentBecauseOfEmptyForm.getYear(),
                declinedPaymentBecauseOfEmptyForm.getOwner(), declinedPaymentBecauseOfEmptyForm.getAuthCode());

    }


}
