package ru.ic218.wallpapermanager.data.remote.client;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * @author Nikolay Vlaskin on 15.03.2018.
 */

public class TLSSocketFactory extends SSLSocketFactory {

    private SSLSocketFactory delegate;
    private SSLContext mSSLContext;
    private String[] mCiphers;
    private String[] mProtocols;

    public TLSSocketFactory() throws KeyManagementException, NoSuchAlgorithmException {
        mSSLContext = SSLContext.getInstance("TLS");
        mSSLContext.init(null, null, null);
        mProtocols = GetProtocolList();
        mCiphers = GetCipherList();
        delegate = mSSLContext.getSocketFactory();
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return mCiphers;
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return mCiphers;
    }

    @Override
    public Socket createSocket() throws IOException {
        return enableTLSOnSocket(delegate.createSocket());
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(s, host, port, autoClose));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return enableTLSOnSocket(delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        return enableTLSOnSocket(delegate.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(address, port, localAddress, localPort));
    }

    private Socket enableTLSOnSocket(Socket socket) {
        if(socket != null && (socket instanceof SSLSocket)) {
            //((SSLSocket)socket).setEnabledProtocols(new String[] {"TLSv1", "TLSv1.1",});
            ((SSLSocket)socket).setEnabledProtocols(mProtocols);
            ((SSLSocket)socket).setEnabledCipherSuites(mCiphers);
            //((SSLSocket)socket).setEnabledCipherSuites(new String[] {"TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384"});
        }
        return socket;
    }

    protected String[] GetProtocolList()
    {
        String[] protocols = { "TLSv1", "TLSv1.1"};
        String[] availableProtocols = null;

        SSLSocket socket = null;

        try
        {
            SSLSocketFactory factory = mSSLContext.getSocketFactory();
            socket = (SSLSocket)factory.createSocket();

            availableProtocols = socket.getSupportedProtocols();
        }
        catch(Exception e)
        {
            return new String[]{ "TLSv1" };
        }
        finally
        {
            if(socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                }
        }

        List<String> resultList = new ArrayList<String>();
        for(int i = 0; i < protocols.length; i++)
        {
            int idx = Arrays.binarySearch(availableProtocols, protocols[i]);
            if(idx >= 0)
                resultList.add(protocols[i]);
        }

        return resultList.toArray(new String[0]);
    }

    protected String[] GetCipherList()
    {
        List<String> resultList = new ArrayList<String>();
        SSLSocketFactory factory = mSSLContext.getSocketFactory();
        for(String s : factory.getSupportedCipherSuites()){
            Log.e("CipherSuite type = ",s);
            resultList.add(s);
        }
        return resultList.toArray(new String[resultList.size()]);
    }

}
