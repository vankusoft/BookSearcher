package com.jgntic.ivan.booksearcher;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ivan on 9/16/2018.
 */

public class SearchForBook {

    private static final String URL_ADDRESS="https://www.googleapis.com/books/v1/volumes?";

    private static final String QUERY_PARAM="q";

    private static final String MAX_RESULTS="maxResults";

    private static final String PRINT_TYPE="printType";

    Context context;
    String query;

    private HttpURLConnection httpURLConnection=null;
    private BufferedReader bufferedReader=null;
    private String JSONString=null;

    public SearchForBook(Context context, String query){
        this.context=context;
        this.query=query;

        openConnection();
    }

    private void openConnection(){
        try {

            Uri uri = Uri.parse(URL_ADDRESS).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, query)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            URL url = new URL(uri.toString());

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String makeSearch() {
        try{
            if(httpURLConnection!=null){
                InputStream inputStream=httpURLConnection.getInputStream();

                if(inputStream!=null){

                    bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer stringBuffer=new StringBuffer();

                    String line="";

                    while ((line=bufferedReader.readLine())!=null){
                        stringBuffer.append(line+ "\n");
                    }

                    JSONString=stringBuffer.toString();
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }

            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return JSONString;
    }

}
