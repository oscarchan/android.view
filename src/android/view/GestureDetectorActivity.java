package android.view;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Toast;

public class GestureDetectorActivity extends Activity
{
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        OnGestureListener listener = (OnGestureListener) Proxy.newProxyInstance(this.getClassLoader(), new Class[] {OnGestureListener.class}, new InvocationHandler()
        {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
            {
                Toast.makeText(GestureDetectorActivity.this, method.getName() + "(" + Arrays.asList(args) + ")", Toast.LENGTH_SHORT);
                
                method.invoke(proxy, args);
                return null;
            }
        });
        mGestureDetector = new GestureDetector(this, listener);

//        setContentView(R.layout.)
    }
}
