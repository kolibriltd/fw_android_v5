package com.anstar.fieldwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.anstar.models.Account;


public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splech_scrin);
        if (isLogin()) {
            Intent i = new Intent(this, DashboardActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        finish();
    }

    public boolean isLogin() {
        Account account = Account.getUser();
        if (account != null) {
            if (account.isLogin) {
                return true;

            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
