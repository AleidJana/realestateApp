package tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lama.realestateapp.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;


public class tab2 extends Fragment {
PhotoView chart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tab2,container,false);
        chart=(PhotoView)view.findViewById(R.id.photo);
        String zipcode =this.getArguments().getString("zipid");
        Picasso.with(getContext()).load("https://www.zillow.com:443/app?chartDuration=1year&chartType=partner&height=150&page=webservice%2FGetChart&service=chart&showPercent=true&width=300&zpid="+zipcode).into(chart);

        return view;
    }
}
