package cs.ualberta.ca.medlog.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cs.ualberta.ca.medlog.R;

/**
 * <p>
 *     Description: <br>
 *         The Activity for the Care Provider patient view records screen, this presents the gui for
 *         a Provider to view a list of the records attached to a problem and proceed to a given one
 *         by clicking them.
 * </p>
 * <p>
 *     Issues: <br>
 *         Add connection to a Patient records list to display
 *         Add ability to grab a selected record
 *         Add transfer to the Provider Record View Activity
 * </p>
 *
 * @author Tyler Gobran
 * @version 0.1
 * @see ProviderProblemViewActivity
 */
public class ProviderPatientViewRecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_patient_view_records);

        //TODO Add code for an array adapter to connect to a provided records list

        ListView recordsList = findViewById(R.id.activityProviderPatientViewRecords_RecordsListView);
        recordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openRecordView(position);
            }
        });
    }

    private void openRecordView(int index) {
        //TODO Add record grabbing code

        //TODO Add transfer to the Provider Record View Activity
    }
}
