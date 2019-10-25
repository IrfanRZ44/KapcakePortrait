
package com.exomatik.kapcakeportrait.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelLogout {

    @SerializedName("response")
    @Expose
    private String response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelLogout() {
    }

    /**
     * 
     * @param response
     */
    public ModelLogout(String response) {
        super();
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
