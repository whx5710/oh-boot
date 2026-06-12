package com.finn.framework.security.mobile;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 手机短信登录 AuthenticationProvider
 * 由于用户可能是平台用户的同时，还是第三方平台用户（微信、支付宝小程序等其他第三方平台），手机号码相同，
 * 因此，用手机验证码登录时，需确定用户源于哪个平台登录
 * @author 王小费 whx5710@qq.com
 *
 */
@Component
public class MobileAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final MobileVerifyCodeService mobileVerifyCodeService;

    public MobileAuthenticationProvider(MobileVerifyCodeService mobileVerifyCodeService) {
        this.mobileVerifyCodeService = mobileVerifyCodeService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        Assert.isInstanceOf(MobileAuthenticationToken.class, authentication,
//                () -> messages.getMessage(
//                        "MobileAuthenticationProvider.onlySupports",
//                        "Only MobileAuthenticationProvider is supported"));

        MobileAuthenticationToken authenticationToken = (MobileAuthenticationToken) authentication;
        String mobile = authenticationToken.getName();
        String code = (String) authenticationToken.getCredentials();
        String userType = authenticationToken.getUserType();

        try {
            UserDetails userDetails = mobileVerifyCodeService.loadUserByMobile(mobile, userType);
            if (userDetails == null) {
                throw new BadCredentialsException("Bad credentials");
            }

            // 短信验证码效验
            if (mobileVerifyCodeService.verifyCode(mobile, code)) {
                return createSuccessAuthentication(authentication, userDetails, userType);
            } else {
                throw new BadCredentialsException("mobile code is not matched");
            }
        } catch (UsernameNotFoundException ex) {
            throw new BadCredentialsException(this.messages
                    .getMessage("MobileAuthenticationProvider.badCredentials", "Bad credentials"));
        }

    }

    protected Authentication createSuccessAuthentication(Authentication authentication, UserDetails user, String userType) {
        MobileAuthenticationToken result = new MobileAuthenticationToken(user, null, userType,
                authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(mobileVerifyCodeService, "mobileVerifyCodeService must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

}
