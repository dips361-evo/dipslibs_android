package com.evo.mitzoom.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dhairytripathi.library.EditTextPin;
import com.evo.mitzoom.R;
import com.evo.mitzoom.ui.DipsWaitingRoom;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class frag_form_opening extends Fragment {
    private Context context;
    private ImageView iconKtp, iconNpwp, iconSignature, iconForm, preview_ktp, preview_npwp, preview_signature;;
    private TextView Nama,NIK,Email,Alamat,Agama,Status, Timer, Resend_Otp;
    private Button btnProcess;
    private LayoutInflater inflater;
    private View dialogView;
    private Button btnVerifikasi;
    private EditTextPin editTextPin;
    private Handler handlerSuccess;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_form_opening_account, container, false);
        iconKtp = view.findViewById(R.id.icon_ktp);
        iconNpwp = view.findViewById(R.id.icon_npwp);
        iconSignature = view.findViewById(R.id.icon_signature);
        iconForm = view.findViewById(R.id.icon_form);
        Nama = view.findViewById(R.id.et_nama);
        NIK = view.findViewById(R.id.et_nik);
        Email = view.findViewById(R.id.et_email);
        Alamat = view.findViewById(R.id.et_alamat);
        Agama = view.findViewById(R.id.et_agama);
        Status = view.findViewById(R.id.et_status);
        btnProcess = view.findViewById(R.id.btnProses);
        preview_ktp = view.findViewById(R.id.Imageview_ktp);
        preview_npwp = view.findViewById(R.id.Imageview_npwp);
        preview_signature = view.findViewById(R.id.Imageview_tanda_tangan);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iconKtp.setBackgroundTintList(context.getResources().getColorStateList(R.color.bg_cif_success));
        iconNpwp.setBackgroundTintList(context.getResources().getColorStateList(R.color.bg_cif_success));
        iconSignature.setBackgroundTintList(context.getResources().getColorStateList(R.color.bg_cif_success));
        iconForm.setBackgroundTintList(context.getResources().getColorStateList(R.color.bg_cif));

        Nama.setText("Andi Setiawan");
        NIK.setText("323432342304203");
        Alamat.setText("Rt.15 Rw/20 Maju, Kecamatan Suka Mulya, DKI Jakarta, Jawa Barat 12345");
        Agama.setText("Islam");
        Status.setText("Belum Kawin");

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconForm.setBackgroundTintList(context.getResources().getColorStateList(R.color.bg_cif_success));
                PopUp();
            }
        });
    }

    private void PopUp(){
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.item_otp,null);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        sweetAlertDialog.setCustomView(dialogView);
        sweetAlertDialog.hideConfirmButton();
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
        btnVerifikasi = dialogView.findViewById(R.id.btnVerifikasi);
        Timer = dialogView.findViewById(R.id.timer_otp);
        Resend_Otp = dialogView.findViewById(R.id.btn_resend_otp);
        editTextPin = dialogView.findViewById(R.id.kode_otp);
        btnVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPin.getPin().equalsIgnoreCase("")){
                    Toast.makeText(context, "Kode Otp masih kosong", Toast.LENGTH_SHORT).show();
                }
                else {
                    editTextPin.getPin().toString();
                    PopUpSuccesOtp();
                }
            }
        });
    }
    private void PopUpSuccesOtp(){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setTitleText("Kode OTP Sesuai");
        sweetAlertDialog.setContentText("Formulir sedang dalam proses.\nHarap menunggu beberapa saat");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
    }
    private void PopUpSuccesRegistration(){
        handlerSuccess = new Handler();
        handlerSuccess.postDelayed(new Runnable() {
            @Override
            public void run() {
                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                    sweetAlertDialog.setContentText(getResources().getString(R.string.headline_success));
                    sweetAlertDialog.setConfirmText(getResources().getString(R.string.btn_continue));
                    sweetAlertDialog.show();
                    Button btnConfirm = (Button) sweetAlertDialog.findViewById(cn.pedant.SweetAlert.R.id.confirm_button);
                    btnConfirm.setBackgroundTintList(context.getResources().getColorStateList(R.color.Blue));
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    });
            }
        },10000);
    }

}
