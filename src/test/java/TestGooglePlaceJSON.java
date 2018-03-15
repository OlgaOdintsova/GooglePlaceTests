import beans.GooglePlaceAnswer;
import beans.Status;
import core.GooglePlaceApi;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGooglePlaceJSON {

    private static final String API_KEY = readFromProperty();

    private static String readFromProperty() {
        try {
            Properties api = new Properties();
            api.load(TestGooglePlaceJSON.class.getResourceAsStream("api.properties"));
            return api.getProperty("key");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void testZeroRadius() {
        GooglePlaceAnswer answer = GooglePlaceApi.getGooglePlaceAnswer(GooglePlaceApi.with()
                                                                                     .key(API_KEY)
                                                                                     .location(0, 0)
                                                                                     .radius(0)
                                                                                     .get());

        assertThat("Status is wrong", answer.getStatus(), equalTo(Status.INVALID_REQUEST));
    }

    @Test
    public void testCorrectRequestWithoutResults() {
        GooglePlaceAnswer answer = GooglePlaceApi.getGooglePlaceAnswer(GooglePlaceApi.with()
                                                                                     .key(API_KEY)
                                                                                     .location(0, 0)
                                                                                     .radius(1)
                                                                                     .get());

        assertThat("Status is wrong", answer.getStatus(), equalTo(Status.ZERO_RESULTS));
    }

    @Test
    public void testCorrectRequestWithResults() {
        double latitudeKremlin = 55.754;
        double longitudeKremlin = 37.6207;

        GooglePlaceAnswer answer = GooglePlaceApi.getGooglePlaceAnswer(GooglePlaceApi.with()
                                                                                     .key(API_KEY)
                                                                                     .location(latitudeKremlin, longitudeKremlin)
                                                                                     .radius(500)
                                                                                     .get());

        assertThat("Status is wrong", answer.getStatus(), equalTo(Status.OK));
        assertThat("It isn't Moscow!", answer.getResults().get(0).getName(), equalTo("Moscow"));
        assertThat("It isn't Kremlin!", answer.getResults().get(1).getName(), equalTo("The Moscow Kremlin"));
    }
}
