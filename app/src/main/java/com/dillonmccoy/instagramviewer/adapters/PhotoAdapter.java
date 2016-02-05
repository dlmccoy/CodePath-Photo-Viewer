package com.dillonmccoy.instagramviewer.adapters;

import android.content.Context;
import android.media.Image;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dillonmccoy.instagramviewer.R;
import com.dillonmccoy.instagramviewer.classes.Photo;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;


public class PhotoAdapter extends ArrayAdapter<Photo> {

    public PhotoAdapter(Context context, List<Photo> objects) {
        super(context, R.layout.item_photo, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Photo photo = getItem(position);

        View view;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        // Load the profile photo.
        ImageView ivProfilePhoto = (ImageView) convertView.findViewById(R.id.ivProfilePhoto);
        Picasso.with(getContext()).load(photo.profilePhotoUrl).into(ivProfilePhoto);

        // Load the username.
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        tvUsername.setText(photo.username);

        // Load the image caption.
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        tvCaption.setText(photo.caption);

        // Load the actual photo
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);

        // Load the number of likes.
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        tvLikes.setText("\uD83D\uDC9A " + photo.likesCount);
//        tvLikes.setText("asdf");

        // Load the created time diff.
        TextView tvCreated = (TextView) convertView.findViewById(R.id.tvCreated);
        CharSequence createdStr = DateUtils.getRelativeDateTimeString(
                getContext(),
                photo.createdTime,
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS, 0);
        tvCreated.setText(createdStr);

        return convertView;
    }
}
