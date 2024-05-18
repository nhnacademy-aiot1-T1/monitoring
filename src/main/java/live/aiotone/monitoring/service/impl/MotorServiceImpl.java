package live.aiotone.monitoring.service.impl;

import java.util.List;
import live.aiotone.monitoring.domain.Motor;
import live.aiotone.monitoring.repository.MotorRepository;
import live.aiotone.monitoring.service.MotorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MotorService 구현체.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MotorServiceImpl implements MotorService {

  private final MotorRepository motorRepository;

  @Override
  public List<Motor> readMotorList() {
    return motorRepository.findAll();
  }
}
