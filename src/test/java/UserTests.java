import Steps.ReqRes.UserSteps;
import org.testng.annotations.Test;

public class UserTests {

    @Test(description = "task 5")
    public void VerifyPageTwoUsers() {
        new UserSteps()
                .verifySecondPageUsers();
    }
}