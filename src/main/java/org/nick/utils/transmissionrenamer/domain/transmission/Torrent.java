
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
        "comment",
        "downloadDir",
        "error",
        "errorString",
        "files",
        "id",
        "title",
        "status"
})
public class Torrent {

    @JsonProperty("comment")
    private String comment;
    @JsonProperty("downloadDir")
    private String downloadDir;
    @JsonProperty("error")
    private Long error;
    @JsonProperty("errorString")
    private String errorString;
    @JsonProperty("files")
    private List<File> files = new ArrayList<>(0);
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String name;
    @JsonProperty("status")
    private Long status;

}
