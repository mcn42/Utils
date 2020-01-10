/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mtahq.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michaeln
 */
public class AppPropertyManager {
    private Class enumClass; 
    private final Map<IAppProperty, String> props = new HashMap<>();
    private final String filePath;
    private final Logger log = Logger.getLogger(this.getClass().getCanonicalName());

    public AppPropertyManager(String filePath, Class enumClass) {
        this.filePath = filePath;
        this.enumClass = enumClass;
        this.loadProperties();
        //this.saveProperties();
        this.logProperties();
    }
    
    public void logProperties() {
        StringBuilder sb = new StringBuilder();
        sb.append("Application properties:\n");
        this.props.keySet().forEach((prop) -> {
            String val = this.props.get(prop);
            sb.append(String.format("%s: \t\t%s\n", prop,val));
        });
        log.info(sb.toString());
    }

    public String get(IAppProperty propId) {
        String s = this.props.get(propId);
        if (s == null) {
            s = propId.getDefaultValue();
        }
        return s;
    }
    
    public Integer getInt(IAppProperty propId)
    {
        String s = this.props.get(propId);
        if (s == null) {
            s = propId.getDefaultValue();
        }
        int i = Integer.parseInt(s);
        return i;
    }
    
    public Long getLong(IAppProperty propId)
    {
        String s = this.props.get(propId);
        if (s == null) {
            s = propId.getDefaultValue();
        }
        long i = Long.parseLong(s);
        return i;
    }
    
    public Float getFloat(IAppProperty propId)
    {
        String s = this.props.get(propId);
        if (s == null) {
            s = propId.getDefaultValue();
        }
        float i = Float.parseFloat(s);
        return i;
    }
    
    public boolean getBoolean(IAppProperty propId)
    {
        String s = this.props.get(propId);
        if (s == null) {
            s = propId.getDefaultValue();
        }
        boolean i = Boolean.valueOf(s);
        return i;
    }

    public final void loadProperties() {
        boolean missing = false;
        Properties p = new Properties();
        this.props.clear();
        try {
            Object[] aps = this.enumClass.getEnumConstants();
            p.load(new FileReader(new File(this.filePath)));
            for (Object obj : aps) {
                IAppProperty ap = (IAppProperty) obj;
                String val = p.getProperty(ap.toString());
                if (val == null) {
                    val = ap.getDefaultValue();
                    missing = true;
                    log.log(Level.WARNING, String.format("Could not load property '%s', defaulting to '%s'", ap, val));
                }
                this.props.put(ap, val);
            }

        } catch (FileNotFoundException ex) {
            log.log(Level.WARNING, String.format("Could not find properties file to load at '%s'", this.filePath));
            this.saveProperties();
        } catch (IOException ex) {
            log.log(Level.SEVERE, String.format("Could not load properties file at '%s'", this.filePath), ex);
        }
        if(missing) this.saveProperties();
    }

    public void saveProperties() {
        Properties p = new Properties();
        Object[] aps = this.enumClass.getEnumConstants();
        for (Object obj : aps) {
            IAppProperty ap = (IAppProperty) obj;
            String val = this.props.get(ap);
            if (val == null) {
                val = ap.getDefaultValue();
                log.log(Level.WARNING, String.format("Could not find property '%s' for saving, defaulting to '%s'", ap, val));
            }
            p.put(ap.toString(), val);
        }
        try {
            String cmt = String.format("Properties saved at %s", new Date());
            p.store(new FileWriter(new File(this.filePath)), cmt);
            Logger.getLogger(this.getClass().getCanonicalName()).info(String.format("Properties saved to %s", this.filePath));
        } catch (IOException ex) {
            log.log(Level.SEVERE, String.format("Could not save properties file at '%s'", this.filePath), ex);
        }
    }
}
