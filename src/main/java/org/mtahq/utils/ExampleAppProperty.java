/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mtahq.utils;

/**
 *
 * @author MNILSEN
 */
public enum ExampleAppProperty implements IAppProperty {
    PROPERTY_1("P1"),
    PROPERTY_2("P2"),
    PROPERTY_BOOL("false"),
    PROPERTY_INT("1024");

    private final String defaultValue;

    private ExampleAppProperty(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

}
