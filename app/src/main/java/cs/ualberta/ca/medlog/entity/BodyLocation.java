package cs.ualberta.ca.medlog.entity;

import java.io.Serializable;

/**
 * <p>
 *     Description: <br>
 *         Class representing a record's body location in the application. It contains the related
 *         body photo and the x,y percentages of the location selected on the photo.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich
 * @version 1.2
 * @see Photo
 */
public class BodyLocation implements Serializable {
    private Photo photo;
    private float x, y;

    private static final double SEARCH_DIST_BODY = 25;

    /**
     * Constructs the body location with the specified body photo and x-y percentages of the location.
     * @param photo The body photo.
     * @param x The x percentage of the selected location.
     * @param y The y percentage of the selected location.
     */
    public BodyLocation(Photo photo, float x, float y){
        this.photo = photo;
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the body photo related to the given body location.
     * @return The body photo.
     */
    public Photo getPhoto() {
        return photo;
    }

    /**
     * Retrieves the x percentage of the body location.
     * @return The x percentage.
     */
    public float getX() {
        return x;
    }

    /**
     * Retrieves the y percentage of the body location.
     * @return The y percentage.
     */
    public float getY() {
        return y;
    }

    /**
     * Checks if two body locations are equal.
     * @param obj The other body location object
     * @return True if they are equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BodyLocation){
            BodyLocation bl = (BodyLocation) obj;
            return bl.getX() == x && bl.getY() == y && photo == bl.getPhoto();
        }
        return false;
    }

    /**
     * Returns if a body location is near to another location.
     * @param otherLocation The body location to compare against.
     * @return If its less than or equal to the max dist.
     */
    public boolean isNear(BodyLocation otherLocation){
        double dist = Math.sqrt(Math.pow((x - otherLocation.x)*100, 2) + Math.pow((y - otherLocation.y)*100, 2));
        return dist <= SEARCH_DIST_BODY;
    }
}
