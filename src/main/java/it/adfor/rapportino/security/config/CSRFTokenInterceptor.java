
package it.adfor.rapportino.security.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

public class CSRFTokenInterceptor extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest req, 
            HttpServletResponse res, FilterChain fc)
            throws ServletException, IOException {
        CsrfToken token = (CsrfToken)  req.getAttribute(CsrfToken.class.getName());
        if (token!=null){
            Cookie cookie = WebUtils.getCookie(req, "XSRF-TOKEN");
            String tokenString = token.getToken();
            if (cookie == null || tokenString!=null && !tokenString.equals(cookie.getValue())){
                cookie = new Cookie("XSRF-TOKEN",tokenString);
                cookie.setPath("/");
                res.addCookie(cookie);
            }
        }
        fc.doFilter(req, res);
    }
    
}
