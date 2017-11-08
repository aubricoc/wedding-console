package cat.aubricoc.weddingconsole;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private List<? extends Card> cards;

    private OnCardClickListener onCardClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView subtitle;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            this.text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public CardsAdapter(List<? extends Card> cards) {
        this.cards = cards;
    }

    @Override
    public CardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCardClickListener != null) {
                    onCardClickListener.onCardClick((Card) v.getTag());
                }
            }
        });
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Card card = cards.get(position);

        holder.title.setText(card.getTitle());
        holder.subtitle.setText(card.getSubtitle());
        holder.text.setText(card.getText());

        holder.itemView.setTag(card);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setOnCardClickListener(OnCardClickListener onCardClickListener) {
        this.onCardClickListener = onCardClickListener;
    }

    public interface OnCardClickListener {
        void onCardClick(Card card);
    }
}