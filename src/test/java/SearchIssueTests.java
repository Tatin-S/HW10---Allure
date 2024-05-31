import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.*;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class SearchIssueTests extends TestBase{

    @Test
    public void searchIssueListenerTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("");
        $(".search-input").click();
        $("#query-builder-test").setValue(REPOSITORY).pressEnter();
        $(linkText(REPOSITORY)).click();
        $("#issues-tab").click();
        $("#issue_"+ NUMBER).should(Condition.exist).shouldHave(text(TITLE));
        takeScreenshot();
    }

    @Test
    public void searchIssueLambdaStep() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу", () -> {
            open("");
        });
        step("Ищем репозиторий " + REPOSITORY, () -> {
            $(".search-input").click();
            $("#query-builder-test").setValue(REPOSITORY).pressEnter();
        });
        step("Переходим в репозиторий" + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Переходим по табу Issues", () -> {
            $("#issues-tab").click();
        });
        step("Проверяем наличие Issue с номером " + NUMBER + " и названием " + TITLE, () -> {
            $("#issue_" + NUMBER).should(Condition.exist).shouldHave(text(TITLE));
        });
        takeScreenshot();
    }

    @Test
    void searchIssueAnnotatedStepsTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();
        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithNumberAndTitle(NUMBER, TITLE);
        takeScreenshot();
    }
}
