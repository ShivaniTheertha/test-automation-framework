package com.orangehrm.utils;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestStepFinished;

public class CucumberEventListener implements ConcurrentEventListener {


    // Stores exception from any failed step — ThreadLocal for thread safety
    public static final ThreadLocal<Throwable> stepException =
            new ThreadLocal<>();

    // Stores the failed step name
    public static final ThreadLocal<String> failedStepName =
            new ThreadLocal<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        // Listen to every step finishing
        publisher.registerHandlerFor(
                TestStepFinished.class,
                this::handleStepFinished
        );
    }

    private void handleStepFinished(TestStepFinished event) {
        Result result = event.getResult();

        // Only process failed steps
        if (result.getStatus() == Status.FAILED) {

            // Store the actual exception
            if (result.getError() != null) {
                stepException.set(result.getError());
            }

            // Store the step name if available
            if (event.getTestStep() instanceof
                    io.cucumber.plugin.event.PickleStepTestStep) {
                io.cucumber.plugin.event.PickleStepTestStep step =
                        (io.cucumber.plugin.event.PickleStepTestStep)
                                event.getTestStep();
                failedStepName.set(step.getStep().getText());
            }
        }

/*        // Clear on pass
        if (result.getStatus() == Status.PASSED) {
            stepException.remove();
            failedStepName.remove();
        }*/
    }
}
