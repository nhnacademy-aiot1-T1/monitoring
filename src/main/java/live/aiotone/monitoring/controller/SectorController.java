package live.aiotone.monitoring.controller;

import com.nhnacademy.common.dto.CommonResponse;
import java.util.List;
import java.util.stream.Collectors;
import live.aiotone.monitoring.controller.dto.SectorDto;
import live.aiotone.monitoring.controller.dto.mapper.SectorMapper;
import live.aiotone.monitoring.controller.response.ReadSectorListResponse;
import live.aiotone.monitoring.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/monitor/sectors")
@RequiredArgsConstructor
public class SectorController {

  private final SectorService sectorService;
  private final SectorMapper sectorMapper;

  @GetMapping
  public CommonResponse<ReadSectorListResponse> readSectorList() {
    List<SectorDto> sectorDtoList = sectorService.readSectorList().stream()
        .map(sectorMapper::toDto)
        .collect(Collectors.toList());
    ReadSectorListResponse response = new ReadSectorListResponse(sectorDtoList);
    return CommonResponse.success(response);
  }

}
