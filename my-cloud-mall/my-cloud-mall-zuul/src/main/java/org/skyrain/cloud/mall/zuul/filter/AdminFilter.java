package org.skyrain.cloud.mall.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.skyrain.cloud.mall.common.common.Constant;
import org.skyrain.cloud.mall.user.model.pojo.User;
import org.skyrain.cloud.mall.zuul.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class AdminFilter extends ZuulFilter {
    @Autowired
    UserFeignClient userFeignClient;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context=RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String requestURI = request.getRequestURI();
        if(requestURI.contains("admin")){
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession();
        User currentUser = (User)session.getAttribute(Constant.CURRENT_USER);
        if(currentUser==null){
            context.setSendZuulResponse(false);
            context.setResponseBody("{\n"
                    + "    \"status\": 10007,\n"
                    + "    \"msg\": \"NEED_LOGIN\",\n"
                    + "    \"data\": null\n"
                    + "}");
            context.setResponseStatusCode(200);
            return null;
        }
        if (!userFeignClient.isAdminRole(currentUser.getUsername())) {
            context.setSendZuulResponse(false);
            context.setResponseBody("{\n"
                    + "    \"status\": 10008,\n"
                    + "    \"msg\": \"NEED_ADMIN\",\n"
                    + "    \"data\": null\n"
                    + "}");
            context.setResponseStatusCode(200);
        }

        return null;
    }
}
