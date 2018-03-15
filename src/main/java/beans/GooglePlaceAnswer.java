package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
public class GooglePlaceAnswer {

    @SerializedName("html_attributions")
    @Expose
    private List<String> htmlAttributions;

    @SerializedName("results")
    @Expose
    private List<Place> results;

    @SerializedName("status")
    @Expose
    private Status status;
}
