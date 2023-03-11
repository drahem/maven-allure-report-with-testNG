import deserialize.createUserResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import serialization.createRequest;

import java.io.File;

import static io.restassured.RestAssured.given;

public class TestAPIS2 {

    @Test
    public void testCreateUser(){
        String requestBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        Response createUserResponse = given().contentType(ContentType.JSON).body(requestBody)
                .when().post("https://reqres.in/api/users");

        createUserResponse.prettyPrint();

        createUserResponse resData = createUserResponse.as(deserialize.createUserResponse.class);


        Assert.assertEquals(createUserResponse.statusCode(),201);
        Assert.assertEquals(resData.getName(), "morpheus");
        Assert.assertEquals(resData.getJob(), "leader");
        Assert.assertEquals(createUserResponse.header("Content-Type"), "application/json; charset=utf-8");
    }

    @Test
    public void testLogin(){
        String body = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";
        Response loginResponse = given().contentType(ContentType.JSON).body(body).
                when().post("https://reqres.in/api/login");
        Assert.assertEquals(loginResponse.statusCode(), 200);
        Assert.assertEquals(loginResponse.body().path("token").toString().length(), 17);
        String token = loginResponse.body().path("token");
    }

    @Test
    public void testGetUsesList(){

        Response usersList = given().when().get("https://reqres.in/api/users?page=2");

        usersList.prettyPrint();
/*
        usersListResponse listData = usersList.as(usersListResponse.class);

        Assert.assertEquals(usersList.statusCode(),200);
        Assert.assertEquals(usersList.body().path("data.id").toString(),"[7, 8, 9, 10, 11, 12]");
        Assert.assertEquals(listData.getData().get(0).getId(),7);

 */
    }

    @Test
    public void testAPIWithParam(){

        Response activity = given().pathParam("id", 1).
                when().get("https://fakerestapi.azurewebsites.net/api/v1/Activities/{id}");
        activity.prettyPrint();
        Assert.assertEquals(activity.statusCode(),200);
    }

    //  send file
    @Test
    public void testSendFile(){
        // Creating a File instance
        File jsonDataInFile = new File(System.getProperty("user.dir")+"/resources/request1.json");

        Response res = given().when().contentType(ContentType.JSON).body(jsonDataInFile)
                .post("https://reqres.in/api/users");
        res.prettyPrint();

    }


    @DataProvider(name = "createData")
    public Object[][] data1(){
        String[][] data = {{"name1","job1"}, {"name2","job2"}, {"name3","job3"}, {"name4","job4"}};
        return data;
    }

    @Test(dataProvider = "createData")
    public void testPOJO(String name, String job){
        createRequest req = new createRequest(name, job);
        Response res = given().when().contentType(ContentType.JSON).body(req)
                .post("https://reqres.in/api/users");
        res.prettyPrint();
    }

}
