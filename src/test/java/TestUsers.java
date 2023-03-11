import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestUsers {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("validate the data of users is correct")
    @Link("www.google.com")
    public void testGetListUsers(){

        Response usersResponse = given().when().get("https://reqres.in/api/users");
        usersResponse.prettyPrint();
        Assert.assertEquals(usersResponse.statusCode(),200);
        Assert.assertEquals(usersResponse.getBody().path("page").toString(), "1");
        Assert.assertEquals(usersResponse.getBody().path("per_page").toString(), "6");
        Assert.assertEquals(usersResponse.getBody().path("data.id[0]").toString(), "1");
        Assert.assertEquals(usersResponse.getBody().path("data.email[0]").toString(), "george.bluth@reqres.in");
        Assert.assertEquals(usersResponse.getBody().path("data.first_name[0]").toString(), "George");
        Assert.assertEquals(usersResponse.getBody().path("data.last_name[0]").toString(), "Bluth");
        Assert.assertEquals(usersResponse.getBody().path("data.avatar[0]").toString(), "https://reqres.in/img/faces/1-image.jpg");

        Assert.assertEquals(usersResponse.getBody().path("support.url").toString(), "https://reqres.in/#support-heading");
        Assert.assertEquals(usersResponse.getBody().path("support.text").toString(), "To keep ReqRes free, contributions towards server costs are appreciated!");
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    public void testCreateUser(){
        String bodyData = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        Response createUser = given().contentType(ContentType.JSON).
                body(bodyData.toString()).when()
                .post("https://reqres.in/api/users");
        createUser.prettyPrint();
        Assert.assertEquals(createUser.statusCode(),200);
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    public void pathParameters(){
        Response res = given().pathParam("id", 1)
                .when().get("https://fakerestapi.azurewebsites.net/api/v1/Users/{id}");
        res.prettyPrint();
        System.out.println(res.headers());
        Assert.assertEquals(res.header("Server"), "Kestrel");
        Assert.assertEquals(res.header("Content-Type"), "application/json; charset=utf-8; v=1.0");
    }
}
