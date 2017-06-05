package org.nick.utils.transmissionrenamer.parsers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@AllArgsConstructor
public class TorrentPage {
    private String name;
    private String img;
}
