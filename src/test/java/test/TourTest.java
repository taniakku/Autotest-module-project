package test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import data.DataGenerator;
import page.StartTourPage;

import static com.codeborne.selenide.Selenide.open;

public class TourTest {
    StartTourPage startPage;

    @BeforeEach
    void browserSetUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        startPage = new StartTourPage();
    }

    @Test
        //тест с валидной картой
    void shouldPayForTheTourWithValidCard() {
        var debitPaymentPage = startPage.debitBuy();
        var approvedPayment = DataGenerator.cardApproved();
        debitPaymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getOwner(), approvedPayment.getAuthCode());

    }

    @Test
        //тест с граничными значениями - текущий месяц валидной карты
    void shouldPayForTheTourWithValidCardCurrentMonth() {
        var debitPaymentPage = startPage.debitBuy();
        var approvedPayment = DataGenerator.cardApprovedThisMonthThisYear();
        debitPaymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getOwner(), approvedPayment.getAuthCode());

    }

    @Test
        //тест с граничными значениями - прошлый месяц валидной карты
    void shouldPayForTheTourWithValidCardPreviousMonth() {
        var debitPaymentPage = startPage.debitBuy();
        var approvedPayment = DataGenerator.cardApprovedPreviousMonthThisYear();
        debitPaymentPage.unsuccessfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getOwner(), approvedPayment.getAuthCode());

    }

    @Test
        //тест с граничными значениями - следующий месяц валидной карты
    void shouldPayForTheTourWithValidCardNextMonth() {
        var debitPaymentPage = startPage.debitBuy();
        var approvedPayment = DataGenerator.cardApprovedNextMonthThisYear();
        debitPaymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getOwner(), approvedPayment.getAuthCode());

    }

    @Test
        //тест с невалидной картой
    void shouldPayForTheTourWithInvalidCard() {
        var debitPaymentPage = startPage.debitBuy(); //тут баг - банк должен отказать, т.к. карта должна быть отклонена
        var declinedPayment = DataGenerator.cardDeclined();
        debitPaymentPage.unsuccessfulSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(), declinedPayment.getYear(),
                declinedPayment.getOwner(), declinedPayment.getAuthCode());
    }

    @Test
        //переключение страниц формы
    void shouldSwitchPages() {
        var debitPaymentPage = startPage.debitBuy();
        var creditPage = startPage.creditBuy();
        var newPaymentPage = startPage.debitBuy();
    }

    @Test
        //тест с валидной картой и невалидным именем
    void shouldPayForTheTourInvalidDataName() { //тут баг - можно отправить форму с именем на кириллице, сообщения о неверном формате нет
        var debitPaymentPage = startPage.debitBuy();
        var declinedPaymentBecauseOfName = DataGenerator.anyCardInvalidName();
        debitPaymentPage.formatError(declinedPaymentBecauseOfName.getCardNumber(), declinedPaymentBecauseOfName.getMonth(), declinedPaymentBecauseOfName.getYear(),
                declinedPaymentBecauseOfName.getOwner(), declinedPaymentBecauseOfName.getAuthCode());

    }

    @Test
        //тест с валидной картой и невалидными датами
    void shouldPayForTheTourInvalidDataMonthYear() { //тест с невалидной датой карты (абсурдная дата)
        var debitPaymentPage = startPage.debitBuy();
        var declinedPaymentBecauseOfDates = DataGenerator.anyCardInvalidMonthYear();
        debitPaymentPage.cardInvalidDatesError(declinedPaymentBecauseOfDates.getCardNumber(), declinedPaymentBecauseOfDates.getMonth(), declinedPaymentBecauseOfDates.getYear(),
                declinedPaymentBecauseOfDates.getOwner(), declinedPaymentBecauseOfDates.getAuthCode());

    }

    @Test
        //тест с валидной картой и невалидными датами 2: протухшая карта
    void shouldPayForTheTourWithExpiredCard() { //тест с просроченной картой (прошлый год)
        var debitPaymentPage = startPage.debitBuy();
        var declinedPaymentBecauseOfExpired = DataGenerator.anyCardExpiredYear();
        debitPaymentPage.cardExpiredDatesError(declinedPaymentBecauseOfExpired.getCardNumber(), declinedPaymentBecauseOfExpired.getMonth(), declinedPaymentBecauseOfExpired.getYear(),
                declinedPaymentBecauseOfExpired.getOwner(), declinedPaymentBecauseOfExpired.getAuthCode());

    }

    @Test
        //тест с пустой формой
    void shouldSendEmptyForm() {
        var debitPaymentPage = startPage.debitBuy();
        var declinedPaymentBecauseOfEmptyForm = DataGenerator.empty();
        debitPaymentPage.emptyFieldError(declinedPaymentBecauseOfEmptyForm.getCardNumber(), declinedPaymentBecauseOfEmptyForm.getMonth(), declinedPaymentBecauseOfEmptyForm.getYear(),
                declinedPaymentBecauseOfEmptyForm.getOwner(), declinedPaymentBecauseOfEmptyForm.getAuthCode());

    }


}
