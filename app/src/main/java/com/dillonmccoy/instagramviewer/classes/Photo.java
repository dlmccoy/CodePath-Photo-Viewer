package com.dillonmccoy.instagramviewer.classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dmccoy on 2/4/16.
 */
public class Photo {
    public String imageUrl;
    public String username;
    public String profilePhotoUrl;
    public String caption;
    public int imageHeight;
    public int likesCount;
    public long createdTime;

    public Photo(JSONObject photoJson) {
        try {
            // Get the image information.
            JSONObject imageResolutions = photoJson.getJSONObject("images");
            JSONObject standardResolution = imageResolutions.getJSONObject("standard_resolution");
            imageUrl = standardResolution.getString("url");
            imageHeight = standardResolution.getInt("height");

            // Get the username.
            JSONObject user = photoJson.getJSONObject("user");
            username = user.getString("username");
            profilePhotoUrl = user.getString("profile_picture");

            // Get the caption.
            JSONObject captionJson = photoJson.getJSONObject("caption");
            caption = captionJson.getString("text");

            // Get the likes.
            likesCount = photoJson.getJSONObject("likes").getInt("count");

            // Get the created time.
            createdTime = photoJson.getLong("created_time") * 1000;


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
