package live.aiotone.monitoring.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import live.aiotone.monitoring.common.exception.MotorNotFoundException;
import live.aiotone.monitoring.common.exception.sector.SectorNotFoundException;
import live.aiotone.monitoring.common.holder.ClockHolder;
import live.aiotone.monitoring.controller.dto.MotorRunningRateDto;
import live.aiotone.monitoring.controller.dto.response.MotorDetailResponse;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.domain.MotorRunningLog.Duration;
import live.aiotone.monitoring.domain.Sector;
import live.aiotone.monitoring.repository.MotorRepository;
import live.aiotone.monitoring.repository.MotorRunningLogRepository;
import live.aiotone.monitoring.repository.SectorRepository;
import live.aiotone.monitoring.service.MotorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MotorService 구현체.
 */
@Service
@Slf4j
@Transactional(readOnly = true)
@EnableCaching
@RequiredArgsConstructor
public class MotorServiceImpl implements MotorService {

  private final SectorRepository sectorRepository;

  private final MotorRepository motorRepository;
  private final MotorRunningLogRepository motorRunningLogRepository;
  private final ClockHolder clockHolder;

  @Override
  public List<Motor> readMotorList() {
    return motorRepository.findAll();
  }

  @Override
  @Cacheable("motorRunningRate")
  public List<MotorRunningRateDto> readMotorRunningRate(Duration duration) {
    LocalDateTime currentTime = LocalDateTime.now(clockHolder.getClock());
    return motorRunningLogRepository.readMotorRunningRate(currentTime, duration);
  }

  @Override
  public List<MotorRunningRateDto> readMotorRunningRateById(Long motorId, Duration duration) {
    LocalDateTime currentTime = LocalDateTime.now(clockHolder.getClock());
    return motorRunningLogRepository.readMotorRunningRateById(motorId, currentTime, duration);
  }

  @Override
  @Transactional
  public void updateMotorSector(Long motorId, Long sectorId) {
    Sector updateSector = sectorRepository.findById(sectorId).orElseThrow(() -> new SectorNotFoundException(sectorId));
    Motor motor = motorRepository.findById(motorId).orElseThrow(() -> new MotorNotFoundException(motorId));
    motor.updateSector(updateSector);
  }

  @Override
  public MotorDetailResponse getMotorDetail(Long motorId) {

    Motor motor = motorRepository.findById(motorId)
        .orElseThrow(() -> new MotorNotFoundException(motorId));

    List<MotorDetailResponse.Sensor> sensors = motor.getSensors().stream()
        .map(sensor -> MotorDetailResponse.Sensor.builder()
            .sensorId(sensor.getId())
            .sensorType(sensor.getSensorName())
            .build())
        .collect(Collectors.toList());

    return MotorDetailResponse.builder()
        .motorId(motor.getId())
        .motorName(motor.getMotorName())
        .sectorId(motor.getSector().getId())
        .sectorName(motor.getSector().getSectorName())
        .on(motor.isOn())
        .normal(motor.isNormal())
        .sensors(sensors)
        .build();
  }

  @CacheEvict(value = "motorRunningRate", allEntries = true)
  @Scheduled(cron = "0 0 0 * * ?")
  public void evictAllCacheValues() {
    log.info("가동률 캐시 삭제");
  }


}



