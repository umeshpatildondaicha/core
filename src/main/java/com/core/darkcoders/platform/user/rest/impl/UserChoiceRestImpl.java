package com.core.darkcoders.platform.user.rest.impl;

import com.core.darkcoders.platform.user.model.UserChoice;
import com.core.darkcoders.platform.user.rest.UserChoiceRest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of UserChoiceRest.
 */
@RestController
@Primary
@RequestMapping(path = "/base/util/rest/UserChoice", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserChoiceRestImpl implements UserChoiceRest {

    private static final Logger logger = LogManager.getLogger(UserChoiceRestImpl.class);

    @Override
    public List<UserChoice> findChoices() {
        logger.info("Finding all user choices");
        List<UserChoice> choices = new ArrayList<>();
        
        // Add some sample choices
        UserChoice choice1 = new UserChoice();
        choice1.setId(1L);
        choice1.setChoiceName("Theme");
        choice1.setChoiceValue("dark");
        choice1.setChoiceType("UI");
        choice1.setDescription("User interface theme preference");
        choice1.setIsActive(true);
        choice1.setCreatedBy("system");
        choice1.setUpdatedBy("system");
        choices.add(choice1);

        UserChoice choice2 = new UserChoice();
        choice2.setId(2L);
        choice2.setChoiceName("Language");
        choice2.setChoiceValue("en");
        choice2.setChoiceType("UI");
        choice2.setDescription("User interface language preference");
        choice2.setIsActive(true);
        choice2.setCreatedBy("system");
        choice2.setUpdatedBy("system");
        choices.add(choice2);

        return choices;
    }
} 