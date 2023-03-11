import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestAPIs {

    @Test
    public void testLogin(){
        String loginBody = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";
        Response loginToken = given().contentType(ContentType.JSON).body(loginBody)
                .when().post("https://reqres.in/api/login");
        loginToken.prettyPrint();
        Assert.assertEquals(loginToken.statusCode(), 200);
        Assert.assertEquals(loginToken.body().path("token"),"QpwL5tke4Pnpja7X4");
        Assert.assertEquals(loginToken.header("Server"), "cloudflare");

        System.out.println(loginToken.headers());

    }

    @Test
    public void testCreateUser(){

        String createUserBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        Response createUserResponse = given().contentType(ContentType.JSON).body(createUserBody)
                .when().post("https://reqres.in/api/users");

        Assert.assertEquals(createUserResponse.statusCode(),201);
        Assert.assertEquals(createUserResponse.body().path("name"),"morpheus");
        Assert.assertEquals(createUserResponse.body().path("job"),"leader");
        String id = createUserResponse.body().path("id");
        System.out.println(id);
        createUserResponse.prettyPrint();
    }

    @Test
    public void testGetUsersList(){
        Response usersList = given().when().get("https://reqres.in/api/users?page=2");
        usersList.prettyPrint();
        Assert.assertEquals(usersList.statusCode(),200);
        Assert.assertEquals(usersList.body().path("page").toString(), "2");
        Assert.assertEquals(usersList.body().path("per_page").toString(), "6");
        Assert.assertEquals(usersList.body().path("total").toString(), "12");
        Assert.assertEquals(usersList.body().path("total_pages").toString(), "2");

        Assert.assertEquals(usersList.body().path("data.id[0]").toString(),"7");
        Assert.assertEquals(usersList.body().path("data.email[0]"),"michael.lawson@reqres.in");
        Assert.assertEquals(usersList.body().path("data.first_name[0]"),"Michael");

    }

    @Test
    public void testGetActivities(){
        String id = "12";
        Response activities = given().pathParam("id", 1)
                .when().get("https://fakerestapi.azurewebsites.net/api/v1/Activities/{id}");
        Assert.assertEquals(activities.statusCode(),200);
        activities.prettyPrint();
    }

}
