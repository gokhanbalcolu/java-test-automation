package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ⏱ hızlı mod
    private static final int STEP_SLEEP_MS = 500;          // 0.5 sn
    private static final int PAGE_WAIT_SEC  = 5;           // max 5 sn bekle

    /* ===================== LOCATORS ===================== */

    private final By loginLink =
            By.cssSelector("a[data-qa-id='layout-header-user-srogon'], a[href*='/tr/tr/logon']");

    private final By emailInput =
            By.cssSelector("input[name='username'][type='email'], input[type='email'][name*='user']");

    private final By passwordInput =
            By.cssSelector("input[data-qa-input-qualifier='password'], input[type='password'][name='password']");

    private final By invalidEmailText =
            By.cssSelector("div.field-error__text");

    private final By submitBtnPrimary =
            By.cssSelector("button[data-qa-id='login-form-submit']");

    private final By submitBtnByText =
            By.xpath("//button[@type='submit' and normalize-space()='Oturum aç']");

    private final By alertDialogTitle =
            By.cssSelector("h1.zds-alert-dialog__title");

    private final By alertDialogOkButton =
            By.cssSelector("button[data-qa-id='zds-alert-dialog-accept-button']");

    private final By backdrop =
            By.cssSelector("div.zds-backdrop[data-hidden='false']");

    private final By zaraLogo =
            By.cssSelector("a.hlp-header__logo-link");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_WAIT_SEC));
    }

    /* ===================== NAVIGATION ===================== */

    public void goToLoginPage() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        click(link);

        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        sleepStep(); // küçük geçiş
        System.out.println("✅ Login sayfasına gidildi");
    }

    /* ===================== FORM ACTIONS ===================== */

    public void fillEmail(String email) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        el.clear();
        el.sendKeys(email);
        // burada ekstra sleep yok (hız)
    }

    public void clearEmail() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        el.sendKeys(Keys.CONTROL + "a");
        el.sendKeys(Keys.DELETE);
    }

    public void fillPassword(String password) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        el.clear();
        el.sendKeys(password);

        // imleç password'da takılı kalmasın + validasyon tetiklensin
        el.sendKeys(Keys.TAB);
    }

    private void blurToTriggerValidation() {
        WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        pass.click(); // blur/focus
    }

    /* ===================== FLOWS ===================== */

    // 1) Format hatası doğrula
    public String verifyInvalidEmailFormat(String invalidEmail) {
        fillEmail(invalidEmail);
        blurToTriggerValidation();

        return wait.until(ExpectedConditions.visibilityOfElementLocated(invalidEmailText))
                .getText().trim();
    }

    // 2) Dummy creds -> popup başlığı doğrula + OK ile kapat
    public String verifyInvalidCredentialsPopup(String validEmail, String dummyPassword) {
        fillEmail(validEmail);
        fillPassword(dummyPassword);

        clickLoginSubmit();

        String title = wait.until(ExpectedConditions.visibilityOfElementLocated(alertDialogTitle))
                .getText().trim();

        WebElement ok = wait.until(ExpectedConditions.elementToBeClickable(alertDialogOkButton));
        click(ok);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(alertDialogTitle));
        sleepStep();
        return title;
    }

    /* ===================== SUBMIT ===================== */

    private void clickLoginSubmit() {
        WebElement submit;
        try {
            submit = wait.until(ExpectedConditions.elementToBeClickable(submitBtnPrimary));
        } catch (TimeoutException e) {
            submit = wait.until(ExpectedConditions.elementToBeClickable(submitBtnByText));
        }

        scrollToCenter(submit);

        // backdrop varsa max 1 sn bekle, yoksa anında geç
        waitBackdropFast();

        sleepStep(); // tek küçük bekleme
        click(submit);
    }

    /* ===================== HOME RETURN ===================== */

    public void clickLogoToReturnHome() {
        WebElement logo = wait.until(ExpectedConditions.presenceOfElementLocated(zaraLogo));
        scrollToCenter(logo);

        waitBackdropFast();
        sleepStep();
        click(logo);

        wait.until(ExpectedConditions.urlContains("/tr"));
        sleepStep();
        System.out.println("🏠 Ana sayfaya dönüldü");
    }

    /* ===================== HELPERS ===================== */

    private void click(WebElement el) {
        try {
            el.click();
        } catch (WebDriverException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    private void scrollToCenter(WebElement el) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el
        );
    }

    private void waitBackdropFast() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(1))
                    .until(ExpectedConditions.invisibilityOfElementLocated(backdrop));
        } catch (TimeoutException ignored) {
        }
    }

    private void sleepStep() {
        try {
            Thread.sleep(STEP_SLEEP_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread sleep interrupted", e);
        }
    }
}
