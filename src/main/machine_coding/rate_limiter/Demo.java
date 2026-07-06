package rate_limiter;

public class Demo {
    public static void main(String[] args) {
        RateLimiterUser rateLimiterUser = new RateLimiterUser("Ram");
        BusinessService businessService = new BusinessService();
        System.out.println(businessService.service(new Request(2, rateLimiterUser)).getMsg());
        System.out.println(businessService.service(new Request(1, rateLimiterUser)).getMsg());
        System.out.println(businessService.service(new Request(3, rateLimiterUser)).getMsg());
        System.out.println(businessService.service(new Request(4, rateLimiterUser)).getMsg());
        System.out.println(businessService.service(new Request(3, rateLimiterUser)).getMsg());
    }
}
