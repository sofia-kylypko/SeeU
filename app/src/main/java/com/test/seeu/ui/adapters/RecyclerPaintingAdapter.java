package com.test.seeu.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.test.seeu.R;
import com.test.seeu.data.FirebaseHelper;
import com.test.seeu.data.models.PaintingModel;
import com.test.seeu.ui.activities.ActivityInfo;

import java.util.ArrayList;
import java.util.List;

public class RecyclerPaintingAdapter extends RecyclerView.Adapter<RecyclerPaintingAdapter.PrintingViewHolder> {

    private List<PaintingModel> paintingList = new ArrayList();
    private Context paintContext;

    public RecyclerPaintingAdapter(Context paintContext) {
        this.paintContext = paintContext;

    }
    public void setPaintingList(List<PaintingModel> paintingList) {
        this.paintingList = paintingList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PrintingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.container, parent, false);
        PrintingViewHolder holder = new PrintingViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PrintingViewHolder holder, int position) {
        holder.onBind(paintingList.get(position));
    }

    @Override
    public int getItemCount() {
        return paintingList.size();
    }

    public class PrintingViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtAuthor;
        private TextView txtDetails;
        private ImageView imgPainting;

        public PrintingViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtDetails = itemView.findViewById(R.id.txtPreviewInfo);
            imgPainting = itemView.findViewById(R.id.imgPhoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positionIndex = getAdapterPosition();
                    PaintingModel paintingModel = paintingList.get(positionIndex);
                    Intent intent = new Intent(paintContext, ActivityInfo.class);
                    intent.putExtra("image", paintingModel.getPhoto());
                    intent.putExtra("name", paintingModel.getName());
                    intent.putExtra("author", paintingModel.getAuthor());
                    intent.putExtra("info", paintingModel.getMainInfo());
                    paintContext.startActivity(intent);
                }
            });
        }

        public void onBind(PaintingModel paintingModel) {
            txtName.setText(paintingModel.getName());
            txtAuthor.setText(paintingModel.getAuthor());
            txtDetails.setText(paintingModel.getPreviewInfo());
            FirebaseHelper.getInstance()
                    .getReference(paintingModel.getPhoto())
                    .getDownloadUrl()
                    .addOnSuccessListener(uri -> Glide.with(imgPainting.getContext())
                            .asBitmap()
                            .load(uri)
                            .into(imgPainting))
            .addOnFailureListener(e -> Log.e("Firebase storage:",e.getLocalizedMessage()));
        }
    }
}
