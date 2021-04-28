/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient;

import com.acidmanic.commandline.commands.Command;
import com.acidmanic.commandline.commands.CommandFactory;
import com.acidmanic.commandline.commands.Help;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.lightweight.logger.ConsoleLogger;
import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.localbrokerclient.commands.ApplicationContext;
import com.acidmanic.localbrokerclient.commands.Pull;
import com.acidmanic.localbrokerclient.commands.Push;
import java.util.Map;

/**
 *
 * @author diego
 */
public class Main {

    public static void main(String[] args) {

        Logger logger = new ConsoleLogger();

        TypeRegistery registery = new TypeRegistery();

        registery.registerClass(Pull.class);

        registery.registerClass(Push.class);

        registery.registerClass(Help.class);

        ApplicationContext context = new ApplicationContext();

        context.setFailure(false);

        CommandFactory factory = new CommandFactory(registery, logger, context);

        Map<Command, String[]> commands = factory.make(args,true);

        commands.forEach((c, ar) -> c.execute(ar));

        if (context.isFailure()) {
            System.exit(-1);
        }
    }
}
