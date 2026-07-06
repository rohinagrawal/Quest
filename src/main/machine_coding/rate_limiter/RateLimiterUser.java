package rate_limiter;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RateLimiterUser {
    String id;
    String name;

    public RateLimiterUser(String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
