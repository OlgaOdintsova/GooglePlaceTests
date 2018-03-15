package core;

import beans.GooglePlaceAnswer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.lessThan;

public class GooglePlaceApi {

    private static final String URI = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
    private Map<Param, String> params = new HashMap<>();

    private GooglePlaceApi() {}

    public static class Builder {
        GooglePlaceApi api;

        private Builder(GooglePlaceApi api) {
            this.api = api;
        }

        public Builder key(String key) {
            api.params.put(Param.KEY, key);
            return this;
        }

        public Builder location(double latitude, double longitude) {
            api.params.put(Param.LOCATION, latitude + "," + longitude);
            return this;
        }

        public Builder radius(int radius) {
            api.params.put(Param.RADIUS, String.valueOf(radius));
            return this;
        }

        public Builder lang(Language language) {
            api.params.put(Param.LANGUAGE, language.getLanguageCode());
            return this;
        }

        public Response get() {
            return RestAssured.with()
                              .queryParams(api.params.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue)))
                              .log().all()
                              .get(URI)
                              .prettyPeek();
        }
    }

    public static Builder with() {
        return new Builder(new GooglePlaceApi());
    }

    public static GooglePlaceAnswer getGooglePlaceAnswer(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<GooglePlaceAnswer>(){}.getType());
    }

    public static ResponseSpecification successResponse(){
        return new ResponseSpecBuilder().expectContentType(ContentType.JSON)
                                        .expectResponseTime(lessThan(20_000L))
                                        .expectStatusCode(HttpStatus.SC_OK)
                                        .build();
    }
}
