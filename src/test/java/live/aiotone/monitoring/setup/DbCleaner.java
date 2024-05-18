package live.aiotone.monitoring.setup;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DbCleaner{

  @PersistenceContext
  private EntityManager entityManager;
  private List<String> tableNames;

  @PostConstruct
  public void init() {
    tableNames = entityManager.getMetamodel().getEntities().stream()
        .filter(entity -> entity.getJavaType().getAnnotation(javax.persistence.Entity.class) != null)
        .map(entityType -> {
          Table tableAnnotation = entityType.getJavaType().getAnnotation(Table.class);
          if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
          }
          return entityType.getName();
        })
        .collect(Collectors.toList());
  }

  @SuppressWarnings("SqlNoDataSourceInspection")
  @Transactional
  public void databaseClean() {
    entityManager.flush();
    // fk 제약 비활성화
    entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
    for (String tableName : tableNames) {
      // 테이블 초기화
      entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
      // 테이블 id 값 초기화
      entityManager.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1")
          .executeUpdate();
    }
    // fk 제약 활성화
    entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
  }
}
