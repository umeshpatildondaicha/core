package com.core.darkcoders.platform.user.rest.impl;

import com.core.darkcoders.platform.user.model.UserInfo;
import com.core.darkcoders.platform.user.rest.UserRest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of UserRest.
 */
@RestController
@Primary
@RequestMapping(path = "/base/util/rest/User", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestImpl implements UserRest {

    private static final Logger logger = LogManager.getLogger(UserRestImpl.class);

    @Override
    public UserInfo getUserInfo() {
        logger.info("Getting user information");
        UserInfo userInfo = new UserInfo();
        userInfo.setName("Yogesh Chauhan");
        userInfo.setEmail("yogesh.chauhan@visionwaves.com");
        userInfo.setCustomerId("2");
        userInfo.setCustomerName("BNTV");
        userInfo.setPreferredUsername("yogesh.admin");
        userInfo.setLocale("en");
        userInfo.setGivenName("Yogesh");
        userInfo.setFamilyName("Chauhan");
        userInfo.setEmailVerified(true);
        return userInfo;
    }
} 