package tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lama.realestateapp.MainActivity;
import com.example.lama.realestateapp.R;
import com.example.lama.realestateapp.SecoundActivity;
import com.example.lama.realestateapp.googleMab;

public class tab1 extends Fragment {
    View view;
    TextView txt1;
    TextView txt2 ;
    TextView txt3 ;
    TextView txt4;
    TextView txt5;
    String[] comp2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
         view = inflater.inflate(R.layout.fragment_tab1, container, false);
        txt1=(TextView)view.findViewById(R.id.city);
        txt2= (TextView)view.findViewById(R.id.street);
        txt3= (TextView)view.findViewById(R.id.state);
        txt4= (TextView)view.findViewById(R.id.zipid);
        txt5=(TextView)view.findViewById(R.id.amount);

        comp2=this.getArguments().getStringArray("info");
        txt1.setText(comp2[2]);
        txt2.setText(comp2[0]);
        txt3.setText(comp2[3]);
        txt4.setText(comp2[1]);
        txt5.setText(comp2[6]);
        Button btn = (Button)view.findViewById(R.id.map);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putStringArray("info", comp2);
                Intent intent = new Intent(getActivity(), googleMab.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
     return view;

    }


}