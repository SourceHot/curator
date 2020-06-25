# curator_retry 重试机制
> author: [huifer](https://github.com/huifer)
>
> git_repo : [curator](https://github.com/SourceHot/curator)
>

## RetryPolicy
- 类路径: `org.apache.curator.RetryPolicy`
    - 该接口定义了一个方法 **是否重试** 
    
    
```java
public interface RetryPolicy {
    /**
     * 是否可以重试
     *
     * @param retryCount    重试次数
     * @param elapsedTimeMs the elapsed time in ms since the operation was attempted
     * @param sleeper       睡眠接口
     *                      
     */
    boolean allowRetry(int retryCount, long elapsedTimeMs, RetrySleeper sleeper);

    /**
     * 默认的是否可以重试.判断方式
     *
     * @param exception the cause that this operation failed
     * @return true/false
     */
    default boolean allowRetry(Throwable exception) {
        if (exception instanceof KeeperException) {
            final int rc = ((KeeperException) exception).code().intValue();
            return (rc == KeeperException.Code.CONNECTIONLOSS.intValue()) ||
                    (rc == KeeperException.Code.OPERATIONTIMEOUT.intValue()) ||
                    (rc == KeeperException.Code.SESSIONMOVED.intValue()) ||
                    (rc == KeeperException.Code.SESSIONEXPIRED.intValue());
        }
        return false;
    }
}
```


## RetrySleeper
- 类路径: `org.apache.curator.RetrySleeper`

```java
public interface RetrySleeper {
    /**
     * Sleep for the given time
     * <p>
     * 睡眠多久
     *
     * @param time time 时间
     * @param unit time unit 单位
     * @throws InterruptedException if the sleep is interrupted
     */
    void sleepFor(long time, TimeUnit unit) throws InterruptedException;
}
```


## ExponentialBackoffRetry
- 类路径: `org.apache.curator.retry.ExponentialBackoffRetry`
- 基本属性 

```java
    /**
     * 最大重试次数
     */
    private static final int MAX_RETRIES_LIMIT = 29;
    /**
     * 最大睡眠时间
     */
    private static final int DEFAULT_MAX_SLEEP_MS = Integer.MAX_VALUE;

    /**
     * 随机
     */
    private final Random random = new Random();
    /**
     * 基础睡眠时间
     */
    private final int baseSleepTimeMs;
    /**
     * 最大睡眠时间
     */
    private final int maxSleepMs;
```

- 构造方法 

```java
public ExponentialBackoffRetry(int baseSleepTimeMs, int maxRetries, int maxSleepMs) {
        super(validateMaxRetries(maxRetries));
        this.baseSleepTimeMs = baseSleepTimeMs;
        this.maxSleepMs = maxSleepMs;
    }
```

- 验证

```java
private static int validateMaxRetries(int maxRetries) {
        if (maxRetries > MAX_RETRIES_LIMIT) {
            log.warn(String.format("maxRetries too large (%d). Pinning to %d", maxRetries, MAX_RETRIES_LIMIT));
            maxRetries = MAX_RETRIES_LIMIT;
        }
        return maxRetries;
    }
```

