package itz.next_step.android.ui.main.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import eu.davidea.flexibleadapter.databinding.BR;
import itz.next_step.android.R;
import itz.next_step.android.data.model.api.request.login.CandidateLoginRequest;
import itz.next_step.android.databinding.ActivityLoginBinding;
import itz.next_step.android.di.component.ActivityComponent;
import itz.next_step.android.ui.base.activity.BaseActivity;
import itz.next_step.android.ui.main.MainActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        setUpPassword();
        setUpValidation();
    }
    public void onBackMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("from_login", true);
        startActivity(intent);
        finish();
    }

    public void onSignUpClick() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
    @SuppressLint("ClickableViewAccessibility")
    public void setUpPassword() {
        viewBinding.password.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2; // 0: left, 1: top, 2: right, 3: bottom
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (viewBinding.password.getRight() - viewBinding.password.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                    // Đảo trạng thái hiển thị mật khẩu
                    if (viewBinding.password.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                        // Hiển thị mật khẩu
                        viewBinding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        viewBinding.password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.eye_open, 0);
                    } else {
                        // Ẩn mật khẩu
                        viewBinding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        viewBinding.password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.eye_closed, 0);
                    }
                    // Đặt lại con trỏ
                    viewBinding.password.setSelection(viewBinding.password.getText().length());
                    return true;
                }
            }
            return false;
        });
    }

    public void onLoginClick() {
        hideKeyboard();
        String phone = viewBinding.phone.getText().toString().trim();
        String password = viewBinding.password.getText().toString().trim();

        boolean hasError = false;

        if (phone.isEmpty()) {
            viewBinding.phone.setBackgroundResource(R.drawable.bg_text_box_select);
            viewBinding.mgsErPhone.setVisibility(View.VISIBLE);
            hasError = true;
        }

        if (password.isEmpty()) {
            viewBinding.password.setBackgroundResource(R.drawable.bg_text_box_select);
            viewBinding.mgsErPassword.setVisibility(View.VISIBLE);
            hasError = true;
        }

        if (hasError) return;

        CandidateLoginRequest request = new CandidateLoginRequest();
        request.setPhone(viewBinding.phone.getText().toString().trim());
        request.setPassword(viewBinding.password.getText().toString().trim());

        viewModel.candidateLogin(request);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setUpValidation() {
        viewBinding.phone.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String phone = viewBinding.phone.getText().toString().trim();
                if (phone.isEmpty()) {
                    viewBinding.phone.setBackgroundResource(R.drawable.bg_text_box_select);
                    viewBinding.mgsErPhone.setVisibility(View.VISIBLE);
                }
            }
        });

        viewBinding.password.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String password = viewBinding.password.getText().toString().trim();
                if (password.isEmpty()) {
                    viewBinding.password.setBackgroundResource(R.drawable.bg_text_box_select);
                    viewBinding.mgsErPassword.setVisibility(View.VISIBLE);
                }
            }
        });

        viewBinding.phone.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    viewBinding.phone.setBackgroundResource(R.drawable.bg_text_box_un_select);
                    viewBinding.mgsErPhone.setVisibility(View.GONE);
                }
            }
        });

        viewBinding.password.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    viewBinding.password.setBackgroundResource(R.drawable.bg_text_box_un_select);
                    viewBinding.mgsErPassword.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }
}
