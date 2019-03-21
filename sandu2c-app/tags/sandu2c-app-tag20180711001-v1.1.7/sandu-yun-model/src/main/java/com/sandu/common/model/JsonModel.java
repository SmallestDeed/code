package com.sandu.common.model;

import java.io.Serializable;

public class JsonModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5099348548818478723L;

    protected boolean success = false; // indicate whether it is a successful return

    protected String message;

    /* for test case! */
    public JsonModel() {
    }

    protected JsonModel(boolean success) {
        this.success = success;
    }

    protected JsonModel(String message) {
        this.success = false;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    /*****************************************************************************/
  /* Helper methods to construct JsonModel */

    /*****************************************************************************/
    public static JsonModel createSuccessfulEmptyJsonModel() {
        return new JsonModel(true);
    }

    public static JsonModel createFailedJsonModel(String message) {
        return new JsonModel(message);
    }

}
