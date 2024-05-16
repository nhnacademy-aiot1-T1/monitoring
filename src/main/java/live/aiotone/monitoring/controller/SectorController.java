package live.aiotone.monitoring.controller;

import com.nhnacademy.common.dto.CommonResponse;
import java.util.List;
import javax.validation.Valid;
import live.aiotone.monitoring.controller.dto.SectorDto;
import live.aiotone.monitoring.controller.dto.mapper.SectorMapper;
import live.aiotone.monitoring.controller.dto.request.CreateSectorRequest;
import live.aiotone.monitoring.controller.dto.request.UpdateSectorNameRequest;
import live.aiotone.monitoring.controller.dto.response.ReadSectorListResponse;
import live.aiotone.monitoring.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
   * Sector 목록 조회 요청 핸들러 메서드.
   *
   * @return Sector 목록
   */
  @GetMapping
  public CommonResponse<ReadSectorListResponse> readSectorList() {
    List<SectorDto> sectorDtoList = sectorMapper.toDtoList(sectorService.readSectorList());
    ReadSectorListResponse data = ReadSectorListResponse.of(sectorDtoList);
    return CommonResponse.success(data);
  }

  /**
   * Sector 생성 요청 핸들러 메서드.
   *
   * @param createSectorRequest Sector 생성 요청
   * @return 생성된 Sector
   */
  @PostMapping
  public CommonResponse<SectorDto> createSector(
      @RequestBody @Valid CreateSectorRequest createSectorRequest) {
    String sectorName = createSectorRequest.getSectorName();
    SectorDto sectorDto = sectorMapper.toDto(sectorService.createSector(sectorName));
    return CommonResponse.success(sectorDto);
  }

  /**
   * Sector 이름 변경 요청 핸들러 메서드.
   *
   * @param sectorId                Sector ID
   * @param updateSectorNameRequest Sector 이름 변경 요청
   * @return 변경된 Sector
   */
  @PutMapping("/{sectorId}")
  public CommonResponse<SectorDto> updateSectorName(@PathVariable Long sectorId,
      @RequestBody @Valid UpdateSectorNameRequest updateSectorNameRequest) {
    String sectorName = updateSectorNameRequest.getSectorName();
    SectorDto sectorDto = sectorMapper.toDto(sectorService.updateSectorName(sectorId, sectorName));
    return CommonResponse.success(sectorDto);
  }


  /**
   * Sector 삭제 요청 처리 핸들러 메서드.
   *
   * @param sectorId Sector ID
   * @return 성공 시 비어있는 data
   */
  @DeleteMapping("/{sectorId}")
  public CommonResponse<Void> deleteSector(@PathVariable Long sectorId) {
    sectorService.deleteSectorById(sectorId);
    return CommonResponse.success(null, "Sector가 삭제되었습니다.");
  }

}
