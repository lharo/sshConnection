package lharo.ssh;

import com.jcraft.jsch.JSchException;
import java.io.IOException;

public class SSHConnection {
 
    private static final String USERNAME = "admin";
    private static final String HOST = "localhost";
    private static final int PORT = 22;
    private static final String PASSWORD = "root";
 
    public static void main(String[] args) {
 
        try {
            SSHConnector sshConnector = new SSHConnector();
             
             
            sshConnector.connect(USERNAME, PASSWORD, HOST, PORT);
            String result = sshConnector.executeCommand("ls -l");
            sshConnector.disconnect();
             
            System.out.println(result);
        } catch (JSchException ex) {
            ex.printStackTrace();
             
            System.out.println(ex.getMessage());
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
             
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
             
            System.out.println(ex.getMessage());
        }
    }
}