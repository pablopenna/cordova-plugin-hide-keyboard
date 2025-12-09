package com.rockmary.cordova.plugins;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class HideKeyboardPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        Activity activity = this.cordova.getActivity();
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    @Override
    public void pluginInitialize() {
        super.pluginInitialize();

        // Hide keyboard when app starts
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) cordova.getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(webView.getView().getWindowToken(), 0);
            }
        });
    }

    @Override
    public void onResume(boolean multitasking) {
        // Keep keyboard hidden on resume
        cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) cordova.getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(webView.getView().getWindowToken(), 0);
            }
        });
    }
}

