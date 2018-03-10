package ru.ic218.wallpapermanager.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Home user on 10.03.2018.
 */

public class CategoryPhoto {

    private String photoUrl;
    private String title;

    public CategoryPhoto(String photoUrl, String title) {
        this.photoUrl = photoUrl;
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<CategoryPhoto> getCategory() {
        return new ArrayList<>(
                Arrays.asList(
                        new CategoryPhoto("http://i.imgur.com/zuG2bGQ.jpg", "Galaxy"),
                        new CategoryPhoto("http://i.imgur.com/ovr0NAF.jpg", "Space Shuttle"),
                        new CategoryPhoto("http://i.imgur.com/n6RfJX2.jpg", "Galaxy Orion"),
                        new CategoryPhoto("http://i.imgur.com/qpr5LR2.jpg", "Earth"),
                        new CategoryPhoto("http://i.imgur.com/pSHXfu5.jpg", "Astronaut"),
                        new CategoryPhoto("http://i.imgur.com/3wQcZeY.jpg", "Satellite")));
    }
}
