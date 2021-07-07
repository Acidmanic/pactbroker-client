/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands;

import com.acidmanic.localbrokerclient.commands.arguments.ArgumentsContext;
import com.acidmanic.localbrokerclient.models.TagsDto;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;

/**
 *
 * @author diego
 */
public class Tags extends ApplicationCommandBase {

    @Override
    protected void execute(ApplicationContext applicationContext, ArgumentsContext argumentsContext) {

        HttpResponse<TagsDto> response = null;

        Unirest.config().setObjectMapper(new JacksonObjectMapper());

        try {
            response = Unirest
                    .get(argumentsContext.getServer() + "/tags")
                    .header("token", argumentsContext.getToken())
                    .asObject(TagsDto.class);
        } catch (Exception e) {

            error("EXCEPTION calling tags on server: " + e.getClass().getSimpleName());
        }
        if (response != null && response.isSuccess()) {

            TagsDto tags = response.getBody();

            if (tags != null) {

                log("----------------");

                tags.getModel().forEach((tag) -> log(tag));

                log("----------------");
            }
        }
    }

    @Override
    protected String getUsageDescription() {

        return "This commands gets all stored tags "
                + "from broker server and prints them on console.";
    }
}
