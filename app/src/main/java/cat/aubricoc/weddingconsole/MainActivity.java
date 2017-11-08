package cat.aubricoc.weddingconsole;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.recyclerView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(ConfirmationsActivity.class);
            }
        });
        findViewById(R.id.quiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(QuizActivity.class);
            }
        });
    }

    private void goTo(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
