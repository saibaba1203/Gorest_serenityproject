package in.co.gorest.gorestinfo;

import in.co.gorest.testbase.TestBase;
import in.co.gorest.userinfo.UserSteps;
import in.co.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)
public class GorestCURDTestWithSteps extends TestBase {

    static String name = "Allasani Peddana" + TestUtils.getRandomValue();
    static String email = "allasani.peddana993@gmail.com" + TestUtils.getRandomValue();
    static String gender = "female" + TestUtils.getRandomValue();
    static String status = "active" + TestUtils.getRandomValue();



    static int userID;

    @Steps
    UserSteps userSteps;

    @Title("This will create a new user")
    @Test
    public void test001() {
        HashMap<Object, Object> userMap = new HashMap<>();
        userMap.put("storeId", 1);
        userMap.put("serviceId", 1);

        ValidatableResponse response = userSteps.createUser(name, gender, email, status );
        response.log().all().statusCode(201);
        userID = response.log().all().extract().path("id");
        System.out.println(userID);
    }

    @Title("Verify if the user was added to the application")
    @Test
    public void test002() {
        HashMap<Object, Object> uservarrify = userSteps.getUserInfoByID(userID);
        Assert.assertThat(uservarrify, hasValue(userID));
        System.out.println(uservarrify);
    }
    @Title("Update the user information and verify the updated information")
    @Test
    public void test003() {

        name = name + "_updated";
        HashMap<Object, Object> userMap = new HashMap<>();
        userMap.put("storeId", 4);
        userMap.put("serviceId", 3);

        userSteps.updateUser( userID,name,  gender,  email,status
        ) .log().all().statusCode(200);


        HashMap<Object, Object> userupdatemap = userSteps.getUserInfoByID(userID);
        Assert.assertThat(userupdatemap, hasValue(name));

    }
    @Title("Delete the user and verify if the user is deleted!")
    @Test
    public void test004() {
        userSteps.deleteUser(userID).statusCode(200);
        userSteps.getusertById(userID).statusCode(404);
    }



}
