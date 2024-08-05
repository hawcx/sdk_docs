

import com.hawcx.framework.HawcxFramework;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HawcxFramework.init(this, "YOUR_API_KEY_HERE");
    }
}

HawcxAuth.register("email", new HawcxAuth.AuthCallback() {
    // add your success and failure logic handling here
});

// User Login
HawcxAuth.login("email", new HawcxAuth.AuthCallback() {
    // add your success and failure logic handling here
});






