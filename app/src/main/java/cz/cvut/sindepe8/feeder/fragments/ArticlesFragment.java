package cz.cvut.sindepe8.feeder.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.models.ArticleModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
private IArticlesFragmentListener listener;
    private LinearLayout articlesLayout;
    private View.OnClickListener onClickListener;
    private List<ArticleModel> displayedArticles;

    public ArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof IArticlesFragmentListener)
        {
            this.listener = (IArticlesFragmentListener)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        this.listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        articlesLayout = (LinearLayout) view.findViewById(R.id.ArticlesLinearLayout);

        return view;
    }

    public void DisplayArticles(List<ArticleModel> articles)
    {
        // Clear
        articlesLayout.removeAllViews();

        displayedArticles = articles;

        // For each article add a TextView
        for(ArticleModel article : articles)
        {
            TextView view = new TextView(getActivity());
            view.setText(article.getTitle());
            view.setPadding(0, 4, 0,4 );
            view.setOnClickListener(this);
            articlesLayout.addView(view); // ASK: Jak toto zefektivnit (zamezit opětovnému přepočtu layoutu)
        }
    }

    @Override
    public void onClick(View view) {
        int index = articlesLayout.indexOfChild(view);
        if(index >= 0)
            listener.ArticleSelected(displayedArticles.get(index));
    }

    public interface IArticlesFragmentListener{
        void ArticleSelected(ArticleModel article);
    }
}

