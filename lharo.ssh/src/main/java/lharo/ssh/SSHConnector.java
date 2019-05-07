package lharo.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
 
public class SSHConnector {
 
    private static final String ENTER_KEY = "n";
    private Session session;
 
    public void connect(String username, String password, String host, int port)
        throws JSchException, IllegalAccessException {
        if (this.session == null || !this.session.isConnected()) {
            JSch jsch = new JSch();
 
            this.session = jsch.getSession(username, host, port);
            this.session.setPassword(password); 
            this.session.setConfig("StrictHostKeyChecking", "no");

            this.session.connect();
        } else {
            throw new IllegalAccessException("Sesion SSH ya iniciada.");
        }
    }
 
    public final String executeCommand(String command)
        throws IllegalAccessException, JSchException, IOException {
        if (this.session != null && this.session.isConnected()) {
 
            ChannelExec channelExec = (ChannelExec) this.session.
                openChannel("exec");
 
            InputStream in = channelExec.getInputStream();
 
            channelExec.setCommand(command);
            channelExec.connect();
 
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String linea;
 
            while ((linea = reader.readLine()) != null) {
                builder.append(linea);
                builder.append(ENTER_KEY);
            }
 
            channelExec.disconnect();
 
            return builder.toString();
        } else {
            throw new IllegalAccessException("No existe sesion SSH iniciada.");
        }
    }
 
    public final void disconnect() {
        this.session.disconnect();
    }
}