package com.topic.scheduler.quartz.triggers;

import org.quartz.Trigger;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class EveryXSecondsTrigger {

    public static Trigger get(int x, String identity, String group){

        return newTrigger()
                .withIdentity(identity, group)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(x)
                        .repeatForever())
                .build();
    }

}
