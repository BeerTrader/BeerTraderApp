package com.jim.demo1.messaging;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.jim.demo1.R;
import com.jim.demo1.Tools.Config;
import com.jim.demo1.Tools.PreferencesManager;
import com.jim.demo1.domains.Message;
import com.jim.demo1.ui.MessageTableRow;

import java.text.SimpleDateFormat;
import java.util.Date;

import ibt.ortc.api.Ortc;
import ibt.ortc.extensibility.OnConnected;
import ibt.ortc.extensibility.OnDisconnected;
import ibt.ortc.extensibility.OnException;
import ibt.ortc.extensibility.OnMessage;
import ibt.ortc.extensibility.OnReconnected;
import ibt.ortc.extensibility.OnReconnecting;
import ibt.ortc.extensibility.OnSubscribed;
import ibt.ortc.extensibility.OnUnsubscribed;
import ibt.ortc.extensibility.OrtcClient;
import ibt.ortc.extensibility.OrtcFactory;

public class ChatActivity extends Activity {

    private OrtcClient client;
    private String channel;
    private String user;
    private static boolean mIsInForegroundMode;
    final public static String ORTC_TAG = "ORTC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        channel = getIntent().getStringExtra("channel");
        setTitle(channel);
        user = getIntent().getStringExtra("user");

        final TextView charNumber = (TextView) findViewById(R.id.charNumber);
        charNumber.setText("" + 260);

        final EditText text = (EditText) this.findViewById(R.id.editMessage);

        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    refreshUI(user + ":" + text.getText());
                }
                return false;
            }
        });

        text.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                charNumber.setText("" + (260 - text.getText().toString().length()));
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}

        });

        final Button sendBtn = (Button) findViewById(R.id.btnSend);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = PreferencesManager.getInstance(getApplicationContext()).loadUser();
                String msg = text.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat(Config.DATE_FORMAT);
                client.send(channel,new Message(user,msg,sdf.format(new Date())).toString());
                text.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
                text.getText().clear();
            }
        });

        try {
            Ortc ortc = new Ortc();

            OrtcFactory factory;

            factory = ortc.loadOrtcFactory("IbtRealtimeSJ");

            client = factory.createClient();

            client.setHeartbeatActive(true);

        } catch (Exception e) {
        }

        if (client != null) {
            try {

                client.onConnected = new OnConnected() {

                    public void run(final OrtcClient sender) {
                        runOnUiThread(new Runnable() {

                            public void run() {
                                Log.i(ORTC_TAG, "Connected");
                                client.subscribe(channel,true,new OnMessage() {
                                    @Override
                                    public void run(OrtcClient ortcClient, String channel, final String message) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                refreshUI(message);
                                            }
                                        });

                                    }
                                });
                            }
                        });
                    }
                };

                client.onDisconnected = new OnDisconnected() {

                    public void run(OrtcClient arg0) {
                        runOnUiThread(new Runnable() {

                            public void run() {
                                Log.i(ORTC_TAG, "Disconnected");
                            }
                        });
                    }
                };

                client.onSubscribed = new OnSubscribed() {

                    public void run(OrtcClient sender, final String channel) {
                        runOnUiThread(new Runnable() {

                            public void run() {
                                Log.i(ORTC_TAG, "Subscribed to " + channel);
                                sendBtn.setEnabled(true);
                            }
                        });
                    }
                };

                client.onUnsubscribed = new OnUnsubscribed() {

                    public void run(OrtcClient sender, final String channel) {
                        runOnUiThread(new Runnable() {

                            public void run() {
                                Log.i(ORTC_TAG, "Unsubscribed from " + channel);
                            }
                        });
                    }
                };

                client.onException = new OnException() {

                    public void run(OrtcClient send, final Exception ex) {
                        runOnUiThread(new Runnable() {

                            public void run() {
                                Log.e(ORTC_TAG, "Exception " + ex.toString());
                            }
                        });
                    }
                };

                client.onReconnected = new OnReconnected() {

                    public void run(final OrtcClient sender) {
                        runOnUiThread(new Runnable() {

                            public void run() {
                                Log.i(ORTC_TAG, "Reconnected");
                            }
                        });
                    }
                };

                client.onReconnecting = new OnReconnecting() {

                    public void run(OrtcClient sender) {
                        runOnUiThread(new Runnable() {

                            public void run() {
                                Log.i(ORTC_TAG, "Reconnecting");

                            }
                        });
                    }
                };
            } catch (Exception e) {
                Log.e("Exception ",e.toString());
            }

            client.setConnectionMetadata(Config.METADATA);

            if(Config.CLUSTERURL != null){
                client.setClusterUrl(Config.CLUSTERURL);
            } else {
                client.setUrl(Config.CLUSTERURL);
            }
            client.connect(Config.APPKEY, Config.TOKEN);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsInForegroundMode = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsInForegroundMode = true;
    }

    public static boolean isInForeground() {
        return mIsInForegroundMode;
    }

    private void refreshUI(String message){
        String[] parts = message.split(":");
        Message newMsg = new Message(parts[0], parts[1], new Date().toString());

        TableLayout tableMessages = (TableLayout) findViewById(R.id.tableMessages);

        if (newMsg.user.equals(PreferencesManager.getInstance(ChatActivity.this).loadUser())) {
            new MessageTableRow(ChatActivity.this, tableMessages, true, newMsg);
        } else {
            new MessageTableRow(ChatActivity.this, tableMessages, false, newMsg);
        }
    }
}
