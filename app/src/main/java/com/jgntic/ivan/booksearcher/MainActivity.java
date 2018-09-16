package com.jgntic.ivan.booksearcher;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    TextView bookTitle;
    TextView bookAuthor;
    EditText etSearchBook;
    Button searchBttn;

    String stringQuery="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

       if(getSupportLoaderManager().getLoader(0)!=null){
           getSupportLoaderManager().initLoader(0,null,this);
       }
    }

    private void init(){

        bookTitle=findViewById(R.id.bookTitle);
        bookAuthor=findViewById(R.id.bookAuthor);
        etSearchBook=findViewById(R.id.etSearchBook);
        searchBttn=findViewById(R.id.btnSearchBook);

    }

    public void searchBook(View view) {
        stringQuery=etSearchBook.getText().toString();

        if(!stringQuery.isEmpty()){
            Bundle bundle=new Bundle();
            bundle.putString("query",stringQuery);
            getSupportLoaderManager().restartLoader(0,bundle,this);

        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader(this,args.getString("query"));
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        try{
            JSONObject jsonObject= new JSONObject(data);
            JSONArray jsonArray=jsonObject.getJSONArray("items");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject book=jsonArray.getJSONObject(i);
                JSONObject details=book.getJSONObject("volumeInfo");

                String title=details.getString("title");
                String author=details.getString("authors");

                bookTitle.setText(title);
                bookAuthor.setText(author);

                return;
            }


        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
