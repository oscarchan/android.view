package android.view;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import android.app.Activity;
import android.widget.Toast;

public class ProxyUtil
{
    static class ToastInvocationHandler implements InvocationHandler
    {
        private Activity mActivity;
        private Object mWrappedObj;
        
        public ToastInvocationHandler(Activity activity, Object wrappedObj)
        {
            mActivity = activity;
            mWrappedObj = wrappedObj;
        }
        
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            Toast.makeText(mActivity, method.getName() + "(" + Arrays.asList(args) + ")", Toast.LENGTH_SHORT).show();
            
            Object returned = null;
            
            if(mWrappedObj!=null)
                returned = method.invoke(mWrappedObj, args);
            
            return returned;
        }
    }
    
    public static <T> T getToastProxy(Activity activity, Class<T> clazz, T object)
    {
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, new ToastInvocationHandler(activity, object));
        return proxy;
        
    }
}
