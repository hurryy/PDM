package com.example.yann.projetpdm.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yann.projetpdm.R;
import com.example.yann.projetpdm.classes.Voiture;

/**
 * Created by Yann_TOUR on 02/05/2017.
 */

public class ListeVoitureAdapter extends ArrayAdapter<Voiture> {
    private final Context context;
    private final Voiture[] values;
    //private ImageView image;
    private TextView txtImmatriculation;
    private TextView txtMarque;
    private TextView txtModel;
    public ListeVoitureAdapter(Context context, Voiture[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_liste_voitures, parent, false);
        txtImmatriculation = (TextView) rowView.findViewById(R.id.layout_liste_voitures_immatriculation);
        txtMarque = (TextView) rowView.findViewById(R.id.layout_liste_voitures_marque);
        txtModel = (TextView) rowView.findViewById(R.id.layout_liste_voitures_model);
        //image = (ImageView) rowView.findViewById(R.id.layout_liste_voitures_image);
        txtImmatriculation.setText(values[position].getImmatriculation());
        txtMarque.setText(values[position].getMarque());
        txtModel.setText(values[position].getModel());
        /*Bitmap original = BitmapFactory.decodeFile(values[position].getImage());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        original.compress(Bitmap.CompressFormat.PNG, 100, out);
        original = null;
        System.gc();
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
        image.setImageBitmap(decoded);*/
        //image.setImageURI(Uri.fromFile(new File((values[position].getImage()))));
        return rowView;
    }

    public Voiture getListe(int position){
        return values[position];
    }
}
