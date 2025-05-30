package itz.next_step.android.ui.main.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import eu.davidea.flexibleadapter.databinding.BR;
import itz.next_step.android.R;
import itz.next_step.android.data.model.api.request.candidate.CandidateSignUpRequest;
import itz.next_step.android.databinding.ActivitySignupBinding;
import itz.next_step.android.di.component.ActivityComponent;
import itz.next_step.android.ui.base.activity.BaseActivity;

public class SignUpActivity extends BaseActivity<ActivitySignupBinding, SignUpViewModel> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        setUpValidation();
        setUpRePassword();
        setUpPassword();
    }
    public void onSignUpClick() {
        hideKeyboard();
        if (!isValidForm()) return;

        CandidateSignUpRequest request = new CandidateSignUpRequest();

        request.setFullName(viewBinding.name.getText().toString().trim());
        request.setEmail(viewBinding.email.getText().toString().trim());
        request.setPhone(viewBinding.phone.getText().toString().trim());
        request.setPassword(viewBinding.password.getText().toString().trim());

        viewModel.signUpCandidate(request);
    }

    public Boolean isValidForm() {
        String name = viewBinding.name.getText().toString().trim();
        String email = viewBinding.email.getText().toString().trim();
        String phone = viewBinding.phone.getText().toString().trim();
        String password = viewBinding.password.getText().toString().trim();
        String rePassword = viewBinding.rePassword.getText().toString().trim();

        boolean noError = true;

        // Tên
        if (name.isEmpty()) {
            setError(viewBinding.name, viewBinding.mgsErName, getString(R.string.err_name));
            noError = false;
        }

        // Email
        if (email.isEmpty()) {
            setError(viewBinding.email, viewBinding.mgsErEmail, getString(R.string.err_email));
            noError = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setError(viewBinding.email, viewBinding.mgsErEmail, getString(R.string.err_email_2));
            noError = false;
        }

        // SĐT
        if (phone.isEmpty()) {
            setError(viewBinding.phone, viewBinding.mgsErPhone, getString(R.string.err_phone));
            noError = false;
        } else if (!phone.matches("^[0-9]{10,11}$")) {
            setError(viewBinding.phone, viewBinding.mgsErPhone, getString(R.string.err_phone_2));
            noError = false;
        }

        // Mật khẩu
        if (password.isEmpty()) {
            setError(viewBinding.password, viewBinding.mgsErPassword, getString(R.string.err_password));
            noError = false;
        } else if (password.length() < 6 || password.length() > 12) {
            setError(viewBinding.password, viewBinding.mgsErPassword, getString(R.string.err_password_2));
            noError = false;
        }

        // Nhập lại mật khẩu
        if (!rePassword.equals(password)) {
            setError(viewBinding.rePassword, viewBinding.mgsErRePassword, getString(R.string.err_password_3));
            noError = false;
        }
        return noError;
    }
    private void setUpValidation() {
        validateName();
        validateEmail();
        validatePhone();
        validatePassword();
        validateRePassword();
    }

    private void validateName() {
        viewBinding.name.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String name = viewBinding.name.getText().toString().trim();
                if (name.isEmpty()) {
                    setError(viewBinding.name, viewBinding.mgsErName, getString(R.string.err_name));
                }
            }
        });

        viewBinding.name.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    clearError(viewBinding.name, viewBinding.mgsErName);
                }
            }
        });
    }

    private void validateEmail() {
        viewBinding.email.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String email = viewBinding.email.getText().toString().trim();
                if (email.isEmpty()) {
                    setError(viewBinding.email, viewBinding.mgsErEmail, getString(R.string.err_email));
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    setError(viewBinding.email, viewBinding.mgsErEmail, getString(R.string.err_email_2));
                }
            }
        });

        viewBinding.email.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString().trim();

                if (email.isEmpty()) {
                    setError(viewBinding.email, viewBinding.mgsErEmail, getString(R.string.err_email));
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    setError(viewBinding.email, viewBinding.mgsErEmail, getString(R.string.err_email_2));
                } else {
                    clearError(viewBinding.email, viewBinding.mgsErEmail);
                }
            }
        });
    }
    private void validatePhone() {
        viewBinding.phone.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String phone = viewBinding.phone.getText().toString().trim();
                if (phone.isEmpty()) {
                    setError(viewBinding.phone, viewBinding.mgsErPhone, getString(R.string.err_phone));
                } else if (!phone.matches("^[0-9]{10,11}$")) {
                    setError(viewBinding.phone, viewBinding.mgsErPhone, getString(R.string.err_phone_2));
                }
            }
        });

        viewBinding.phone.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone = s.toString().trim();

                if (phone.isEmpty()) {
                    setError(viewBinding.phone, viewBinding.mgsErPhone, getString(R.string.err_phone));
                } else if (!phone.matches("^[0-9]{10,11}$")) {
                    setError(viewBinding.phone, viewBinding.mgsErPhone, getString(R.string.err_phone_2));
                } else {
                    clearError(viewBinding.phone, viewBinding.mgsErPhone);
                }
            }
        });
    }
    private void validatePassword() {
        viewBinding.password.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String password = viewBinding.password.getText().toString().trim();
                if (password.isEmpty()) {
                    setError(viewBinding.password, viewBinding.mgsErPassword, getString(R.string.err_password));
                } else if (password.length() < 6 || password.length() > 12) {
                    setError(viewBinding.password, viewBinding.mgsErPassword, getString(R.string.err_password_2));
                }
            }
        });

        viewBinding.password.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString().trim();

                if (password.isEmpty()) {
                    setError(viewBinding.password, viewBinding.mgsErPassword, getString(R.string.err_password));
                } else if (password.length() < 6 || password.length() > 12) {
                    setError(viewBinding.password, viewBinding.mgsErPassword, getString(R.string.err_password_2));
                } else {
                    clearError(viewBinding.password, viewBinding.mgsErPassword);
                }
            }
        });
    }
    private void validateRePassword() {
        viewBinding.rePassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String rePassword = viewBinding.rePassword.getText().toString().trim();
                String password = viewBinding.password.getText().toString().trim();
                if (!rePassword.equals(password)) {
                    setError(viewBinding.rePassword, viewBinding.mgsErRePassword, getString(R.string.err_password_3));
                }
            }
        });

        viewBinding.rePassword.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String rePassword = s.toString().trim();
                String password = viewBinding.password.getText().toString().trim();
                if (rePassword.equals(password)) {
                    clearError(viewBinding.rePassword, viewBinding.mgsErRePassword);
                }
            }
        });
    }

    private void setError(EditText editText, TextView errorText, String message) {
        editText.setBackgroundResource(R.drawable.bg_text_box_select);
        errorText.setText(message);
        errorText.setVisibility(View.VISIBLE);
    }

    private void clearError(EditText editText, TextView errorText) {
        editText.setBackgroundResource(R.drawable.bg_text_box_un_select);
        errorText.setVisibility(View.GONE);
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
    @SuppressLint("ClickableViewAccessibility")
    public void setUpRePassword() {
        viewBinding.rePassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2; // 0: left, 1: top, 2: right, 3: bottom
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (viewBinding.rePassword.getRight() - viewBinding.rePassword.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                    // Đảo trạng thái hiển thị mật khẩu
                    if (viewBinding.rePassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                        // Hiển thị mật khẩu
                        viewBinding.rePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        viewBinding.rePassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.eye_open, 0);
                    } else {
                        // Ẩn mật khẩu
                        viewBinding.rePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        viewBinding.rePassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0, R.drawable.eye_closed, 0);
                    }
                    // Đặt lại con trỏ
                    viewBinding.rePassword.setSelection(viewBinding.rePassword.getText().length());
                    return true;
                }
            }
            return false;
        });
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_signup;
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
