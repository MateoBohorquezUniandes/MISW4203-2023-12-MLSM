package co.edu.uniandes.misw4203.group18.backvynils;

import android.os.IBinder;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import androidx.test.espresso.Root;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ToastMatcher extends TypeSafeMatcher<Root> {

    @Override
    public void describeTo(Description description) {
        description.appendText("is toast");
    }

    @Override
    protected boolean matchesSafely(Root item) {
        int type = item.getWindowLayoutParams().get().type;
        if (type == LayoutParams.TYPE_TOAST) {
            IBinder windowToken = item.getDecorView().getWindowToken();
            IBinder appToken = item.getDecorView().getApplicationWindowToken();
            if (windowToken == appToken) {
                return true;
            }
        }
        return false;
    }

    public static ToastMatcher isToast() {
        return new ToastMatcher();
    }
}
