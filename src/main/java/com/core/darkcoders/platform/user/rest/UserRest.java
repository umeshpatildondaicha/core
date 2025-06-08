package com.core.darkcoders.platform.user.rest;

import com.core.darkcoders.platform.user.model.UserInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST interface for user management.
 */
@RequestMapping(path = "/base/util/rest/User", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserRest {

    /**
     * Get user information.
     *
     * @return the user information
     */
    @GetMapping("/userinfo")
    UserInfo getUserInfo();
} 