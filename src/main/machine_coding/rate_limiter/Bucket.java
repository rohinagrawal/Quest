package rate_limiter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bucket {

    private final long bucketSize;
    private final long refillRate;
    private double currentFilled;
    private long lastRefillTime;

    public Bucket(long bucketSize, long refillRate) {
        this.bucketSize = bucketSize;
        this.refillRate = refillRate;
        this.currentFilled = bucketSize;
        lastRefillTime = System.nanoTime();
    }
}
