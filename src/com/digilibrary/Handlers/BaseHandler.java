package com.digilibrary.Handlers;

import com.digilibrary.Dao.IDao;
import com.digilibrary.Dao.SqlDao;
import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.io.*;

//has stream read/write operations for the handlers to read the request
//taken from online examples
public class BaseHandler {
    protected Gson gson = new Gson();
    protected IDao dao = new SqlDao(); //Should pass in specific type by DI, simplifying for this example
    /*
    The writeString method shows how to write a String to an OutputStream.
    */
    protected void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    /*
    The readString method shows how to write a String to an OutputStream.
    */
    protected String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /**
     * This encodes java objects into JSON
     *
     * @param obj      The object to encode
     * @param respBody The output stream
     */
    protected void encode(Object obj, OutputStream respBody) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(respBody);
        writer.write(gson.toJson(obj));
        writer.flush();

    }
}
