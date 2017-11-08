package cat.aubricoc.weddingconsole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Competitor> competitors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        CardsAdapter cardsAdapter = new CardsAdapter(competitors);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cardsAdapter);

        loadCompetitors();

        cardsAdapter.setOnCardClickListener(new CardsAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(Card card) {
                goToCompetitor((Competitor) card);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.confirmation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            loadCompetitors();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadCompetitors() {
        DatabaseReference confirmationsRef = FirebaseDatabase.getInstance().getReference("competitors");
        confirmationsRef.keepSynced(true);
        confirmationsRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                competitors.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Competitor competitor = toCompetitor(child);
                    competitors.add(competitor);
                }
                Collections.sort(competitors);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    private Competitor toCompetitor(DataSnapshot dataSnapshot) {
        Competitor competitor = new Competitor();
        for (DataSnapshot property : dataSnapshot.getChildren()) {
            String key = property.getKey();
            switch (key) {
                case "name":
                    competitor.setName((String) property.getValue());
                    break;
                case "language":
                    competitor.setLanguage((String) property.getValue());
                    break;
                case "answers":
                    Map<Integer, Integer> answers = new HashMap<>();
                    for (DataSnapshot answer : property.getChildren()) {
                        answers.put(Integer.parseInt(answer.getKey()), ((Long) answer.getValue()).intValue());
                    }
                    competitor.setAnswers(answers);
                    break;
                case "date":
                    Date date = new Date();
                    date.setTime((Long) property.getValue());
                    competitor.setDate(date);
                    break;
            }
        }

        calculatePoints(competitor);

        return competitor;
    }

    private void calculatePoints(Competitor competitor) {
        int points = 0;
        for (int answer : competitor.getAnswers().values()) {
            if (answer == 1) {
                points++;
            }
        }
        competitor.setPoints(points);
    }

    private void goToCompetitor(Competitor competitor) {
        Intent intent = new Intent(this, CompetitorActivity.class);
        intent.putExtra(Constants.COMPETITOR_EXTRA, competitor);
        startActivity(intent);
    }
}
