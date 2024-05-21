package live.aiotone.monitoring.service;

import java.util.List;
import live.aiotone.monitoring.common.exception.sector.SectorNotFoundException;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.domain.SectorOverView;

/**
 * Sector 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
 */
public interface SectorService {

  /**
   * Sector 목록 조회.
   *
   * @return Sector 목록 Sector 가 없을 경우 빈 List 를  반환
   */
  List<Sector> readSectorList();

  /**
   * Sector 이름으로 Sector 생성.
   *
   * @param sectorName Sector 이름
   * @return 생성된 Sector
   */
  Sector createSector(String sectorName);

  /**
   * Sector 의 이름을 수정.
   *
   * @param sectorId   수정할 Sector ID
   * @param sectorName 수정할 Sector 이름
   * @return 수정된 Sector
   * @throws SectorNotFoundException Sector 가 존재하지 않을 경우
   */
  Sector updateSectorName(Long sectorId, String sectorName);

  /**
   * Sector Id 로 Sector 삭제.
   *
   * @param sectorId Sector ID
   * @throws SectorNotFoundException Sector 가 존재하지 않을 경우
   */
  void deleteSectorById(Long sectorId);


  /**
   * SectorOverView 목록 조회.
   *
   * @return SectorOverView 목록
   */
  List<SectorOverView> readSectorOverviewList();
}
