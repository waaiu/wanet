package com.waaiu.net.common.kit.concurrent.timer.delay;

import com.waaiu.net.common.kit.RandomKit;
import com.waaiu.net.common.kit.concurrent.TaskListener;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * DelayTaskTest
 *
 * @author
 * @date 2024-09-01
 * @since 25.1
 */
@Slf4j
class DelayTaskTest {
    static DelayTaskRegion delayTaskRegion;

    @BeforeEach
    public void setUp() {
        DelayTaskKit.setDelayTaskRegion(new DebugDelayTaskRegion());
        delayTaskRegion = DelayTaskKit.delayTaskRegion;
    }

    @AfterEach
    public void tearDown() throws Exception {
        TimeUnit.SECONDS.sleep(3);
        log.info("-------- {}", delayTaskRegion.count());
    }

    @Test
    public void runDelayTask() {
        log.info(" - ");

        long timeMillis = System.currentTimeMillis();
        // 1
        DelayTaskKit.of(() -> {
            log.info("1 ");
            long value = System.currentTimeMillis() - timeMillis;
            Assertions.assertTrue(value > 990);
        })
                // N
                .plusTime(Duration.ofSeconds(1))
                //
                .task();
    }

    @Test
    public void plusDelayTime() {
        // --------------- - ---------------
        log.info(" - ");

        long timeMillis = System.currentTimeMillis();
        // 1
        DelayTask delayTask = DelayTaskKit.of(() -> {
            long value = System.currentTimeMillis() - timeMillis;
            log.info("， {} ms ，", value);
            Assertions.assertTrue(value > 1490);
        })
                // N
                .plusTime(Duration.ofSeconds(1))
                //
                .task();

        // 0.5
        delayTask.plusTimeMillis(500);
        log.info("{}", delayTask);
        // 1.5
    }

    @Test
    public void minusDelayTime() {
        // --------------- - ---------------
        log.info(" - ");

        long timeMillis = System.currentTimeMillis();
        // 1
        DelayTask delayTask = DelayTaskKit.of(() -> {
            long value = System.currentTimeMillis() - timeMillis;
            log.info("， {} ms ，", value);

            Assertions.assertTrue(value < 510);
        })
                // N
                .plusTime(Duration.ofSeconds(1))
                //
                .task();

        // 0.5
        delayTask.minusTime(Duration.ofMillis(100))
                .plusTimeMillis(-400);

        log.info("{}", delayTask);
        // 0.5
    }

    @Test
    public void coverDelayTask() throws InterruptedException {
        log.info(" - ");

        String taskId = "1";

        DelayTaskKit.of(taskId, () -> log.info(" - 1"))
                // N
                .plusTime(Duration.ofSeconds(2))
                //
                .task();

        TimeUnit.MILLISECONDS.sleep(500);

        long timeMillis = System.currentTimeMillis();

        // taskId ，
        DelayTask delayTask = DelayTaskKit.of(taskId, () -> {
            long value = System.currentTimeMillis() - timeMillis;

            log.info(" - 2， {} ms ，", value);

            Assertions.assertTrue(value > 990);
        })
                // N
                .plusTime(Duration.ofSeconds(1))
                //
                .task();

        log.info("{}", delayTask);
    }

    @Test
    public void cancelDelayTask() throws InterruptedException {
        // ----------- - ； DelayTask -----------
        log.info(" - ");

        DelayTask delayTask = DelayTaskKit.of(() -> {
            log.info(" - ");
        })
                // N
                .plusTime(Duration.ofSeconds(2))
                //
                .task();

        Assertions.assertEquals(1, delayTaskRegion.count());

        log.info("0.5 , , ");
        TimeUnit.MILLISECONDS.sleep(500);
        //
        delayTask.cancel();

        Assertions.assertFalse(delayTask.isActive());
        Assertions.assertEquals(0, delayTaskRegion.count());

        // ----------- - ； taskId -----------

        log.info(" -  taskId ");

        String taskId = "1";
        // ， taskId
        DelayTaskKit.of(taskId, () -> log.info(" taskId  - "))
                // N
                .plusTime(Duration.ofSeconds(1))
                //
                .task();

        Assertions.assertEquals(1, delayTaskRegion.count());

        log.info("0.5 , , ");
        TimeUnit.MILLISECONDS.sleep(500);
        // taskId
        DelayTaskKit.cancel(taskId);

        Assertions.assertEquals(0, delayTaskRegion.count());
    }

    @Test
    public void optionalDelayTask() {
        String newTaskId = "1";
        DelayTaskKit.of(newTaskId, () -> log.info("hello DelayTask"))
                // 2.5
                .plusTime(Duration.ofSeconds(2))
                .plusTimeMillis(500)
                //
                .task();

        // ， taskId
        Optional<DelayTask> optionalDelayTask = DelayTaskKit.optional(newTaskId);
        optionalDelayTask.ifPresent(delayTask -> log.info("{}", delayTask));

        // taskId ，
        DelayTaskKit.ifPresent(newTaskId, delayTask -> {
            delayTask.plusTimeMillis(500); // 0.5
        });
    }

    @Test
    public void customTaskListener() {
        // minus
        // --------------- - TaskListener ---------------

        DelayTaskKit.of(new TaskListener() {
            @Override
            public void onUpdate() {
                log.info("1.7 ");
            }

            @Override
            public boolean triggerUpdate() {
                // onUpdate
                return TaskListener.super.triggerUpdate();
            }

            @Override
            public Executor getExecutor() {
                // onUpdate
                return TaskListener.super.getExecutor();
            }

            @Override
            public void onException(Throwable e) {
                //
                TaskListener.super.onException(e);
            }
        })
                .plusTime(Duration.ofMillis(1700))
                .task();
    }

    @Test
    public void more() {
        DelayTask delayTask = DelayTaskKit.of(new ShootTaskListener(""))
                // 1.5 。（）
                .plusTime(Duration.ofSeconds(1))
                .plusTimeMillis(500)
                //
                .task();

        // true ， 0.5 ，
        if (RandomKit.randomBoolean()) {
            delayTask.minusTime(Duration.ofMillis(500));
            // ，
            ShootTaskListener shootTaskListener = delayTask.getTaskListener();
            shootTaskListener.setLuck(true);
        }
    }

    @Slf4j
    @Setter
    static final class ShootTaskListener implements TaskListener {
        final String targetEntity;
        /**  */
        boolean luck;
        int attack = 10;

        public ShootTaskListener(String targetEntity) {
            this.targetEntity = targetEntity;
        }

        @Override
        public void onUpdate() {
            int value = luck ? attack * 2 : attack;
            log.info("【{}】， {} ", targetEntity, value);
        }
    }
}
