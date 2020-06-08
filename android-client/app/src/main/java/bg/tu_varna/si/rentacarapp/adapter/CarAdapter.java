package bg.tu_varna.si.rentacarapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bg.tu.varna.si.model.Car;
import bg.tu_varna.si.rentacarapp.R;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private Context context;
    private List<Car> cars;
    private CompanyAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(CompanyAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CarAdapter(Context context, List<Car> cars) {
        this.context = context;
        this.cars = cars;
    }

    public void updateCarList(List<Car> newCarList) {
        cars = newCarList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car current = cars.get(position);
        String image = current.getImage();
        Log.d("Image: ", image);
        String regNumber = current.getRegNumber();
        String brand = current.getBrand();
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.regNumber.setText(regNumber);
        holder.brand.setText(brand);
        holder.image.setImageBitmap(decodedByte);


    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView regNumber;
        public TextView brand;
        public TextView priceForDay;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.car_image_view);
            regNumber = itemView.findViewById(R.id.view_reg_number);
            brand = itemView.findViewById(R.id.view_brand);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
