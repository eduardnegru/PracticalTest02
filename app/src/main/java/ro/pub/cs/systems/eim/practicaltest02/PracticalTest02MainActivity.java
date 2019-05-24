package ro.pub.cs.systems.eim.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest02MainActivity extends AppCompatActivity {

    EditText serverPortEditText;
    EditText clientPortEditText;
    EditText clientAddressEditText;
    EditText clientUrlEditText;

    Button serverStartButton;
    Button clientConnectButton;
    ServerThread serverThread = null;
    ClientThread clientThread = null;
    TextView urlViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = findViewById(R.id.server_port_edit_text);
        clientPortEditText = findViewById(R.id.client_port_edit_text);
        clientAddressEditText = findViewById(R.id.client_address_edit_text);
        clientUrlEditText = findViewById(R.id.client_url);

        serverStartButton = findViewById(R.id.server_start_button);
        clientConnectButton = findViewById(R.id.client_connect_button);

        urlViewer = findViewById(R.id.client_html_viewer);

        serverStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String serverPort = serverPortEditText.getText().toString();
                if (serverPort == null || serverPort.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }

                serverThread = new ServerThread(Integer.parseInt(serverPort));

                if (serverThread.getServerSocket() == null) {
                    Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                    return;
                }

                serverThread.start();

                Toast.makeText(getApplicationContext(), "Server started", Toast.LENGTH_SHORT).show();

            }
        });


        clientConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String clientAddress = clientAddressEditText.getText().toString();
                String clientPort = clientPortEditText.getText().toString();


                if (serverThread == null || !serverThread.isAlive()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = String.valueOf(clientUrlEditText.getText());

                if (url == null || url == "")
                {
                    Toast.makeText(getApplicationContext(), "Invalid url", Toast.LENGTH_SHORT).show();
                    return;
                }

                clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), url, urlViewer);
                clientThread.start();
            }
        });



    }
}
