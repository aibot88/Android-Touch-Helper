package com.zfdang.touchhelper;

import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.zfdang.TouchHelperApp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.CharsetDecoder;

public class Utilities {
    static final String TAG = "Utilities";

    static public void printNodeStack(AccessibilityNodeInfo node) {
        Log.d(TAG, "Show Node information: ");
        String indent = "";
        while (node != null) {
            Log.d(TAG, indent + "class = " + node.getClassName() + " id = " + node.getWindowId() + " label = " + node.getText());
            node = node.getParent();
            indent += "  ";
        }
    }

    static public String getTraceStackInString(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        if (e != null) {
            e.printStackTrace(pw);
        }
        return sw.toString();
    }

    public static void toast(CharSequence cs) {
        Toast.makeText(TouchHelperApp.getAppContext(), cs, Toast.LENGTH_SHORT).show();
    }

    public static String describeAccessibilityNode(AccessibilityNodeInfo e) {
        if (e == null) {
            return "null";
        }
//        return e.toString();
        String result = "";
        String nodeType = "";
        if (e.getText()!=null){
            nodeType = "p";
        } else if (e.isClickable()){
            nodeType = "button";
        } else if (e.getClassName().toString().contains("ImageView")){
            nodeType = "img";
        } else{
            nodeType = "div";
        }
        if (e.getChildCount() == 0 && e.isVisibleToUser()){
            String htmlTag = nodeType;
            result = String.format(
                    "<%s%s%s> %s </%s>\n",
                    nodeType,
                    (e.getWindowId() != 0) ? " id=" + e.getWindowId() : "",
                    (e.getClassName() != null) ? " class=\"" + String.join(" ", e.getClassName()) + "\"" : "",
                    (e.getContentDescription() != null) ? " alt=\"" + e.getContentDescription() + "\"" : "",
                    (e.getText() != null) ? e.getText() + ", " : "",
                    htmlTag
            );
        }
        return result;
    /*

        result += " class=" + e.getClassName().toString();

        final Rect rect = new Rect();
        e.getBoundsInScreen(rect);
        result += String.format(" Position=[%d, %d, %d, %d]", rect.left, rect.right, rect.top, rect.bottom);


        CharSequence id = e.getViewIdResourceName();
        if(id != null) {
            result += " ResourceId=" + id.toString();
        }

        CharSequence description = e.getContentDescription();
        if(description != null) {
            result += " Description=" + description.toString();
        }

        CharSequence text = e.getText();
        if(text != null) {
            result += " Text=" + text.toString();
        }
        CharSequence packageName = e.getPackageName();
        if(packageName != null) {
            result += " packageName=" + packageName.toString();
        }

        return result;
    */
    }

}
