
package com.exomatik.kapcakeportrait.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelVersion {

    @SerializedName("version")
    @Expose
    private String version;

    /**
     * No args constructor for use in serialization
     *
     */
    public ModelVersion() {
    }

    /**
     *
     * @param version
     */
    public ModelVersion(String version) {
        super();
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
