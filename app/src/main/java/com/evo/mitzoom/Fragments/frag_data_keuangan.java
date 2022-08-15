package com.evo.mitzoom.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evo.mitzoom.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frag_data_keuangan extends Fragment {
    private Context context;
    private Button btnProses, btnKembali;
    private byte[] KTP, NPWP, TTD;
    private LinearLayout iconKtp, iconNpwp, iconSignature, iconForm;
    private AutoCompleteTextView jenisPekerjaan, penghasilanBulan, sumberDana, tujuanPenggunaan, penghasilanTambahan, jenisRekening, namaProduk, mataUang, perkiraan, frekuensi, perkiraan2, frekuensi2, perkiraan3, frekuensi3, perkiraan4, frekuensi4;
    private String [] jenisPekerjaan_, penghasilanBulan_, sumberDana_, tujuanPenggunaan_, penghasilanTambahan_, jenisRekening_, namaProduk_, mataUang_, perkiraan_, frekuensi_;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_data_keuangan, container, false);
        jenisPekerjaan = view.findViewById(R.id.act_jenisPekerjaan);
        penghasilanBulan = view.findViewById(R.id.act_penghasilanBulan);
        sumberDana = view.findViewById(R.id.act_sumberDana);
        tujuanPenggunaan = view.findViewById(R.id.act_tujuanPenggunaan);
        penghasilanTambahan = view.findViewById(R.id.act_penghasilanTambahan);
        jenisRekening = view.findViewById(R.id.act_jenisRekening);
        namaProduk = view.findViewById(R.id.act_namaProduk);
        mataUang = view.findViewById(R.id.act_mataUang);
        perkiraan = view.findViewById(R.id.act_perkiraan);
        frekuensi = view.findViewById(R.id.act_frekuensi);
        perkiraan2 = view.findViewById(R.id.act_perkiraan2);
        frekuensi2 = view.findViewById(R.id.act_frekuensi2);
        perkiraan3 = view.findViewById(R.id.act_perkiraan3);
        frekuensi3 = view.findViewById(R.id.act_frekuensi3);
        perkiraan4 = view.findViewById(R.id.act_perkiraan4);
        frekuensi4 = view.findViewById(R.id.act_frekuensi4);

        btnProses = view.findViewById(R.id.btnProses_Keuangan);
        btnKembali = view.findViewById(R.id.btnKembali_Keuangan);
        iconKtp = view.findViewById(R.id.icon_ktp);
        iconNpwp = view.findViewById(R.id.icon_npwp);
        iconSignature = view.findViewById(R.id.icon_signature);
        iconForm = view.findViewById(R.id.icon_form);
        iconKtp.setBackgroundTintList(context.getResources().getColorStateList(R.color.bg_cif_success));
        iconNpwp.setBackgroundTintList(context.getResources().getColorStateList(R.color.bg_cif_success));
        iconSignature.setBackgroundTintList(context.getResources().getColorStateList(R.color.bg_cif_success));
        iconForm.setBackgroundTintList(context.getResources().getColorStateList(R.color.bg_cif));
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jenisPekerjaan_ = new String[]{"Karyawan","Wiraswasta"};
        penghasilanBulan_ = new String[]{"< 10jt","> 10 - 50jt","> 50 - 100jt","> 100 - 500jt", "> 500jt"};
        sumberDana_ = new String[]{"Gaji","Bisnis/Usaha","Tabungan Pribadi"};
        tujuanPenggunaan_ = new String[]{"Pengeluaran Rutin", "Pembayaran", "Bisnis"};
        penghasilanTambahan_ = new String[]{"< 10jt","> 10 - 50jt","> 50 - 100jt","> 100 - 500jt", "> 500jt"};
        jenisRekening_ = new String[]{"Giro","Tabungan","Deposito","Pinjaman"};
        namaProduk_ = new String[]{"Tabungan A","Tabungan B"};
        mataUang_ = new String[]{"Rupiah"};
        perkiraan_ = new String[]{"< 10jt","> 10 - 50jt","> 50 - 100jt","> 100 - 500jt","> 500jt"};
        frekuensi_ = new String[]{"0-10 Kali","11-20 Kali", "21-30 Kali"};

        ArrayAdapter<String> jp= new ArrayAdapter<String>(context,R.layout.list_item, jenisPekerjaan_);
        jenisPekerjaan.setAdapter(jp);

        ArrayAdapter<String> pb= new ArrayAdapter<String>(context,R.layout.list_item, penghasilanBulan_);
        penghasilanBulan.setAdapter(pb);

        ArrayAdapter<String> sd= new ArrayAdapter<String>(context,R.layout.list_item, sumberDana_);
        sumberDana.setAdapter(sd);

        ArrayAdapter<String> tp= new ArrayAdapter<String>(context,R.layout.list_item, tujuanPenggunaan_);
        tujuanPenggunaan.setAdapter(tp);

        ArrayAdapter<String> pt= new ArrayAdapter<String>(context,R.layout.list_item, penghasilanTambahan_);
        penghasilanTambahan.setAdapter(pt);

        ArrayAdapter<String> jk= new ArrayAdapter<String>(context,R.layout.list_item, jenisRekening_);
        jenisRekening.setAdapter(jk);

        ArrayAdapter<String> np= new ArrayAdapter<String>(context,R.layout.list_item, namaProduk_);
        namaProduk.setAdapter(np);

        ArrayAdapter<String> mu= new ArrayAdapter<String>(context,R.layout.list_item, mataUang_);
        mataUang.setAdapter(mu);

        ArrayAdapter<String> p= new ArrayAdapter<String>(context,R.layout.list_item, perkiraan_);
        perkiraan.setAdapter(p);
        perkiraan2.setAdapter(p);
        perkiraan3.setAdapter(p);
        perkiraan4.setAdapter(p);

        ArrayAdapter<String> f= new ArrayAdapter<String>(context,R.layout.list_item, frekuensi_);
        frekuensi.setAdapter(p);
        frekuensi2.setAdapter(p);
        frekuensi3.setAdapter(p);
        frekuensi4.setAdapter(p);

        Bundle arg = getArguments();
        KTP = arg.getByteArray("ktp");
        NPWP = arg.getByteArray("npwp");
        TTD = arg.getByteArray("ttd");
        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iconForm.setBackgroundTintList(context.getResources().getColorStateList(R.color.bg_cif_success));
                PopUpSuccesRegistration();
            }
        });
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new frag_data_pekerjaan();
                Bundle bundle = new Bundle();
                bundle.putByteArray("ktp",KTP);
                bundle.putByteArray("npwp",NPWP);
                bundle.putByteArray("ttd",TTD);
                fragment.setArguments(bundle);
                getFragmentPage(fragment);
            }
        });
    }
    private void PopUpSuccesRegistration(){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setTitleText(getResources().getString(R.string.reg_title));
        sweetAlertDialog.setContentText(getResources().getString(R.string.reg_content));
        sweetAlertDialog.setConfirmText(getResources().getString(R.string.activation));
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
        Button btnConfirm = (Button) sweetAlertDialog.findViewById(cn.pedant.SweetAlert.R.id.confirm_button);
        btnConfirm.setBackgroundTintList(context.getResources().getColorStateList(R.color.Blue));
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                getFragmentPage(new frag_aktivasi_ibmb());
                sweetAlertDialog.dismiss();
            }
        });
    }
    private void getFragmentPage(Fragment fragment){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_frame2, fragment)
                .addToBackStack(null)
                .commit();
    }
}
