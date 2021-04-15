/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands;

import com.acidmanic.commandline.commands.context.ExecutionContext;

/**
 *
 * @author diego
 */
public class ApplicationContext implements ExecutionContext {

    private boolean failure;

    public ApplicationContext() {
        this.failure = true;
    }

    public boolean isFailure() {
        return failure;
    }

    public void setFailure(boolean failure) {
        this.failure = failure;
    }

    public void fail() {
        this.failure = true;
    }
}
