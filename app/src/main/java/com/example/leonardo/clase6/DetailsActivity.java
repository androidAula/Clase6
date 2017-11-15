package com.example.leonardo.clase6;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Leonardo on 14/11/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_details);

        /*
        Get the text of clicked list item, that needs to be displayed
        Before launching the DetailActivity, the ListActivity added text of clicked item as a stringExtra in the intent
         */
        String text = getIntent().getStringExtra("Name");

        TextView title = (TextView) findViewById(R.id.detailsTitleText);
        title.setText(text);
    }

    /**
     * This method is called when the user manually clicks the BACK button of the device
     * We override the method to perform the custom logic of reversing the animation of the title,
     * when transitioning back to the ListActivity
     */
    @Override
    public void onBackPressed()
    {
        /*
        Ideally, always call the super() implementation first as it allows the system to perform any necessary operations
        We should follow up the super() call with the custom logic that we would like to execute
         */
        super.onBackPressed();

        /*
        The "title" textView was animated during transitioning from ListActivity -> DetailsActivity
        In order to perform reverse animation when transitioning back to ListActivity,
         we need to call finishAfterTransition() rather than Activity.finish();
         */
        finishAfterTransition();
    }

}
