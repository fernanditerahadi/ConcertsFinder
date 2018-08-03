package com.example.android.music.views.activities;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.music.R;
import com.example.android.music.views.adapters.DescriptionViewPagerAdapter;
import com.example.android.music.views.adapters.RecyclerViewHolder;
import com.example.android.music.database.AppDatabase;
import com.example.android.music.database.models.Concert;
import com.example.android.music.executors.AppExecutors;
import com.example.android.music.viewmodels.ConcertViewModel;
import com.example.android.music.viewmodels.ConcertViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DescriptionActivity extends AppCompatActivity {

    private boolean enableBookmark;
    private Concert concert;
    private AppDatabase appDatabase;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        // Instantiate the View Pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.desc_view_pager);
        DescriptionViewPagerAdapter descriptionViewPagerAdapter =
                new DescriptionViewPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(descriptionViewPagerAdapter);

        // Instantiate the Tab Layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.desc_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        // Instantiate the Image View
        ImageView imageView = (ImageView) findViewById(R.id.desc_image_view);

        // Receive an intent from User's click and display the image on the Image View
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(RecyclerViewHolder.CONCERT_INFO)) {
            concert = intent.getExtras().getParcelable(RecyclerViewHolder.CONCERT_INFO);
            String imageUrl = concert.getArtistImageURL();
            if (!imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(imageView);
            } else {
                Picasso.get().load(RecyclerViewHolder.PLACEHOLDER_IMAGE_URL).into(imageView);
            }
        }

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        appDatabase = AppDatabase.getInstance(getApplicationContext());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        this.menu = menu;
        checkDatabaseRecord();
        MenuItem bookmark = menu.findItem(R.id.desc_action_bookmark);
        if (enableBookmark) {
            bookmark.setIcon(R.drawable.ic_bookmark_false);
        } else {
            bookmark.setIcon(R.drawable.ic_bookmark_true);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.desc_action_bookmark) {
            if (enableBookmark) {
                bookmarkConcert();
                enableBookmark = false;
                item.setIcon(R.drawable.ic_bookmark_true);
            } else {
                showDeleteConfirmationDialog(item);
            }

            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void bookmarkConcert() {
        AppExecutors.getInstance().diskIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.appDao().insertConcert(concert);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), getString(R.string.toast_concert_bookmark), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
        );

    }

    public void deleteConcert() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.appDao().deleteConcertById(concert.getConcertId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), getString(R.string.toast_concert_remove), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void checkDatabaseRecord() {
        ConcertViewModelFactory factory = new ConcertViewModelFactory(appDatabase, concert.getConcertId());
        final ConcertViewModel viewModel
                = ViewModelProviders.of(this, factory).get(ConcertViewModel.class);

        viewModel.getConcert().observe(this, new Observer<Concert>() {
            @Override
            public void onChanged(@Nullable Concert concertRetrieved) {
                viewModel.getConcert().removeObserver(this);
                if (concertRetrieved == null) {
                    enableBookmark = true;
                    menu.findItem(R.id.desc_action_bookmark).setIcon(R.drawable.ic_bookmark_false);
                } else {
                    enableBookmark = false;
                    menu.findItem(R.id.desc_action_bookmark).setIcon(R.drawable.ic_bookmark_true);
                }
            }
        });
    }

    private void showDeleteConfirmationDialog(final MenuItem item) {
        final CharSequence[] items = {"Yes, remove from favorite."};
        final ArrayList<Integer> selectedItems = new ArrayList<>();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(R.string.delete_dialog);
        alertBuilder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int indexSelected, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(indexSelected);
                } else if (selectedItems.contains(indexSelected)) {
                    selectedItems.remove(Integer.valueOf(indexSelected));
                }
            }
        });
        alertBuilder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (selectedItems.size() > 0) {
                    deleteConcert();
                    enableBookmark = true;
                    item.setIcon(R.drawable.ic_bookmark_false);
                } else {
                    dialog.dismiss();
                }
            }
        });
        alertBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        alertBuilder.create().show();
    }
}
