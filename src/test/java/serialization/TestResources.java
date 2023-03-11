package serialization;

import deserialize.resourcesDeSerialize;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestResources {

    Response res;
    resourcesDeSerialize resources;

    @Test(priority = 0)
    public void TestResourcesDataBasic(){
        res = given().when().get("https://reqres.in/api/unknown");

        resources = res.as(resourcesDeSerialize.class);

        Assert.assertEquals(resources.getPage(), 1);
        Assert.assertEquals(resources.getPer_page(), 6);
        Assert.assertEquals(resources.getTotal(), 12);
        Assert.assertEquals(resources.getTotal_pages(), 2);


    }

    @Test(dependsOnMethods = "TestResourcesDataBasic")
    public void TestResourcesDataIDAndYear(){
        for (int i=0;i<6;i++){
            Assert.assertEquals(resources.getData().get(i).getId(), i+1);
            Assert.assertEquals(resources.getData().get(i).getYear(),2000+i);
        }
    }

    @Test(dependsOnMethods = "TestResourcesDataBasic")
    public void testResourcesColor(){
        Assert.assertEquals(resources.getData().get(0).getColor(), "#98B2D1");
    }
}
