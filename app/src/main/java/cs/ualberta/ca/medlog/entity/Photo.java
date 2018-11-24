package cs.ualberta.ca.medlog.entity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

/**
 *
 * <h1>
 *     Photo
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         The purpose of this class is to hold a representation of a photo as well as an optional
 *         label for the photograph.
 *
 * </p>
 *
 * @author Thomas Roskewich
 * @contact roskewic@ualberta.ca
 */
public class Photo implements Serializable {
    private String path;
    private String id;

    private String label;

    public Photo(String path) {
        this.path = path;
        this.label = "";
    }

    public Photo(String path, String label) {
        this.path = path;
        this.label = label;
    }

    /**
     * Retrieves the label for the body location.
     * @return The label as a string.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the photos path.
     * @return The photos path.
     */
    public String getPath(){
        return path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getBitmap(int width, int height) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        //int scaleFactor = Math.min(photoW/width, photoH/height);

        bmOptions.inJustDecodeBounds = false;
        //bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(path, bmOptions);
    }


}
