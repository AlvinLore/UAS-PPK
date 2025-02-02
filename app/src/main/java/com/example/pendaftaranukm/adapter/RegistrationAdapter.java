package com.example.pendaftaranukm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pendaftaranukm.R;
import com.example.pendaftaranukm.api.ApiService;
import com.example.pendaftaranukm.api.RetrofitClient;
import com.example.pendaftaranukm.model.Registration;
import com.example.pendaftaranukm.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationAdapter extends RecyclerView.Adapter<RegistrationAdapter.ViewHolder> {
    private Context context;
    private List<Registration> registrationList;
    private OnRegistrationActionListener onRegistrationActionListener;

    public RegistrationAdapter(Context context, List<Registration> registrationList, OnRegistrationActionListener onRegistrationActionListener) {
        this.context = context;
        this.registrationList = registrationList;
        this.onRegistrationActionListener = onRegistrationActionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_registration, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Registration registration = registrationList.get(position);
        User user = registration.getUser();

        holder.tvUserName.setText(user.getName());
        holder.tvUserEmail.setText(user.getEmail());
        holder.tvUserNim.setText(user.getNim());

        holder.btnApprove.setOnClickListener(v -> onRegistrationActionListener.onApproveOrReject(registration, true));
        holder.btnReject.setOnClickListener(v -> onRegistrationActionListener.onApproveOrReject(registration, false));
    }

    @Override
    public int getItemCount() {
        return registrationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvUserEmail, tvUserNim;
        Button btnApprove, btnReject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
            tvUserNim = itemView.findViewById(R.id.tvUserNim);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }

    public interface OnRegistrationActionListener {
        void onApproveOrReject(Registration registration, boolean approve);
    }
}
