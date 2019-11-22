import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreatePetTest {

    static long petId;

     public RequestSpecification given(){
      return RestAssured
              .given()
              .baseUri("https://petstore.swagger.io/v2")
              .log().all()
              .contentType(ContentType.JSON);
    }

    @Test
    public void test1createPet() {

        String body = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"new\"\n" +
                "  },\n" +
                "  \"name\": \"sloth\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        ValidatableResponse response = given()
                .body(body)
                .post(PetEndpoint.CREATE_PET)
                .then()
                .statusCode(is(200))
                .body("category.name", is(not("")))
                .log().all();
        petId = response.extract().body().path("id");
        System.out.println(petId);
    }

    @Test
    public void test2getPedById(){
        System.out.println(petId);
        given()
                .get(PetEndpoint.GET_PET, petId)
                .then()
                .statusCode(is(200))
                .body("category.name", is(not("")))
                .log().all();
    }

    @Test
    public void test3deletePedById(){
        System.out.println(petId);
                given()
                .delete(PetEndpoint.DELETE_PET, petId)
                .then()
                .statusCode(is(200))
                .log().all();
    }
}
