package com.xstudio.spring.security.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xstudio.spring.core.ContextUtil;
import com.xstudio.spring.security.AppUserDetails;
import com.xstudio.spring.security.handler.AuthenticationFailureHandler;
import com.xstudio.spring.security.vo.TokenUserVo;
import com.xstudio.tool.request.ClientResponse;
import com.xstudio.tool.request.RequestUtil;
import com.xstudio.tool.utils.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static Logger log = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

    private static String loginUrl;

    public TokenAuthenticationProvider() {
        super();
        this.setHideUserNotFoundExceptions(false);
    }

    private static String getLoginUrl() {
        if (StringUtils.isEmpty(loginUrl)) {
            loginUrl = ContextUtil.getProperty("app.loginUrl");
        }
        return loginUrl;
    }

    /**
     * 密码校验
     *
     * @param userDetails    用户
     * @param authentication 认证信息
     * @throws AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
        AppUserDetails user = (AppUserDetails) userDetails;
        if (!user.isEnabled()) {
            new AuthenticationFailureHandler();
            return;
        }
        if (logger.isDebugEnabled()) {
            log.debug("{}", JSON.toJSONString(userDetails));
        }
    }

    @Override
    protected UserDetails retrieveUser(String token, UsernamePasswordAuthenticationToken authentication) {

        ClientResponse response = RequestUtil.get(getLoginUrl() + token);
        Msg<TokenUserVo> msg = JSON.parseObject(response.getContent(), new TypeReference<Msg<TokenUserVo>>() {
        });
        if (msg.getSuccess()) {
            TokenUserVo userDetails = msg.getData();
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            for (String perm : userDetails.getPerms()) {
                authorities.add(new SimpleGrantedAuthority(perm));
            }

            return new AppUserDetails(userDetails.getUsername(), "", userDetails.getUserId(), userDetails, authorities);
        }
        throw new UsernameNotFoundException("用户不存在");
    }
}
