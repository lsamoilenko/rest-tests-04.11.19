import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;


public class CreatePetTest {

    private PetEndpoint petEndpoint = new PetEndpoint();

    private long petId;
    private String body = "{\n" +
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

    @Before
    public void beforeMethod() {

        ValidatableResponse response = petEndpoint
                .createPet(body)
                .statusCode(is(200))
                .body("category.name", is(not("")));
        petId = response.extract().body().path("id");
        }

    @Test
    public void createPetTest() {
        petEndpoint
                .createPet(body)
                .statusCode(is(200))
                .body("category.name", is(not("")));
        }

    @Test
    public void getPedById(){
        petEndpoint
                .getPet(petId)
                .statusCode(is(200))
                .body("category.name", is(not("")))
                .log().all();
     }

    @Test
    public void deletePedById(){
        petEndpoint
                .deletePet(petId)
                .statusCode(is(200));

        petEndpoint
                .getPet(petId)
                .statusCode(is(404))
                .body("message", is("Pet not found"));
    }
}
