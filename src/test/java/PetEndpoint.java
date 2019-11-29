import data.Pet;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class PetEndpoint {
    public final static String CREATE_PET = "/pet";
    public final static String GET_PET = "/pet/{petId}";
    public final static String DELETE_PET = "/pet/{petId}";
    public final static String GET_PET_BY_STATUS = "/pet/findByStatus";
    public final static String UPDATE_PET = "/pet";
    public final static String UPDATE_PET_BY_ID = "/pet/{petId}";

    static {
        RestAssured.filters(new RequestLoggingFilter(LogDetail.ALL));
        RestAssured.filters(new RequestLoggingFilter(LogDetail.ALL));
    }

    public RequestSpecification given() {
        return RestAssured
                .given()
                .baseUri("https://petstore.swagger.io/v2")
                .contentType(ContentType.JSON)
                .log().all();
    }

    public ValidatableResponse createPet(Pet pet) {
        return given()
                .body(pet)
                .post(CREATE_PET)
                .then();
    }

    public ValidatableResponse getPet(long petId) {
        return given()
                .get(GET_PET, petId)
                .then();
    }

    public ValidatableResponse deletePet(long petId) {
        return given()
                .delete(DELETE_PET, petId)
                .then();
    }


    public ValidatableResponse getPetByStatus (String status) {
        return given()
                .queryParam("status", status)
                .get(GET_PET_BY_STATUS)
                .then();
    }


    public ValidatableResponse updatePet(String body) {
        return given()
                .body(body)
                .put(UPDATE_PET)
                .then();
    }

    public ValidatableResponse updatePetById(long petId, String petName, String petStatus) {
        return given()
                .contentType(ContentType.URLENC)
                .formParam("name",petName)
                .formParam("status",petStatus)
                .post(UPDATE_PET_BY_ID, petId)
                .then();
    }

}