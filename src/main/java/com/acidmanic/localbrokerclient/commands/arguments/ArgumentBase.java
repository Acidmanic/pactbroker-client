/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands.arguments;

import com.acidmanic.commandline.commands.CommandBase;

/**
 *
 * @author diego
 */
public abstract class ArgumentBase extends CommandBase {

    @Override
    protected String getArgumentsDesciption() {
        return "";
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1 || args[0] == null || args[0].length() == 0) {
            error(this.getClass().getSimpleName() + " takes one argument.");
            return;
        }
        ArgumentsContext context = getContext();

        execute(context, args[0]);
    }

    protected abstract void execute(ArgumentsContext context, String argument);

    @Override
    public boolean hasArguments() {
        return true;
    }

}
