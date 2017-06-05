
package org.nick.utils.transmissionrenamer.domain.transmission;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "torrents"
})
public class Arguments {

    @JsonProperty("torrents")
    private List<Torrent> torrents = new ArrayList<>(0);

}
