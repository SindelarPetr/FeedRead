package cz.cvut.sindepe8.feeder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.models.ArticleModel;
import cz.cvut.sindepe8.feeder.services.StorageService;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {


    private TextView headerTextView;
    private TextView contentTextView;

    public ArticleFragment() {
        // Required empty public constructor
    }

    public void DisplayArticle(int articleId)
    {
        ArticleModel article = StorageService.GetArticleById(articleId);

        if(article == null)
            return;

        headerTextView.setText(article.getTitle());
        contentTextView.setText(article.getContent());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        // Get header and content TextViews
        headerTextView = view.findViewById(R.id.ArticleTitleTextView);
        contentTextView = view.findViewById(R.id.ArticleContentTextView);

        return view;
    }

}
