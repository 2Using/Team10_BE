package bdbe.bdbd._core.errors.utils;


import bdbe.bdbd._core.errors.exception.UnAuthorizedError;
import bdbe.bdbd._core.errors.exception.ForbiddenError;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterResponseUtils {
    public static void unAuthorized(HttpServletResponse resp, UnAuthorizedError e) throws IOException {
        resp.setStatus(e.status().value());
        resp.setContentType("application/json; charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(e.body());
        resp.getWriter().println(responseBody);
    }

    public static void forbidden(HttpServletResponse resp, ForbiddenError e) throws IOException {
        resp.setStatus(e.status().value());
        resp.setContentType("application/json; charset=utf-8");
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(e.body());
        resp.getWriter().println(responseBody);
    }
}
