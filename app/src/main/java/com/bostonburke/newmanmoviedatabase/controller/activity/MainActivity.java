package com.bostonburke.newmanmoviedatabase.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bostonburke.newmanmoviedatabase.model.DisplayResults;
import com.bostonburke.newmanmoviedatabase.model.MRParcel;
import com.bostonburke.newmanmoviedatabase.model.MovieTable;
import com.bostonburke.newmanmoviedatabase.R;

/**
 * View controller for activity_main.xml.
 */
public class MainActivity extends AppCompatActivity {

    // Attributes
    MovieTable table;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = new MovieTable();

        // Get the search field
        editText = (EditText) findViewById(R.id.search_field);
    }

    /**
     * Called when the titleSearch button is clicked
     *
     * @param view the view that was clicked
     */
    public void searchButtonClicked(View view) {
        String query = editText.getText().toString();

        // Check for empty string
        if( !query.isEmpty() ) {
            MRParcel results = new MRParcel(table.searchFor(query));

            // If the results set was empty, let the user know in
            // activity_main
            if (results.get().isEmpty()) {
                TextView nrFlag = (TextView) findViewById(R.id.noResultsFlag);
                nrFlag.setVisibility(View.VISIBLE);
            } else {
                Intent intent = new Intent(this, DisplayResults.class);
                intent.putExtra("results", results);
            }
        }
    }
}














