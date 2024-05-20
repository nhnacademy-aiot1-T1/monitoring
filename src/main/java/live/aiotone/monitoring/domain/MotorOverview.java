package live.aiotone.monitoring.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Factory Overview 도메인 클래스.
 */
@Getter
@Builder
@RequiredArgsConstructor
public class MotorOverview {

  /**
   * 총 모터 개수.
   */
  @NonNull
  private final Integer totalMotorCount;
  /**
   * 가동 중인 모터 개수.
   */
  @NonNull
  private final Integer totalOnMotorCount;
  /**
   * 정상 작동 중인 모터 개수.
   */
  @NonNull
  private final Integer totalNormalMotorCount;

  /**
   * 모터 리스트를 받아서 FactoryOverview 객체를 생성합니다.
   *
   * @param motorList 모터 리스트
   * @return FactoryOverview
   */
  public static MotorOverview createOverview(List<Motor> motorList) {
    int totalMotorCount = motorList.size();
    int totalOnMotorCount = 0;
    int totalNormalMotorCount = 0;

    for (Motor motor : motorList) {
      if (motor.isOn()) {
        totalOnMotorCount++;
      }
      if (motor.isNormal()) {
        totalNormalMotorCount++;
      }
    }

    return MotorOverview.builder()
        .totalMotorCount(totalMotorCount)
        .totalOnMotorCount(totalOnMotorCount)
        .totalNormalMotorCount(totalNormalMotorCount)
        .build();
  }
}

