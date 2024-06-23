package live.aiotone.monitoring.common.exception.sector;

import live.aiotone.monitoring.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * 존재하지 않는 Sector 예외.
 */
public final class SectorNotFoundException extends BusinessException {

  /**
   * Sector ID를 받아서 SectorNotFoundException 생성.
   *
   * @param sectorId Sector ID
   */
  public SectorNotFoundException(Long sectorId) {
    super("존재하지 않는 Sector 입니다. : " + sectorId);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.NOT_FOUND;
  }
}
