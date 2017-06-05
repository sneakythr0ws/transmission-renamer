
package org.nick.utils.transmissionrenamer.domain.transmission;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "arguments",
    "result"
})
public class Response {

    @JsonProperty("arguments")
    private Arguments arguments;
    @JsonProperty("result")
    private String result;

}
