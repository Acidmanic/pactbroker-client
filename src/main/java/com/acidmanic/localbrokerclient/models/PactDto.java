/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.models;

/**
 *
 * @author diego
 * @param <TModel> data to be transferred
 */
public class PactDto {

    private Pact model;
    private boolean failure = false;
    private String error;
    
    public PactDto() {
    }

    public PactDto(Pact model) {
        this.model = model;
        failure = false;
        error = "";
    }

    public Pact getModel() {
        return model;
    }

    public void setModel(Pact model) {
        this.model = model;
    }

    public boolean isFailure() {
        return failure;
    }

    public void setFailure(boolean failure) {
        this.failure = failure;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
