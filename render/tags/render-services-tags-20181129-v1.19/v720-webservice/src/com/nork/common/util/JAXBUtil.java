package com.nork.common.util;

import com.nork.pano.model.scene.Image;
import com.nork.pano.model.scene.ImageSphere;
import com.nork.pano.model.scene.Scene;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by Administrator on 2017/7/1.
 */
public class JAXBUtil {

    /**
     * JAVA对象转xml
     * @param obj
     * @param classz
     * @return
     * @throws JAXBException
     */
    public static String beanToXml(Object obj,Class<?> classz){
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(classz);
            Marshaller marshaller = jaxbContext.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(obj, sw);
        }catch(JAXBException e){
            e.printStackTrace();
        }
        return sw.toString();
    }

    /**
     * xml转JAVA对象
     * @param xml
     * @param classz
     * @return
     */
    public static <T> T xmlToBean(String xml,Class<?> classz){
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(classz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Object obj = unmarshaller.unmarshal(new StringReader(xml));
            if( obj != null ){
                return (T)obj;
            }else{
                return null;
            }
        }catch(JAXBException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Scene scene1 = new Scene();
        scene1.setName("scene_1");
        Image image1 = new Image();
        ImageSphere sphere1 = new ImageSphere();
        sphere1.setUrl("%BASEDIR%/images/C09_0006_001_658624_659203.jpg");
        image1.setSphere(sphere1);
        scene1.setImage(image1);

        String xml = JAXBUtil.beanToXml(scene1,Scene.class);
        //System.out.println(xml);

        Scene scene = JAXBUtil.xmlToBean(xml,Scene.class);
        //System.out.println(scene.getName());
    }
}
