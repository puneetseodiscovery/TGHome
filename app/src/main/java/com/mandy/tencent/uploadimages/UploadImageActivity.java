package com.mandy.tencent.uploadimages;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mandy.tencent.ProgressRequestBody;
import com.mandy.tencent.R;
import com.mandy.tencent.controller.Controller;
import com.mandy.tencent.nointernet.NoInternetActivity;
import com.mandy.tencent.termsandcondition.TermsAndConditionActivity;
import com.mandy.tencent.thanku.ThanksActivity;
import com.mandy.tencent.upgrade.UpgradeActivity;
import com.mandy.tencent.upgrade.UpgradeApis;
import com.mandy.tencent.uploadimages.adapter.OnItemClick;
import com.mandy.tencent.uploadimages.adapter.UploadImageAdapter;
import com.mandy.tencent.util.CheckInternet;
import com.mandy.tencent.util.ProgressBarClass;
import com.mandy.tencent.util.SharedToken;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class UploadImageActivity extends AppCompatActivity implements Controller.UploadFile, ProgressRequestBody.UploadCallbacks {

    @BindView(R.id.spinnerItems)
    Spinner spinnerItems;
    @BindView(R.id.recyclerImage)
    RecyclerView recyclerImage;
    @BindView(R.id.edtComment)
    EditText edtComment;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.txtBack)
    TextView txtBack;

    ProgressBar progressBar;

    private static final int SELECT_VIDEOS = 1;
    private static final int SELECT_VIDEOS_KITKAT = 1;

    List<String> selectedVideos;
    ArrayList<String> arrayVideo;
    UploadImageAdapter adapter;
    @BindView(R.id.roundimageUpload)
    RoundedImageView roundimageUpload;

    Controller controller;
    Dialog dialog;
    SharedToken sharedToken;
    MultipartBody.Part file;
    ArrayList<MultipartBody.Part> arrayList = new ArrayList<>();
    String rooms, m2, t1, t2, comment, dropData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        ButterKnife.bind(this);
        controller = new Controller(this);
        dialog = ProgressBarClass.showProgressDialog(this);
        sharedToken = new SharedToken(this);
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);

        setSpinnerData();

        rooms = getIntent().getStringExtra("room");
        m2 = getIntent().getStringExtra("m2");
        t1 = getIntent().getStringExtra("radio1");
        t2 = getIntent().getStringExtra("radio2");

        roundimageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < 19) {
                    Intent intent = new Intent();
                    intent.setType("video/mp4");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select videos"), SELECT_VIDEOS);
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setType("video/mp4");
                    startActivityForResult(intent, SELECT_VIDEOS_KITKAT);
                }
            }
        });


    }

    @OnClick({R.id.btnSubmit, R.id.txtBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                comment = edtComment.getText().toString();
                if (TextUtils.isEmpty(edtComment.getText().toString())) {
                    edtComment.setError("add comment");
                } else if (dropData.equals("Select item")) {
                    Toast.makeText(this, "Select the item", Toast.LENGTH_SHORT).show();
                } else {
                    if (CheckInternet.isInternetAvailable(this)) {
                        dialog.show();
                        controller.setUploadFile(sharedToken.getUserId(), rooms, m2, t1, t2, dropData, comment, arrayList);
                    } else {
                        startActivity(new Intent(this, NoInternetActivity.class));
                    }
                }
                break;

            case R.id.txtBack:
                onBackPressed();
                break;
        }
    }


    //set the spinner data
    private void setSpinnerData() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select item");
        arrayList.add("Homestyling & Homestaging");
        arrayList.add("Furnishing private home");
        arrayList.add("Interior decoration online & homestyling");
        arrayList.add("Tips homestyling for photos & viewing");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItems.setAdapter(arrayAdapter);

        spinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dropData = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void setRecyclerImage() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerImage.setLayoutManager(gridLayoutManager);
        adapter = new UploadImageAdapter(this, arrayVideo);
        recyclerImage.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClick(new OnItemClick() {
            @Override
            public void onClick(int position) {
                if (Build.VERSION.SDK_INT < 19) {
                    Intent intent = new Intent();
                    intent.setType("video/mp4");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select videos"), SELECT_VIDEOS);
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setType("video/mp4");
                    startActivityForResult(intent, SELECT_VIDEOS_KITKAT);
                }
            }
        });
    }

    private List<String> getSelectedVideos(int requestCode, Intent data) {

        List<String> result = new ArrayList<>();

        ClipData clipData = data.getClipData();
        if (clipData != null) {
            for (int i = 0; i < clipData.getItemCount(); i++) {
                ClipData.Item videoItem = clipData.getItemAt(i);
                Uri videoURI = videoItem.getUri();
                String filePath = getPath(this, videoURI);
                result.add(filePath);
            }
        } else {
            Uri videoURI = data.getData();
            String filePath = getPath(this, videoURI);
            result.add(filePath);
        }

        return result;
    }

    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            selectedVideos = getSelectedVideos(requestCode, data);
            Log.d("++++++path", selectedVideos.toString());
            arrayVideo = new ArrayList<>(selectedVideos);
            setRecyclerImage();
            roundimageUpload.setVisibility(View.GONE);

            for (int i = 0; i < arrayVideo.size(); i++) {
                File file1 = new File(arrayVideo.get(i));
                ProgressRequestBody fileBody = new ProgressRequestBody(file1, UploadImageActivity.this);
//                RequestBody videoBody = RequestBody.create(MediaType.parse("*/*"), file1);
                file = MultipartBody.Part.createFormData("file[]", file1.getName(), fileBody);
                arrayList.add(file);
            }

            Log.d("+++++++++path", arrayVideo.toString());

        }

    }


    @Override
    public void onSucess(Response<UploadApis> response) {
        dialog.dismiss();
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ThanksActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "" + response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressUpdate(int percentage) {
        progressBar.setProgress(percentage);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {
        progressBar.setProgress(100);
    }
}
