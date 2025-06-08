package com.core.darkcoders.platform.user.rest;

import com.core.darkcoders.platform.user.model.UserChoice;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * REST interface for user choice management.
 */
@RequestMapping(path = "/base/util/rest/UserChoice", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserChoiceRest {

    /**
     * Find all user choices.
     *
     * @return list of user choices
     */
    @GetMapping("/findChoices")
    List<UserChoice> findChoices();
} 