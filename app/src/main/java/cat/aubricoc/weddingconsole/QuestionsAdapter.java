package cat.aubricoc.weddingconsole;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<Question> questions;

    private Map<Integer, Integer> answers;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView question;
        public Button answer1;
        public Button answer2;
        public Button answer3;
        public Button answer4;

        public ViewHolder(View itemView) {
            super(itemView);
            this.question = (TextView) itemView.findViewById(R.id.question);
            this.answer1 = (Button) itemView.findViewById(R.id.answer_1);
            this.answer2 = (Button) itemView.findViewById(R.id.answer_2);
            this.answer3 = (Button) itemView.findViewById(R.id.answer_3);
            this.answer4 = (Button) itemView.findViewById(R.id.answer_4);
        }
    }

    public QuestionsAdapter(List<Question> questions, Map<Integer, Integer> answers) {
        this.questions = questions;
        this.answers = answers;
    }

    @Override
    public QuestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_question, parent, false);
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Question question = questions.get(position);

        List<Answer> options = question.getAnswers();
        holder.question.setText(question.getQuestion());
        holder.answer1.setText(options.get(0).getAnswer());
        holder.answer2.setText(options.get(1).getAnswer());
        holder.answer3.setText(options.get(2).getAnswer());
        holder.answer4.setText(options.get(3).getAnswer());

        int answer = answers.get(question.getId());
        holder.answer1.setActivated(true);
        holder.answer1.setSelected(answer == 1);
        holder.answer2.setSelected(answer == 2);
        holder.answer3.setSelected(answer == 3);
        holder.answer4.setSelected(answer == 4);

        holder.itemView.setTag(question);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}