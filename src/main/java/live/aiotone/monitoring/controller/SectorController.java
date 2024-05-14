package live.aiotone.monitoring.controller;

import com.nhnacademy.common.dto.CommonResponse;
import java.util.List;
import java.util.stream.Collectors;
import live.aiotone.monitoring.controller.dto.SectorDto;
import live.aiotone.monitoring.controller.dto.mapper.SectorMapper;
import live.aiotone.monitoring.controller.dto.request.CreateSectorRequest;
import live.aiotone.monitoring.controller.dto.request.UpdateSectorNameRequest;
import live.aiotone.monitoring.controller.dto.response.ReadSectorListResponse;
import live.aiotone.monitoring.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
* Sector Controller.
*/
@RestController
@RequestMapping("api/monitor/sectors")
@RequiredArgsConstructor
public class SectorController {

  private final SectorService sectorService;
  private final SectorMapper sectorMapper;

  /**
  * Sector 목록 조회.
   *
   * @return Sector 목록
  */
  @GetMapping
  public CommonResponse<ReadSectorListResponse> readSectorList() {
    List<SectorDto> sectorDtoList = sectorService.readSectorList().stream()
        .map(sectorMapper::toDto)
        .collect(Collectors.toList());
    ReadSectorListResponse data = new ReadSectorListResponse(sectorDtoList);
    return CommonResponse.success(data);
  }

  /**
   * Sector 생성.
   *
   * @param createSectorRequest Sector 생성 요청
   * @return 생성된 Sector
  */
  @PostMapping
  public CommonResponse<SectorDto> createSector(@RequestBody CreateSectorRequest createSectorRequest) {
    String sectorName = createSectorRequest.getSectorName();
    SectorDto sectorDto = sectorMapper.toDto(sectorService.createSector(sectorName));
    return CommonResponse.success(sectorDto);
  }

  @PutMapping("/{sectorId}")
  public CommonResponse<SectorDto> updateSectorName(@PathVariable Long sectorId, @RequestBody UpdateSectorNameRequest updateSectorNameRequest) {
    String sectorName = updateSectorNameRequest.getSectorName();
    SectorDto sectorDto = sectorMapper.toDto(sectorService.updateSectorName(sectorId, sectorName));
    return CommonResponse.success(sectorDto);
  }

}
