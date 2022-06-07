package in.co.gorest.userinfo;

import in.co.gorest.constants.EndPoints;
import in.co.gorest.model.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class UserSteps {
    @Step("Creating User with name : {0}, gender: {1}, email: {2}, status: {3} ")
    public ValidatableResponse createUser(String name, String gender, String email,
                                          String status ) {
        UserPojo userPojo= new UserPojo();
        userPojo.setName(name);
        userPojo.setGender(gender);
        userPojo.setEmail(email);
        userPojo.setStatus(status);


        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer c426452f777927f6e49219f45652a5fd08178e3f873af217a5b982a6fdd15dac")
                .body(userPojo)
                .when()
                .post(EndPoints.CREATE_SINGLE_USERS)
                .then();

    }

    @Step("Getting the user information with name: {0}")
    public HashMap<Object, Object> getUserInfoByID(int userID) {

        HashMap<Object, Object> userMap = SerenityRest.given().log().all()
                .when()
                .pathParam("userID",userID)
                .get(EndPoints.GET_SINGLE_USERS_BY_ID)
                .then()
                .statusCode(200)
                .extract()
                .path("");

        return userMap;
    }

    @Step("Updating User information with usertId:  {0}, name: {1}, gender: {2}, email: {3} status : {4}")
    public ValidatableResponse updateUser(int userID, String name, String gender, String email,String status){

        UserPojo userPojo= new UserPojo();
        userPojo.setName(name);
        userPojo.setStatus(gender);
        userPojo.setEmail(email);
        userPojo.setStatus(status);



        return SerenityRest.given().log().all()
                .pathParam("userID",userID)
                .contentType(ContentType.JSON)
                .body(userPojo)
                .when()
                .put(EndPoints.GET_SINGLE_USERS_BY_ID)
                .then();

    }

    @Step("Deleting user information with userID: {0}")
    public ValidatableResponse deleteUser(int userID){
        return SerenityRest.given().log().all()
                .pathParam("userID", userID)
                .when()
                .delete(EndPoints.DELETE_USERS_BY_ID)
                .then();
    }

    @Step("Getting user information with userId: {0}")
    public ValidatableResponse getusertById(int userID){
        return SerenityRest.given().log().all()
                .pathParam("userID", userID)
                .when()
                .get(EndPoints.GET_SINGLE_USERS_BY_ID)
                .then();
    }
}
