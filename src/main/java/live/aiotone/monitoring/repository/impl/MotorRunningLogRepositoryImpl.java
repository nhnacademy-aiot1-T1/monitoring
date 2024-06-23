package live.aiotone.monitoring.repository.impl;

import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import java.time.LocalDateTime;
import java.util.List;
import live.aiotone.monitoring.controller.dto.MotorRunningRateDto;
import live.aiotone.monitoring.controller.dto.QMotorRunningRateDto;
import live.aiotone.monitoring.domain.MotorRunningLog;
import live.aiotone.monitoring.domain.MotorRunningLog.Duration;
import live.aiotone.monitoring.domain.QMotorRunningLog;
import live.aiotone.monitoring.repository.MotorRunningLogRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * MotorRunningLog Querydsl 로 동적 쿼리를 만들기 위한 RepositoryCustom 구현체.
 */

public class MotorRunningLogRepositoryImpl extends QuerydslRepositorySupport
    implements MotorRunningLogRepositoryCustom {

  private static final QMotorRunningLog qMotorRunningLog = QMotorRunningLog.motorRunningLog;

  public MotorRunningLogRepositoryImpl() {
    super(MotorRunningLog.class);
  }

  @Override
  public List<MotorRunningRateDto> readMotorRunningRate(LocalDateTime currentTime,
      Duration duration) {
    final String dateFormat = duration.getFormat();
    final int durationHours = duration.getHours();

    StringExpression formattedDate = dateFormatExpression(qMotorRunningLog.createdAt, dateFormat);
    LocalDateTime startTime = currentTime.minusHours(durationHours);

    return from(qMotorRunningLog)
        .select(
            new QMotorRunningRateDto(
                formattedDate,
                runningRateExpression())
        )
        .where(qMotorRunningLog.createdAt.after(startTime))
        .groupBy(formattedDate)
        .orderBy(formattedDate.asc())
        .fetch();
  }

  @Override
  public List<MotorRunningRateDto> readMotorRunningRateById(Long motorId, LocalDateTime currentTime,
      Duration duration) {

    final String dateFormat = duration.getFormat();
    final int durationHours = duration.getHours();

    StringExpression formattedDate = dateFormatExpression(qMotorRunningLog.createdAt, dateFormat);
    LocalDateTime startTime = currentTime.minusHours(durationHours);

    return from(qMotorRunningLog)
        .select(
            new QMotorRunningRateDto(
                formattedDate,
                runningRateExpression())
        )
        .where(qMotorRunningLog.createdAt.after(startTime)
            .and(qMotorRunningLog.motor.id.eq(motorId)))
        .groupBy(formattedDate)
        .orderBy(formattedDate.asc())
        .fetch();
  }


  private StringExpression dateFormatExpression(DateTimePath<LocalDateTime> dateTimePath,
      String format) {
    return Expressions.stringTemplate("DATE_FORMAT({0}, {1})", dateTimePath,
        Expressions.constant(format));
  }

  private NumberExpression<Double> runningRateExpression() {
    int trueValue = 1;
    int falseValue = 0;
    int multiplyValue = 100;
    return Expressions.cases()
        .when(qMotorRunningLog.on.isTrue()).then(trueValue)
        .otherwise(falseValue)
        .avg()
        .multiply(multiplyValue);
  }
}
