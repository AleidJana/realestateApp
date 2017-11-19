package tabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lama.realestateapp.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class tab1 extends Fragment {

    View view;
    ImageView chartView;
    URL url;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
         view = inflater.inflate(R.layout.fragment_tab1, container, false);
        chartView = (ImageView) view.findViewById(R.id.imageView);


/*        String zipcode = getArguments().getString("zipcode");

        try {
             url = new URL("http://www.zillow.com/webservice/GetChart.htm?zws-id=X1-ZWz18wlnzn0miz_7oq0o&unit-type=percent&zpid=48749425&width=300&height=150&zpid=" + zipcode);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DownloadImageTask dow = new DownloadImageTask(chartView);
        dow.execute(url+"");

*/
        return view;

    }


    /**
     * Created by Dori on 2/18/2017.
     */
    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView mImage) {
            this.bmImage = mImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mBitmap = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mBitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                // Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mBitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}