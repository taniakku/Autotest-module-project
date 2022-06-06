package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.DELETE;

public class PayCredit {
    private SelenideElement creditButton = $(byText("Купить в кредит"));
    private SelenideElement header = $(byText("Кредит по данным карты"));

    public PayCredit buyCreditTour() {
        creditButton.click();
        return new PayCredit();
    }

    public PayCredit() {
        header.shouldBe(Condition.visible);
    }
}
