package com.example.pengingatobat;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PengingatAdapter extends ArrayAdapter<Pengingat> {

    private static final int HIJAU = 0xFF16A34A;
    private static final int PINK = 0xFFEC4899;

    public PengingatAdapter(Context context, ArrayList<Pengingat> data) {
        super(context, 0, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_pengingat, parent, false);
        }

        Pengingat p = getItem(position);

        TextView tvIkon = convertView.findViewById(R.id.tvIkon);
        TextView tvNama = convertView.findViewById(R.id.tvNama);
        TextView tvDetail = convertView.findViewById(R.id.tvDetail);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);

        tvNama.setText(p.getNama());
        tvDetail.setText((p.isObat() ? "Obat" : "Vitamin") + "  •  " + p.getJam());

        if (p.isSudahMinum()) {
            tvIkon.setText("✓");
            tvIkon.setTextColor(HIJAU);
            tvIkon.setBackgroundResource(R.drawable.bg_circle_sudah);
            tvStatus.setText("Sudah diminum");
            tvStatus.setTextColor(HIJAU);
            tvNama.setPaintFlags(tvNama.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tvIkon.setText(p.isObat() ? "💊" : "🍊");
            tvIkon.setBackgroundResource(R.drawable.bg_circle_belum);
            tvStatus.setText("Belum diminum");
            tvStatus.setTextColor(PINK);
            tvNama.setPaintFlags(tvNama.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        return convertView;
    }
}