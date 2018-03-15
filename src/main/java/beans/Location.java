package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class Location {

    @SerializedName("lat")
    @Expose
    private double latitude;

    @SerializedName("lng")
    @Expose
    private double longitude;
}
