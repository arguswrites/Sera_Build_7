package org.tensorflow.lite.examples.imageclassification.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import org.tensorflow.lite.examples.imageclassification.R;
public class PermissionsFragment extends Fragment {
    public static boolean hasPermission(Context context) {
        return ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher
            = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(requireContext(),
                                    "Permission request granted",
                                    Toast.LENGTH_LONG)
                            .show();
                    navigateToCamera();
                } else {
                    Toast.makeText(requireContext(),
                                    "Permission request denied",
                                    Toast.LENGTH_LONG)
                            .show();
                }
            });

    @Override
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            navigateToCamera();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void navigateToCamera() {
        Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(PermissionsFragmentDirections.actionPermissionsToCamera());
    }
}
