package cs.ualberta.ca.medlog.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import cs.ualberta.ca.medlog.R;
import cs.ualberta.ca.medlog.controller.SyncController;
import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Photo;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.SearchResult;
import cs.ualberta.ca.medlog.entity.user.Patient;
import cs.ualberta.ca.medlog.helper.Database;
import cs.ualberta.ca.medlog.singleton.AppStatus;

/**
 * <p>
 *     Description: <br>
 *         The patient problem searching screen activity for the Application, this presents the
 *         gui for the patient to search through their problems and records utilizing keywords.
 *         Additional searching information in terms of searching around a specified region or body
 *         location is also present through buttons to proceed to selection activities.
 *         The results of these searches can be viewed by clicking on their entries.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Tyler Gobran
 * @version 1.0
 * @see PatientMenuActivity
 * @see PatientProblemViewActivity
 * @see PatientRecordViewActivity
 * @see AddMapLocationActivity
 * @see AddBodyLocationActivity
 */
public class PatientSearchActivity extends AppCompatActivity {
    final int MAP_LOCATION_REQUEST = 1;
    final int BODY_LOCATION_REQUEST = 2;

    private ArrayList<SearchResult> searchResults;
    private SearchAdapter adapter;

    private MapLocation mapLocation = null;
    private BodyLocation bodyLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_search);

        Button searchButton = findViewById(R.id.activityPatientSearch_SearchButton);
        Button mapLocationButton = findViewById(R.id.activityPatientSearch_MapLocationButton);
        Button bodyLocationButton = findViewById(R.id.activityPatientSearch_BodyLocationButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateSearch();
            }
        });
        mapLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapLocationSelector();
            }
        });
        bodyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBodyLocationSelector();
            }
        });

        ListView resultsListView = findViewById(R.id.activityPatientSearch_ResultsListView);
        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openItemView(position);
            }
        });

        adapter = new SearchAdapter(this, new ArrayList<SearchResult>());
        resultsListView.setAdapter(adapter);
    }

    private void initiateSearch() {
        Database db = new Database(this);
        EditText et = findViewById(R.id.activityPatientSearch_KeywordEditText);

        ArrayList<String> keywords = new ArrayList<>(Arrays.asList(et.getText().toString().split(" ")));
        adapter.clear();

        searchResults = db.searchPatient((Patient)AppStatus.getInstance().getCurrentUser(), keywords, mapLocation,  bodyLocation);
        adapter.addAll(searchResults);
        adapter.notifyDataSetChanged();
    }

    private void openMapLocationSelector() {
        Intent intent = new Intent(this, AddMapLocationActivity.class);
        startActivityForResult(intent,MAP_LOCATION_REQUEST);
    }

    private void openBodyLocationSelector() {
        ArrayList<Photo> bodyPhotos = ((Patient)AppStatus.getInstance().getCurrentUser()).getBodyPhotos();
        if (!bodyPhotos.isEmpty()) {
            Intent intent = new Intent(this, AddBodyLocationActivity.class);
            intent.putExtra("BODY_PHOTOS",bodyPhotos);
            startActivityForResult(intent, BODY_LOCATION_REQUEST);
        }
        else {
            Toast.makeText(this,R.string.activityPatientSearch_NoBodyPhotos,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == MAP_LOCATION_REQUEST) { // If a map location was selected
                double latitude = data.getDoubleExtra("LATITUDE", -1);
                double longitude = data.getDoubleExtra("LONGITUDE", -1);
                Toast.makeText(this, R.string.activityPatientSearch_MapLocationAdded, Toast.LENGTH_SHORT).show();
                mapLocation = new MapLocation(latitude,longitude);
            }
            else if (requestCode == BODY_LOCATION_REQUEST) {
                bodyLocation = (BodyLocation)data.getSerializableExtra("BODY_LOCATION");
                Toast.makeText(this,R.string.activityPatientSearch_BodyLocationAdded,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openItemView(int index) {
        SearchResult selected = searchResults.get(index);

        if (selected.getRecord() == null) {
            Intent intent = new Intent(this,PatientProblemViewActivity.class);
            AppStatus.getInstance().setViewedProblem(selected.getProblem());
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this,PatientRecordViewActivity.class);
            AppStatus.getInstance().setViewedProblem(selected.getProblem());
            AppStatus.getInstance().setViewedRecord(selected.getRecord());
            startActivity(intent);
        }
    }
}
