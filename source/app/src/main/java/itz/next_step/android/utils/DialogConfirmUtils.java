package itz.next_step.android.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import itz.next_step.android.R;

public class DialogConfirmUtils {

    public interface OnConfirmListener {
        void onConfirm();
    }

    public static void showConfirmDialog(
            Context context,
            String title,
            String confirmText,
            String cancelText,
            OnConfirmListener listener
    ) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_confirm);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
        }

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextView btnConfirm = dialog.findViewById(R.id.btnConfirm);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);

        tvTitle.setText(title != null ? title : "Bạn có chắc?");
        btnConfirm.setText(confirmText != null ? confirmText : "Xác nhận");
        btnCancel.setText(cancelText != null ? cancelText : "Hủy");

        btnConfirm.setOnClickListener(v -> {
            if (listener != null) listener.onConfirm();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.setCancelable(false);
        dialog.show();
    }
}
