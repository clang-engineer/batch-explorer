# Quartz
## Job
- Job은 Quartz의 기본 인터페이스입니다.
- execute(JobExecutionContext context) 메서드를 구현해야 합니다. 이 메서드에서 실제 작업을 처리합니다.
- Spring과는 별개로, Quartz 자체에서 작업을 정의할 때 사용됩니다.
- Spring에서 Quartz 작업을 정의할 때는 Job 인터페이스를 구현하여 사용하기도 하지만, Spring에서는 이를 QuartzJobBean을 통해 확장하는 것이 일반적입니다.

```java
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Executing Quartz Job");
        // 실제 잡 로직 처리
    }
}
```

## QuartzJobBean
- QuartzJobBean은 Spring Quartz 통합에서 사용하는 Job의 구현체입니다. Spring에서 Quartz 작업을 사용할 때 QuartzJobBean을 상속하여 작업을 정의할 수 있습니다.
- QuartzJobBean은 Spring Framework에서 제공하는 추상 클래스입니다.
- Spring의 Job 인터페이스와의 통합을 위해 executeInternal(JobExecutionContext context) 메서드를 구현하도록 합니다.
- Spring의 JobDetail에 빈으로 등록된 Job을 자동으로 주입받을 수 있도록 도와줍니다.
- QuartzJobBean을 사용하면, Spring의 의존성 주입(예: @Autowired)을 이용할 수 있습니다.
- Job을 상속받는 대신, QuartzJobBean을 상속하여 스프링 환경에 맞게 잡을 쉽게 구성할 수 있습니다.

```java
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MyQuartzJob extends QuartzJobBean {

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private Job sampleJob;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    try {
      // 배치 잡 실행
      jobLauncher.run(sampleJob, new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
      System.out.println("Spring Batch job executed");
    } catch (Exception e) {
      throw new JobExecutionException("Failed to execute batch job", e);
    }
  }
}
```

## 주요 차이점
| 특징 | Job 인터페이스 | QuartzJobBean 클래스 |
|---|---|---|
| 기본 유형 | 인터페이스 | 추상 클래스 (QuartzJobBean을 상속해야 함) |
| 사용 목적 | Quartz에서 잡을 정의할 때 사용 | Spring에서 Quartz 작업을 정의할 때 사용 |
| 의존성 주입 | 의존성 주입을 직접 사용할 수 없음 | Spring의 의존성 주입을 사용할 수 있음 |
| 메서드 구현 | execute(JobExecutionContext context) 메서드를 구현해야 함 | executeInternal(JobExecutionContext context) 메서드를 구현해야 함 |
| Spring 통합 | Spring과의 통합이 별도로 필요 | Spring에서의 Quartz 작업 통합을 쉽게 처리할 수 있음 |
| Spring Quartz 사용 시의 편의성 | Spring에서 사용 시 별도의 설정이 필요 | Spring의 @Autowired 등을 활용하여 자동으로 빈 주입 가능 |
