package me.mtagab.scheduler;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.impl.JobDetailImpl;
import org.quartz.utils.Key;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

public class ScheduleTemplate {

    public static JobDetail createTextMessage(String to, String msg) {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setKey(new JobKey(to, Key.DEFAULT_GROUP));
        jobDetail.setDescription(msg);
        jobDetail.setJobClass(SendText.class);
        jobDetail.setDurability(true);
        return jobDetail;
    }

    public static Trigger sendText(String to, String msg) {
        return newTrigger().forJob(createTextMessage(to, msg))
                .withIdentity("1", Key.DEFAULT_GROUP)
                .withPriority(50).withSchedule(SimpleScheduleBuilder.repeatMinutelyForever())
                .startAt(Date.from(LocalDateTime.now().plusSeconds(3).atZone(ZoneId.systemDefault()).toInstant()))
                .build();
    }


}
