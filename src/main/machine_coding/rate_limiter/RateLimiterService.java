package rate_limiter;

import java.util.HashMap;
import java.util.Map;

public class RateLimiterService {
    private Map<String, Bucket> userBucket;

    public RateLimiterService() {
        this.userBucket = new HashMap<>();
    }

    public boolean isAllowed(Request request){

        if (!userBucket.containsKey(request.getRateLimiterUser().getId()))
            generateBucket(request.getRateLimiterUser());

        Bucket bucket = userBucket.get(request.getRateLimiterUser().getId());
//        System.out.println("token requested : "+request.getToken());
        refill(bucket);
        if (bucket.getCurrentFilled()>=request.getToken()) {
            double currentBucket = bucket.getCurrentFilled();
            bucket.setCurrentFilled(currentBucket-request.getToken());
            return true;
        }
        return false;
    }

    private void generateBucket(RateLimiterUser rateLimiterUser){
        Bucket bucket = new Bucket(5,10000);
        userBucket.put(rateLimiterUser.getId(), bucket);
    }

    private void refill(Bucket bucket){
        long now = System.nanoTime();
        double currentlyTokens = bucket.getCurrentFilled();
        double tokensToBeAdded = (now-bucket.getLastRefillTime()) * bucket.getRefillRate()/1e9;
//        System.out.println("Current: "+currentlyTokens);
//        System.out.println("Added: "+tokensToBeAdded);
        bucket.setCurrentFilled(Math.min(currentlyTokens + tokensToBeAdded, bucket.getBucketSize()));
        bucket.setLastRefillTime(now);
    }
}
