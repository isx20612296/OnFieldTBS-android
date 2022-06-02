package com.example.onfieldtbs_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.onfieldtbs_android.databinding.ActivityEmployeeProfileBinding;
import com.example.onfieldtbs_android.models.Employee;
import com.example.onfieldtbs_android.service.api.ApiClient;
import com.example.onfieldtbs_android.service.api.RetrofitCallBack;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;
import com.example.onfieldtbs_android.ui.views.components.IncidenceTableFragment;

public class EmployeeProfile extends AppCompatActivity {

    // private static String PHONE_NUMBER;
    // private final int REQUEST_CALL_PHONE = 123;
    private ActivityEmployeeProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        String employeeId = bundle.getString("employeeId");
        ApiClient.getApi().getEmployeeById(employeeId).enqueue((RetrofitCallBack<Employee>) (call, response) -> {
            Employee employee = response.body();
            String fullEmployeeName = employee.getName() + " " + employee.getLastname();
            String fullEmployeeExt = employee.getPhoneExt() == null ? "" : getApplicationContext().getResources().getString(R.string.profile_employee_extension) + employee.getPhoneExt();
            binding.profileEmployeeFullName.setText(fullEmployeeName);
            binding.profileEmployeeCompany.setText(employee.getCompany().getName());
            binding.profileEmployeeExt.setText(fullEmployeeExt);
            binding.profileEmployeeEmail.setText(employee.getEmail());
            binding.profileEmployeePhone.setText(employee.getDirectPhone());

            // Call button TODO(llamadas texto no)
            binding.profileEmployeeCallButton.setVisibility(View.GONE);
//            String buttonText = getApplicationContext().getResources().getString(R.string.profile_employee_call_to);
//            if (employee.getDirectPhone() == null){
//                buttonText += employee.getCompany().getName();
//                binding.profileEmployeeCallButton.setText(buttonText);
//                binding.profileEmployeeCallButton.setOnClickListener(view -> {
//                    checkPermissionsAndCall(employee.getCompany().getPhone());
//                });
//            } else {
//                buttonText += employee.getName();
//                binding.profileEmployeeCallButton.setText(buttonText);
//                binding.profileEmployeeCallButton.setOnClickListener(view -> {
//                    checkPermissionsAndCall(employee.getDirectPhone());
//                });
//            }

            final IncidencesViewModel incidencesViewModel = new ViewModelProvider(this).get(IncidencesViewModel.class);
            incidencesViewModel.getEmployeeIncidences(employeeId).observe(this, incidences -> {
                IncidenceTableFragment incidenceTableFragment = new IncidenceTableFragment(incidences);
                getSupportFragmentManager().beginTransaction().replace(R.id.profileEmployeeTableFragment, incidenceTableFragment).commit();
            });


        });
    }

    // TODO(llamadas)
//    private void checkPermissionsAndCall(String phone) {
//        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            PHONE_NUMBER = phone;
//            ActivityCompat.requestPermissions(
//                    this,
//                    new String[]{Manifest.permission.CALL_PHONE},
//                    REQUEST_CALL_PHONE);
//        } else {
//            makeCall(phone);
//        }
//    }
//
//    private void makeCall(String phone) {
//        Intent callIntent =new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:" + phone));
//        startActivity(callIntent);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 123:
//
//                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    makeCall(PHONE_NUMBER);
//                } else {
//                    Log.d("TAG", "Call Permission Not Granted");
//                }
//                break;
//
//            default:
//                break;
//        }
//    }
}