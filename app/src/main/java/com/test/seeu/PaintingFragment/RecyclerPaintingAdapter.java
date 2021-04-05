package com.test.seeu.PaintingFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.test.seeu.R;

import java.util.List;

public class RecyclerPaintingAdapter extends RecyclerView.Adapter<RecyclerPaintingAdapter.PrintingViewHolder> {

    private List<PaintingModel> printingList;
    Context context;

    public RecyclerPaintingAdapter(Context context) {
        this.context = context;
    }

    public void setPrintingList(List<PaintingModel> printingList) {
        this.printingList = printingList;
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
        holder.onBind(printingList.get(position));
    }

    @Override
    public int getItemCount() {
        return printingList.size();
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
            txtDetails = itemView.findViewById(R.id.txtDetails);
            imgPainting = itemView.findViewById(R.id.imgPainting);
        }

        public void onBind(PaintingModel paintingModel) {
            txtName.setText(paintingModel.getName());
            txtAuthor.setText(paintingModel.getAuthor());
            txtDetails.setText(paintingModel.getDetails());
            Glide.with(context)
                .asBitmap()
                .load(paintingModel.getImageURL())
                .into(imgPainting);
        }
    }
}
