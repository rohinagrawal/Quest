package rate_limiter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    String msg;

    public Response(String msg) {
        this.msg = msg;
    }
}
