package cs.ualberta.ca.medlog.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The Activity for adding a body location to a record. It presents an image view that
 *         presents body photos which the user can mark to set a body location. Once the user
 *         selects a location, the location is sent back to the parent activity and the
 *         AddMapLocationActivity closes.
 *
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.1
 * @see PatientAddRecordActivity
 * @see PatientSearchActivity
 */
public class AddBodyLocationActivity extends AppCompatActivity {
    private ImageView imageView;
    private ArrayList<Photo> photos;
    private ArrayList<Bitmap> photoBitmaps;
    private int index = 0;

    private Photo selectedPhoto;
    private int locationX;
    private int locationY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_body_location);

        Button previousButton = findViewById(R.id.activityAddBodyLocation_PreviousButton);
        Button nextButton = findViewById(R.id.activityAddBodyLocation_NextButton);
        Button saveButton = findViewById(R.id.activityAddBodyLocation_SaveButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPreviousPhoto();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNextPhoto();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBodyLocation();
            }
        });

        photos = ((Patient)AppStatus.getInstance().getCurrentUser()).getBodyPhotos();
        retrievePhotoBitmaps(photos);

        imageView = findViewById(R.id.activitySlideshow_ImageView);
        imageView.setImageBitmap(photoBitmaps.get(index));
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    locationX = Math.round(event.getX());
                    locationY = Math.round(event.getY());
                    updateLocationMarkerPlace();
                }
                return false;
            }
        });
    }

    private void retrievePhotoBitmaps(ArrayList<Photo> photos) {
        photoBitmaps = new ArrayList<>();
        for(Photo photo: photos) {
            photoBitmaps.add(photo.getBitmap(50,50));
        }
    }

    private void displayPreviousPhoto() {
        if (index > 0) {
            imageView.setImageBitmap(photoBitmaps.get(--index));
            resetLocation();
        }
        else {
            Toast.makeText(this,"No previous photo",Toast.LENGTH_SHORT).show();
        }
    }

    private void displayNextPhoto() {
        if (index+1 < photoBitmaps.size()) {
            imageView.setImageBitmap(photoBitmaps.get(++index));
            resetLocation();
        }
        else {
            Toast.makeText(this,"No next photo",Toast.LENGTH_SHORT).show();
        }
    }

    private void resetLocation() {
        selectedPhoto = null;
        ImageView markerView = findViewById(R.id.activityAddBodyLocation_MarkerView);
        markerView.setVisibility(View.INVISIBLE);
    }

    private void updateLocationMarkerPlace() {
        selectedPhoto = photos.get(index);
        ImageView markerView = findViewById(R.id.activityAddBodyLocation_MarkerView);
        markerView.setVisibility(View.VISIBLE);
        markerView.setX(imageView.getX() + locationX);
        markerView.setY(imageView.getY() + locationY);
    }

    private void saveBodyLocation() {
        BodyLocation location = new BodyLocation(selectedPhoto,locationX,locationY);

        Intent intent = new Intent();
        intent.putExtra("BODY_LOCATION",location);
        setResult(Activity.RESULT_OK, intent);
    }
}