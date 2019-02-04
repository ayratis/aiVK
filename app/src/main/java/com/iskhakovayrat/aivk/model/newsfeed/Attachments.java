package com.iskhakovayrat.aivk.model.newsfeed;

import com.google.gson.annotations.SerializedName;
import com.iskhakovayrat.aivk.model.newsfeed.attachments.Album;
import com.iskhakovayrat.aivk.model.newsfeed.attachments.Audio;
import com.iskhakovayrat.aivk.model.newsfeed.attachments.Doc;
import com.iskhakovayrat.aivk.model.newsfeed.attachments.Link;
import com.iskhakovayrat.aivk.model.newsfeed.attachments.Photo;
import com.iskhakovayrat.aivk.model.newsfeed.attachments.PostedPhoto;
import com.iskhakovayrat.aivk.model.newsfeed.attachments.Sticker;
import com.iskhakovayrat.aivk.model.newsfeed.attachments.Video;
import com.iskhakovayrat.aivk.model.newsfeed.attachments.Wall;

public class Attachments {

    public static enum Type{
        @SerializedName("photo") PHOTO,
        @SerializedName("link") LINK,
        @SerializedName("video") VIDEO,
        @SerializedName("doc") DOC,
        @SerializedName("audio") AUDIO,
        @SerializedName("posted_photo") POSTED_PHOTO,
        @SerializedName("album") ALBUM,
        @SerializedName("sticker") STICKER,
        @SerializedName("wall") WALL
    }

    @SerializedName("type")
    private Type type;

    @SerializedName("photo")
    private Photo photo;

    @SerializedName("video")
    private Video video;

    @SerializedName("doc")
    private Doc doc;

    @SerializedName("link")
    private Link link;

    @SerializedName("posted_photo")
    private PostedPhoto postedPhoto;

    @SerializedName("audio")
    private Audio audio;

    @SerializedName("album")
    private Album album;

    @SerializedName("sticker")
    private Sticker sticker;

    @SerializedName("wall")
    private Wall wall;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public PostedPhoto getPostedPhoto() {
        return postedPhoto;
    }

    public void setPostedPhoto(PostedPhoto postedPhoto) {
        this.postedPhoto = postedPhoto;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Doc getDoc() {
        return doc;
    }

    public void setDoc(Doc doc) {
        this.doc = doc;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Sticker getSticker() {
        return sticker;
    }

    public void setSticker(Sticker sticker) {
        this.sticker = sticker;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }
}
