package com.example.pingxixi.ui.person;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.pingxixi.MyApplication;
import com.example.pingxixi.R;
import com.example.pingxixi.ui.recyclerview.DetailFragment;
import com.example.pingxixi.ui.recyclerview.EditGoodsFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.linchaolong.android.imagepicker.ImagePicker;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DetailPersonFragment extends Fragment {
    View view;
    //picture
    ImageView picture;
    private ImagePicker imagePicker = new ImagePicker();
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();
    String username;
    String gender;
    String id;
    String fileurl;

    public static DetailPersonFragment newInstance() {
        return new DetailPersonFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_detail_person, container, false);
        getActivity().setTitle("个人信息");
        username = getActivity().getIntent().getStringExtra("username");
        String url = new MyApplication().selectuser;
        RequestParams params = new RequestParams(url);
        //get
        System.out.println("username为"+username);
        params.addQueryStringParameter("un", username);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @SuppressLint("ResourceType")
            @Override
            public void onSuccess(String result) {
                Log.i("DetailPersonFragment",result);
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                mData.addAll(list);
                System.out.println(mData.size());
                picture = (ImageView) view.findViewById(R.id.person_photo);
                picture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startCameraOrGallery();
                    }
                });
                EditText name = view.findViewById(R.id.person_userName);
                EditText userId = view.findViewById(R.id.person_user_id);
                EditText des = view.findViewById(R.id.person_description);
                EditText pwd = view.findViewById(R.id.person_password);
                RadioButton male = view.findViewById(R.id.radioButton2);
                RadioButton female = view.findViewById(R.id.radioButton3);
                id = (String) mData.get(0).get("id");
                System.out.println("id 为"+id);
                gender = (String) mData.get(0).get("gender");
                if (gender.equals("男")){
                    male.setChecked(true);
                }else {
                    female.setChecked(true);
                }
                RadioGroup group = view.findViewById(R.id.person_group);

                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton rb = view.findViewById(i);
                        Toast.makeText(getContext(),rb.getText(),Toast.LENGTH_LONG).show();
                        gender = rb.getText().toString();
                        System.out.println("性别为：" + gender);
                    }
                });
                Button save = view.findViewById(R.id.person_save);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name_c = name.getText().toString();
                        String des_c = des.getText().toString();
                        System.out.println(des_c);
                        String na = userId.getText().toString();
                        String pwd_c = pwd.getText().toString();
                        String url1 = new MyApplication().uu;
                        //String url="http://172.16.26.242:8080/androidweb/UpdateServlet";
                        RequestParams params = new RequestParams(url1);
                        //post
                        params.setMultipart(true);
                        params.addBodyParameter("id", id);
                        params.addBodyParameter("name", name_c);
                        params.addBodyParameter("description", des_c);
                        params.addBodyParameter("username", na);
                        params.addBodyParameter("password", pwd_c);
                        params.addBodyParameter("gender", gender);
                        params.addBodyParameter("uploadedfile",new File(fileurl));

                        x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.i("DetailPersonFragment2",result);
                                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFinished() {

                            }
                        });

                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.nav_home);
                    }
                });
                Button clear = view.findViewById(R.id.person_clear);
                //showpicture
                Glide.with(getActivity()).load(mData.get(0).get("image").toString()).placeholder(R.mipmap.ic_launcher).into(picture);
                name.setText((String)mData.get(0).get("name"));
                userId.setText((String)mData.get(0).get("username"));
                des.setText((String)mData.get(0).get("description"));
                pwd.setText((String)mData.get(0).get("password"));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });

        return view;
    }

    //picture
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private void startCameraOrGallery() {
        new AlertDialog.Builder(getActivity()).setTitle("设置图片")
                .setItems(new String[]{"从相册中选取图片", "拍照"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 回调
                        ImagePicker.Callback callback = new ImagePicker.Callback() {
                            @Override
                            public void onPickImage(Uri imageUri) {
                            }

                            @Override
                            public void onCropImage(Uri imageUri) {
                                //picture.setImageURI(imageUri);
                                Glide.with(getActivity()).load(new File(imageUri.getPath())).into(picture);
                                fileurl = imageUri.getPath();
                            }
                        };
                        if (which == 0) {
                            // 从相册中选取图片
                            imagePicker.startGallery(DetailPersonFragment.this, callback);
                        } else {
                            // 拍照
                            imagePicker.startCamera(DetailPersonFragment.this, callback);
                        }
                    }
                })
                .show()
                .getWindow()
                .setGravity(Gravity.BOTTOM);
    }
}