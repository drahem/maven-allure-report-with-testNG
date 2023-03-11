import deserialize.usersListDeSerialize;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import serialization.createUserSer;

import java.io.File;

import static io.restassured.RestAssured.given;

public class TestAPIsAdvanced {

    @Test
    public void TestSendFile(){

        File jsonRequest =  new File(System.getProperty("user.dir")+"/resources/request1.json");
        Response createRes = given().when().body(jsonRequest).post("https://reqres.in/api/users");
        createRes.prettyPrint();
        Assert.assertEquals(createRes.statusCode(), 201);
    }

    @DataProvider(name = "createUserData")
    public Object[][] data1(){
        String[][] data = {{"name1", "job1"},{"name2", "job2"},{"name3", "job3"},{"name4", "job4"}};
        return data;
    }

    @Test(dataProvider = "createUserData")
    public void TestCreateUserWithSerialization(String name, String job){
        createUserSer body = new createUserSer(name, job);
        Response createRes = given().when().contentType(ContentType.JSON).body(body)
                .post("https://reqres.in/api/users");
        createRes.prettyPrint();
        Assert.assertEquals(createRes.statusCode(),201);
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
    public void TestDeSerialization(){
        Response res = given().when().get("https://reqres.in/api/users?page=2");

        usersListDeSerialize responseData = res.as(usersListDeSerialize.class);

        SoftAssert softassert = new SoftAssert();
        for (int i=0;i<6;i++) {
            softassert.assertEquals(responseData.getData().get(i).getId(), i+7, "IDs do not match...");
        }
        softassert.assertAll();
    }
}
