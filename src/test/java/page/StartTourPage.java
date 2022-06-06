package page;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;

public class StartTourPage {

    private SelenideElement header = $(byText("Путешествие дня"));
    private SelenideElement debitButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    public StartTourPage() {
        header.shouldBe(visible);
    }

    public PayDebit debitBuy() {
        debitButton.click();
        return new PayDebit();
    }

    public PayCredit creditBuy() {
        creditButton.click();
        return new PayCredit();
    }


}
