package com.topic.scheduler.quartz;

import com.topic.scheduler.quartz.jobs.PrintToLogJob;
import com.topic.scheduler.quartz.triggers.EveryXSecondsTrigger;
import jakarta.annotation.PostConstruct;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;

import static org.quartz.JobBuilder.newJob;

//Убрал запуск - пока не надо
// @Component
public class SchedulerImpl {

    @Autowired
    Scheduler scheduler;

    @PostConstruct
    void init(){
        try {

            // все некрасиво, но просто проверить, что работает
            JobDetail job = newJob(PrintToLogJob.class)
                    .withIdentity("job1", "group1")
                    .build();
            Trigger trigger = EveryXSecondsTrigger.get(1, "t1", "g1");
            scheduler.scheduleJob(job, trigger);


        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

}
