package rate_limiter;

public class BusinessService {
    private RateLimiterService rateLimiterService;

    public BusinessService(){
        this.rateLimiterService = new RateLimiterService();
    }

    public Response service(Request request){
        if(rateLimiterService.isAllowed(request))
            return new Response("Success");
        return new Response("Exceeded no of Request. Please try after some time");
    }
}
