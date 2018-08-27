package org.zgl.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class HttpClientPostImpl {
    private static final Charset charset_utf8 = Charset.forName("UTF-8");
    private static final HttpClientPostImpl instance = new HttpClientPostImpl();

    public HttpClientPostImpl() {
    }

    public static HttpClientPostImpl getInstance() {
        return instance;
    }

    public boolean isCanConnect(String _url) {
        try {
            (new URL(_url)).openConnection().connect();
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public String sendHttp(String _url, String _param, boolean isRead) throws Exception {
        return this.sendHttp(_url, _param, 10000, 600000, isRead);
    }

    public String sendHttp(String _url, String _param, int connectTimeout, int readTimeout, boolean isRead) throws Exception {
        URL url = new URL(_url);
        URLConnection openConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)openConnection;
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type","application/octet-stream; charset=UTF-8");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(connectTimeout);
        httpURLConnection.setReadTimeout(readTimeout);
        httpURLConnection.connect();
        DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
        byte[] writeStrBytes = _param.getBytes(charset_utf8);
        dos.writeInt(writeStrBytes.length);
        dos.write(writeStrBytes);
        dos.flush();
        dos.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        if (isRead) {
            //同步返回
            DataInputStream dis = new DataInputStream(inputStream);
            byte[] readStrBytes = new byte[dis.readInt()];
            dis.readFully(readStrBytes);
            String _return = new String(readStrBytes, charset_utf8);
            dis.close();
            httpURLConnection.disconnect();
            return _return;
        } else {
            return null;
        }
    }

    public byte[] sendHttp(String _url, int msgId, byte[] data, boolean isRead) throws Exception {
        return this.sendHttp(_url, msgId, data, 10000, 600000, isRead);
    }

    public byte[] sendHttp(String _url, int msgId, byte[] data, int connectTimeout, int readTimeout, boolean isRead) throws Exception {
        URL url = new URL(_url);
        URLConnection openConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)openConnection;
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type","application/octet-stream; charset=UTF-8");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(connectTimeout);
        httpURLConnection.setReadTimeout(readTimeout);
        httpURLConnection.connect();
        DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
        dos.writeShort(msgId);
        dos.writeInt(data.length);
        dos.write(data);
        dos.flush();
        dos.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        if (isRead) {
            //同步返回
            DataInputStream dis = new DataInputStream(inputStream);
            byte[] readStrBytes = new byte[dis.readInt()];
            dis.readFully(readStrBytes);
            dis.close();
            httpURLConnection.disconnect();
            return readStrBytes;
        } else {
            return null;
        }
    }
    public byte[] sendHttp(String _url, byte[] data, boolean isRead) throws Exception {
        return this.sendHttp(_url, data, 10000, 600000, isRead);
    }

    public byte[] sendHttp(String _url, byte[] data, int connectTimeout, int readTimeout, boolean isRead) throws Exception {
        URL url = new URL(_url);
        URLConnection openConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)openConnection;
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type","application/octet-stream; charset=UTF-8");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(connectTimeout);
        httpURLConnection.setReadTimeout(readTimeout);
        httpURLConnection.connect();
        DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
        dos.writeInt(data.length);
        dos.write(data);
        dos.flush();
        dos.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        if (isRead) {
            //同步返回
            DataInputStream dis = new DataInputStream(inputStream);
            byte[] readStrBytes = new byte[dis.readInt()];
            dis.readFully(readStrBytes);
            dis.close();
            httpURLConnection.disconnect();
            return readStrBytes;
        } else {
            return null;
        }
    }
    public byte[] sendHttpTest(String _url,byte[] data) throws Exception {
        URL url = new URL(_url);
        URLConnection openConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)openConnection;
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type","application/octet-stream; charset=UTF-8");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setConnectTimeout(10000);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.connect();
        DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());

        dos.writeByte(19);
        dos.writeShort(1024);
        dos.writeInt(5);
        dos.writeShort(-7);
        dos.writeInt(data.length);
        dos.write(data);
        dos.flush();
        dos.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        if (true) {
            //同步返回
            DataInputStream dis = new DataInputStream(inputStream);
            byte[] readStrBytes = new byte[dis.readInt()];
            dis.readFully(readStrBytes);
            dis.close();
            httpURLConnection.disconnect();
            return readStrBytes;
        } else {
            return null;
        }
    }

}