package com.waaiu.net.common.kit.concurrent;

import com.waaiu.net.common.kit.RandomKit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author
 * @date 2023-12-02
 * @since 25.1
 */
@Slf4j
class TaskKitTest {
    @AfterEach
    public void tearDown() throws Exception {
        TimeUnit.SECONDS.sleep(3);
    }

    @BeforeEach
    void setUp() {
        TaskKit.executeVirtual(() -> {
        });
    }

    @Test
    public void execute() {
        TaskKit.execute(() -> {
            log.info("CacheThreadPool consumer task");
        });

        TaskKit.executeVirtual(() -> {
            log.info("Virtual consumer task");
        });
    }

    @Test
    public void runOnce() {

        // ，2
        TaskKit.runOnce(() -> log.info("2 Seconds"), 2, TimeUnit.SECONDS);
        // ，1
        TaskKit.runOnce(() -> log.info("1 Minute"), 1, TimeUnit.MINUTES);
        // ，500 milliseconds
        TaskKit.runOnce(() -> log.info("500 delayMilliseconds"), 500, TimeUnit.MILLISECONDS);

        // ，1500 Milliseconds， theTriggerUpdate true ， onUpdate
        boolean theTriggerUpdate = RandomKit.randomBoolean();
        TaskKit.runOnce(new OnceTaskListener() {
            @Override
            public void onUpdate() {
                log.info("1500 delayMilliseconds");
            }

            @Override
            public boolean triggerUpdate() {
                return theTriggerUpdate;
            }

        }, 1500, TimeUnit.MILLISECONDS);
    }

    @Test
    public void runInterval() {
        // 2
        TaskKit.runInterval(() -> log.info("tick 2 Seconds"), 2, TimeUnit.SECONDS);
        // 30
        TaskKit.runInterval(() -> log.info("tick 30 Minute"), 30, TimeUnit.MINUTES);

        // 【 - 】， hp 0 Listener
        TaskKit.runInterval(new IntervalTaskListener() {
            int hp = 2;

            @Override
            public void onUpdate() {
                hp--;
                log.info(" hp:2-{}", hp);
            }

            @Override
            public boolean isActive() {
                // false ， Listener
                return hp != 0;
            }
        }, 1, TimeUnit.SECONDS);

        // 【 - 】， triggerUpdate true， onUpdate
        TaskKit.runInterval(new IntervalTaskListener() {
            int hp;

            @Override
            public void onUpdate() {
                log.info("current hp:{}", hp);
            }

            @Override
            public boolean triggerUpdate() {
                hp++;
                // true ， onUpdate
                return hp % 2 == 0;
            }
        }, 1, TimeUnit.SECONDS);

        // 【 - 】
        // ， io ，（onUpdate ），。
        ExecutorService executorService = TaskKit.getCacheExecutor();

        TaskKit.runInterval(new IntervalTaskListener() {
            @Override
            public void onUpdate() {
                log.info(" IO ，");

                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                log.info(" IO ，");
            }

            @Override
            public Executor getExecutor() {
                // （onUpdate ），。
                return executorService;
            }
        }, 1, TimeUnit.SECONDS);
    }

    @Test
    public void testException() throws InterruptedException {
        AtomicBoolean hasEx = new AtomicBoolean(false);
        TaskKit.runOnce(new OnceTaskListener() {
            @Override
            public void onUpdate() {
                throw new RuntimeException("hello exception");
            }

            @Override
            public void onException(Throwable e) {
                hasEx.set(true);
            }
        }, 10, TimeUnit.MILLISECONDS);

        TimeUnit.MILLISECONDS.sleep(200);
        Assertions.assertTrue(hasEx.get());
    }

    @Test
    public void example() {

        TaskKit.runOnceSecond(() -> {
        });

        TaskKit.newTimeout(_ -> {

        }, 1, TimeUnit.SECONDS);

        TaskKit.execute(() -> {
        });

        TaskKit.runInterval(() -> {

        }, 1, TimeUnit.SECONDS);

        TaskKit.runIntervalMinute(() -> {

        }, 1);
    }
}
