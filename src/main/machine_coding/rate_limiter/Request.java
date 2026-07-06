package rate_limiter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {
    final double token;
    final RateLimiterUser rateLimiterUser;

    public Request(double token, RateLimiterUser rateLimiterUser){
        this.token = token;
        this.rateLimiterUser = rateLimiterUser;
    }
}
