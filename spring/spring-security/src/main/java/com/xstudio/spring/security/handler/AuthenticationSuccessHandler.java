package com.xstudio.spring.security.handler;

import com.xstudio.spring.security.AppUserDetails;
import com.xstudio.spring.security.vo.TokenUserVo;
import com.xstudio.tool.request.RequestUtil;
import com.xstudio.tool.utils.Msg;
import org.apache.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiaobiao
 * @version 2017/11/27
 */
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        // 输出登录提示信息
        response.setStatus(HttpStatus.SC_OK);
        Msg<Object> msg = new Msg<>();
        msg.setData(setData(authentication));
        // 返回json
        RequestUtil.writeJson(response, msg);
    }

    public Object setData(Authentication authentication) {
        if (authentication.getPrincipal() instanceof AppUserDetails) {
            AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
            return userDetails.getDetails();
        } else if (authentication.getPrincipal() instanceof TokenUserVo) {
            return authentication.getPrincipal();
        }

        return null;
    }
}
