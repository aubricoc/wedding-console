package cat.aubricoc.weddingconsole;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CompetitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        Competitor competitor = (Competitor) getIntent().getSerializableExtra(Constants.COMPETITOR_EXTRA);

        setTitle(competitor.getName() + " - " + competitor.getPoints() + " Points");

        List<Question> questions = getQuestions(getLanguage());

        QuestionsAdapter questionsAdapter = new QuestionsAdapter(questions, competitor.getAnswers());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(questionsAdapter);
    }

    private String getLanguage() {
        String language = Locale.getDefault().getLanguage();
        if (language.equalsIgnoreCase("ca")) {
            return "ca";
        }
        return "es";
    }

    private List<Question> getQuestions(String language) {
        int fileId = R.raw.questions_es;
        if (language.equals("ca")) {
            fileId = R.raw.questions_ca;
        }
        InputStream inputStream = getResources().openRawResource(fileId);
        Reader reader = new InputStreamReader(inputStream);
        Type listType = new TypeToken<ArrayList<Question>>() {
        }.getType();
        return new Gson().fromJson(reader, listType);
    }
}
