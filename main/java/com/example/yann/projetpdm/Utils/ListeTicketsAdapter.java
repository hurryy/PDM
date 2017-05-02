package com.example.yann.projetpdm.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yann.projetpdm.R;
import com.example.yann.projetpdm.classes.Ticket;
import com.example.yann.projetpdm.classes.Zone;

import java.text.DecimalFormat;

/**
 * Created by Yann_TOUR on 02/05/2017.
 */

public class ListeTicketsAdapter extends ArrayAdapter<Ticket> {
    public static String IDT = "idt";
    private final Context context;
    private final Ticket[] values;
    //private ImageView image;
    private TextView txtDateDemande;
    private TextView txtEtat;
    private TextView txtPrix;
    private TextView txtZone;
    public ListeTicketsAdapter(Context context, Ticket[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_liste_tickets, parent, false);
        txtDateDemande = (TextView) rowView.findViewById(R.id.layout_liste_tickets_dateDemande);
        txtEtat = (TextView) rowView.findViewById(R.id.layout_liste_tickets_etat);
        txtPrix = (TextView) rowView.findViewById(R.id.layout_liste_tickets_prix);
        txtZone = (TextView) rowView.findViewById(R.id.layout_liste_tickets_zone);
        txtDateDemande.setText(values[position].getId() + "  -  " + DateHelper.dateJour(values[position].getDateDemande()));
        if(values[position].isValid()){
            txtEtat.setText("Valide");
            txtEtat.setBackgroundResource(R.color.LIGHT_GREEN);
        }else {
            txtEtat.setText("Terminé");
            txtEtat.setBackgroundResource(R.color.LIGHT_RED);
        }
        String pattern = "###.##";
        DecimalFormat decimalFormat= new DecimalFormat(pattern);

        txtPrix.setText(decimalFormat.format(values[position].getCoutTotal()) + "€");
        txtZone.setText(new Zone(getContext(), values[position].getIdZone()).getNom());
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

    public Ticket getItemAtPosition(int position){
        return values[position];
    }

    public Ticket getListe(int position){
        return values[position];
    }
}
