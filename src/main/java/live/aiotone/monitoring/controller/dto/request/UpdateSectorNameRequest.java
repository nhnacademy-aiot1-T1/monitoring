package live.aiotone.monitoring.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class UpdateSectorNameRequest {

    private final String sectorName;
    public String getSectorName() {
        return sectorName;
    }
}
