package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataGenerator;
import data.PostgreSQLHelper;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import page.StartTourPage;
import io.qameta.allure.selenide.AllureSelenide;

import static com.codeborne.selenide.Selenide.closeWindow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.codeborne.selenide.Selenide.open;

public class DataBaseTest {
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
    @Feature("БД не сохраняет номер банковской карты")
    @Story("Дефолтная карта в статусе approved")
    @Description("Банковская карта не хранится в БД, выдаётся пустое поле")
    void shouldNotSaveCardNumberAfterPayment() {
        var paymentPage = startPage.debitBuy();
        var approvedPayment = DataGenerator.cardApproved();
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(), approvedPayment.getOwner(), approvedPayment.getAuthCode());
        assertEquals("null", PostgreSQLHelper.getCardId());
    }


    @Test
    @Epic("Покупка тура по дебетовой карте")
    @Feature("БД сохраняет статус платежа")
    @Story("Дефолтная карта в статусе approved")
    @Description("БД сохраняет платёж по действующей карте как одобренный")
    void shouldSaveDataAboutApprovedCard() {
        var paymentPage = startPage.debitBuy();
        var approvedPayment = DataGenerator.cardApproved();
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(), approvedPayment.getOwner(), approvedPayment.getAuthCode());
        assertEquals("APPROVED", PostgreSQLHelper.getPaymentStatus());
    }

    @Test
    @Epic("Покупка тура по дебетовой карте")
    @Feature("БД сохраняет статус платежа")
    @Story("Дефолтная карта в статусе declined")
    @Description("БД сохраняет операцию по недействующей карте как отклонённую")
    void shouldSaveDataAboutDeclinedCard() {
        var paymentPage = startPage.debitBuy();
        var declinedPayment = DataGenerator.cardDeclined();
        paymentPage.unsuccessfulSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(), declinedPayment.getYear(), declinedPayment.getOwner(), declinedPayment.getAuthCode());
        paymentPage.popUpNotification();
        assertEquals("DECLINED", PostgreSQLHelper.getPaymentStatus());
    }

}
