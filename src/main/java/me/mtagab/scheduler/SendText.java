package me.mtagab.scheduler;

import lombok.extern.slf4j.Slf4j;
import me.mtagab.service.TextService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SendText extends QuartzJobBean {
    private ApplicationContext applicationContext;

    @Autowired
    TextService textService;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("job executed by {}", applicationContext.getBean(Environment.class));
    }
}
