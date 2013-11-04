package com.seitenbau.stu.util;

import java.io.IOException;
import java.io.OutputStream;

public class StringOutputStream extends OutputStream
{

    StringBuilder _buffer = new StringBuilder();

    public void write(int da) throws IOException
    {
        _buffer.append((char) da);
    }

    public String getString()
    {
        return _buffer.toString();
    }

    public void reset()
    {
        _buffer = new StringBuilder();
    }

    @Override
    public String toString()
    {
        return getString();
    }
}