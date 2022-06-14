package data;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.Value;

import java.time.LocalDate;
import java.time.Year;
import java.util.Locale;


public class DataGenerator {


    private DataGenerator() {

    }

    private static final String cardFirstApproved = "1111 2222 3333 4444";
    private static final String cardSecondDeclined = "5555 6666 7777 8888";

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String owner;
        private String authCode;
    }

    @Step("Генерация данных юзера с валидной картой: карта нескоро просрочится")
    public static CardInfo cardApproved() {
        return new CardInfo(cardFirstApproved, month(5), year(2),
                generateValidName(), generateAuthCode());
    }

    @Step("Генерация данных юзера с валидной картой: текущий месяц-год")
    public static CardInfo cardApprovedThisMonthThisYear() {
        return new CardInfo(cardFirstApproved, month(0), year(0),
                generateValidName(), generateAuthCode());
    }

    @Step("Генерация данных юзера с валидной картой: следующий месяц")
    public static CardInfo cardApprovedNextMonthThisYear() {
        return new CardInfo(cardFirstApproved, month(1), year(0),
                generateValidName(), generateAuthCode());
    }

    @Step("Генерация данных юзера с валидной картой: прошлый месяц")
    public static CardInfo cardApprovedPreviousMonthThisYear() {
        return new CardInfo(cardFirstApproved, month(-1), year(0),
                generateValidName(), generateAuthCode());
    }

    @Step("Генерация данных юзера с невалидной картой")
    public static CardInfo cardDeclined() {
        return new CardInfo(cardSecondDeclined, month(0), year(0),
                generateValidName(), generateAuthCode());
    }

    @Step("Генерация данных юзера с кириллическим именем")
    public static CardInfo anyCardInvalidName() {
        return new CardInfo(cardFirstApproved, month(0), year(0),
                generateInvalidName(), generateAuthCode());
    }

    @Step("Генерация данных юзера с именем из цифр")
    public static CardInfo anyCardInvalidNameWithDigits() {
        return new CardInfo(cardFirstApproved, month(0), year(0),
                generateInvalidNameWithDigits(), generateAuthCode());
    }

    @Step("Генерация данных юзера с несуществующей датой")
    public static CardInfo anyCardInvalidMonthYear() {
        return new CardInfo(cardFirstApproved, invalidMonth(), year(55),
                generateValidName(), generateAuthCode());
    }

    @Step("Генерация данных юзера с просроченной картой")
    public static CardInfo anyCardExpiredYear() {
        return new CardInfo(cardFirstApproved, month(0), year(-1),
                generateValidName(), generateAuthCode());
    }

    @Step("Генерация пустого заполнения формы")
    public static CardInfo empty() {
        return new CardInfo("", "", "",
                "", "");
    }

    public static String month(int value) {
        LocalDate date = LocalDate.now();
        return String.format("%tm", date.plusMonths(value));
    }

    public static String invalidMonth() {
        Faker randomTwoDigits = new Faker();
        return randomTwoDigits.number().digits(2);
    }

    public static String year(int value) {
        return String.format("%ty", Year.now().plusYears(value));
    }

    public static String generateValidName() {
        Faker faker = new Faker((new Locale("en")));
        String name = faker.name().fullName();
        return name;
    }

    public static String generateInvalidName() {
        Faker faker = new Faker((new Locale("ru")));
        String name = faker.name().fullName();
        return name;
    }

    public static String generateInvalidNameWithDigits() {
        Faker name = new Faker();
        return name.number().digits(50);
    }

    public static String generateAuthCode() {
        Faker cvv = new Faker();
        return cvv.number().digits(3);
    }


}
