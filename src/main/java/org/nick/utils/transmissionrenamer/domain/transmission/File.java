
package org.nick.utils.transmissionrenamer.domain.transmission;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "bytesCompleted",
        "length",
        "title"
})
public class File {

    @JsonProperty("bytesCompleted")
    private Long bytesCompleted;
    @JsonProperty("length")
    private Long length;
    @JsonProperty("name")
    private String name;

}
