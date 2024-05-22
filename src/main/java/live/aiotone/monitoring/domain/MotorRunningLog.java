package live.aiotone.monitoring.domain;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 모터의 가동률.
 */
@Getter
@Entity
@ToString(exclude = "motor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "motor_running_log")
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @QueryProjection)
@Builder
public class MotorRunningLog {

  @Id
  private Long id;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "motor_id")
  private Motor motor;

  @Column(name = "is_on")
  private boolean on;

  /**
   * 가동률 조회 기간.
   */
  @Getter
  public enum Duration {

    DAY("%Y-%m-%dT%H:00:00", 24),
    WEEK("%Y-%m-%dT00:00:00", 168),
    MONTH("%Y-%m", 720);
    final String format;
    final int hours;

    Duration(String format, int hours) {
      this.format = format;
      this.hours = hours;
    }
  }

}
