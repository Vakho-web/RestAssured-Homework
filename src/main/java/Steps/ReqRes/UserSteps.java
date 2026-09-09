package Steps.ReqRes;

import Calls.ReqRes.UserCalls;
import Models.ReqRes.UserModel;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class UserSteps {

    UserCalls userCalls = new UserCalls();

    // Task 5
    public UserSteps verifySecondPageUsers() {
        Response response = userCalls.getUsers(2);
        Assert.assertEquals(response.getStatusCode(), 200);

        List<UserModel> users = response.jsonPath().getList("data", UserModel.class);

        for (UserModel user : users) {
            Assert.assertTrue(user.email.contains("@"));
            Assert.assertFalse(user.first_name.isEmpty());
            Assert.assertFalse(user.last_name.isEmpty());
        }
        return this;
    }
}
