package com.loginform.GetData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserListData {

    @SerializedName("Status")
    @Expose
    public Boolean status;
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("Userdata")
    @Expose
    public List<Userdata> userdata = null;

    public class Userdata {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("emailid")
        @Expose
        public String emailid;
        @SerializedName("contact")
        @Expose
        public String contact;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("dob")
        @Expose
        public String dob;
        @SerializedName("type")
        @Expose
        public String type;

    }
}
