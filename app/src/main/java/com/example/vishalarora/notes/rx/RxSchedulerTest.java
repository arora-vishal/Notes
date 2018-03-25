package com.example.vishalarora.notes.rx;

import io.reactivex.Scheduler;
import io.reactivex.internal.schedulers.ImmediateThinScheduler;

/**
 * Created by vishalarora on 25/03/18.
 */

public class RxSchedulerTest extends RxSchedulerAbs {

    @Override
    public Scheduler getMainThreadScheduler() {
        return ImmediateThinScheduler.INSTANCE;
    }

    @Override
    public Scheduler getIOScheduler() {
        return ImmediateThinScheduler.INSTANCE;
    }

    @Override
    public Scheduler getComputationScheduler() {
        return ImmediateThinScheduler.INSTANCE;
    }

}