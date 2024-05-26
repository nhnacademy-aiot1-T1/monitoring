package live.aiotone.monitoring.domain;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 모터의 가동률.
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "motor_running_log")
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @QueryProjection)
@Builder
public class MotorRunningLog {

  @Id
  @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
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

    DAY("%Y-%m-%d %H:00:00", 24),
    WEEK("%Y-%m-%d 00:00:00", 168),
    MONTH("%Y-%m-%d 00:00:00", 720);
    final String format;
    final int hours;

    Duration(String format, int hours) {
      this.format = format;
      this.hours = hours;
    }
  }

}
