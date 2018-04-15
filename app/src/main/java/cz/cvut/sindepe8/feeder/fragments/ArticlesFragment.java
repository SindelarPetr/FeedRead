package cz.cvut.sindepe8.feeder.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Dictionary;
import java.util.List;

import cz.cvut.sindepe8.feeder.R;
import cz.cvut.sindepe8.feeder.models.ArticleModel;
import cz.cvut.sindepe8.feeder.services.FeedService;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesFragment extends android.support.v4.app.Fragment {
    private IArticlesFragmentListener listener;
    private ListView articlesListView;
    private View.OnClickListener onClickListener;
    private List<ArticleModel> displayedArticles;
    private Dictionary<View, Integer> viewArticleDictionary;

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
        articlesListView = view.findViewById(R.id.articles_list_view);
        articlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleModel article = (ArticleModel) parent.getItemAtPosition(position);

                listener.ArticleSelected(article.getId());
            }
        });
        DisplayArticles();

        return view;
    }

    private void DisplayArticles()
    {
        List<ArticleModel> articles = FeedService.GetAllArticles();

        // We need to create an adapter for our ListView of articles
        ArticlesAdapter adapter = new ArticlesAdapter(getContext(), articles);
        articlesListView.setAdapter(adapter);
    }

    public interface IArticlesFragmentListener{
        void ArticleSelected(int articleId);
    }

    public class ArticlesAdapter extends ArrayAdapter<ArticleModel>{
        public ArticlesAdapter(Context context, List<ArticleModel> articles){
            super(context, 0, articles);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ArticleModel article = getItem(position);
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_article, parent, false);
            }

            TextView tvTitle = convertView.findViewById(R.id.article_title);

            tvTitle.setText(article.getTitle());
            return convertView;
        }
    }
}

