import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;


public class TaskSchedulingTest {
    private static final ThreadGroup TRIGGER_THREAD_GROUP = new ThreadGroup("Triggers");
    public static ScheduledThreadPoolExecutor timerService;

    public static void main(String[] args) {
        ThreadFactory timerThreadFactory = new DispatcherThreadFactory(TRIGGER_THREAD_GROUP, "Triggers");
        timerService = new ScheduledThreadPoolExecutor(1, timerThreadFactory);
        timerService.setRemoveOnCancelPolicy(true);
        // tasks should be removed if the future is canceled
        timerService.setRemoveOnCancelPolicy(true);

        // make sure shutdown removes all pending tasks
        timerService.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        timerService.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);

        AtomicInteger scheduledTasks = new AtomicInteger(0);

        for (int i = 0; i < 150000; i++) {
            ScheduledFuture<?> future = timerService.schedule(() -> {
                scheduledTasks.incrementAndGet();
            }, 0, TimeUnit.MILLISECONDS);
            future.cancel(false);
        }

        System.out.println("Total scheduled tasks: " + scheduledTasks.get());
        timerService.shutdown();
    }
}