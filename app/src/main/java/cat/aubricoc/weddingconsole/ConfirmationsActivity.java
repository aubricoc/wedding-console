package cat.aubricoc.weddingconsole;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ConfirmationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Confirmation> confirmations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CardsAdapter(confirmations));

        loadConfirmations();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.confirmation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            loadConfirmations();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadConfirmations() {
        DatabaseReference confirmationsRef = FirebaseDatabase.getInstance().getReference("confirmations");
        confirmationsRef.keepSynced(true);
        confirmationsRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                confirmations.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Confirmation confirmation = toConfirmation(child);
                    confirmations.add(confirmation);
                }
                Collections.sort(confirmations);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    @NonNull
    private Confirmation toConfirmation(DataSnapshot dataSnapshot) {
        Confirmation confirmation = new Confirmation();
        for (DataSnapshot property : dataSnapshot.getChildren()) {
            String key = property.getKey();
            switch (key) {
                case "who":
                    confirmation.setWho((String) property.getValue());
                    break;
                case "with":
                    confirmation.setWith((String) property.getValue());
                    break;
                case "why":
                    confirmation.setWhy((String) property.getValue());
                    break;
                case "date":
                    Date date = new Date();
                    date.setTime((Long) property.getValue());
                    confirmation.setDate(date);
                    break;
            }
        }
        return confirmation;
    }
}
