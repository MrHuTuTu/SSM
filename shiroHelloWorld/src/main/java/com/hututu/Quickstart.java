package com.hututu;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class Quickstart {
    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);

    public Quickstart() {
    }

    public static void main(String[] args) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = (SecurityManager)factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String)session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }

        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            token.setRememberMe(true);

            try {
                currentUser.login(token);
            } catch (UnknownAccountException var8) {
                log.info("There is no user with username of " + token.getPrincipal());
                return;
            } catch (IncorrectCredentialsException var9) {
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
                return;
            } catch (LockedAccountException var10) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  Please contact your administrator to unlock it.");
            } catch (AuthenticationException var11) {
            }
        }

        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");
        if (currentUser.hasRole("schwartz")) {
            log.info("*******-->May the Schwartz be with you!");
            if (currentUser.isPermitted("lightsaber:weild")) {
                log.info("You may use a lightsaber ring.  Use it wisely.");
            } else {
                log.info("Sorry, lightsaber rings are for schwartz masters only.");
            }

            if (currentUser.isPermitted("user:delete:zhangsan")) {
                log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  Here are the keys - have fun!");
            } else {
                log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
            }

            System.out.println("*************************************--->" + currentUser.isAuthenticated());
            currentUser.logout();
            System.out.println("*************************************--->" + currentUser.isAuthenticated());
            System.exit(0);
        } else {
            log.info(" Hello, mere mortal.");
        }
    }
}