package com.nork.common.cache.utils;
import java.io.Closeable;

import org.apache.log4j.Logger;

public abstract class SerializeTranscoder {
	protected static Logger logger = Logger.getLogger(SerializeTranscoder.class);
	
    public abstract byte[] serialize(Object value);
    
    public abstract Object deserialize(byte[] in);
    
    public void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            	logger.error("Unable to close " + closeable, e);
            }
        }
    }
}