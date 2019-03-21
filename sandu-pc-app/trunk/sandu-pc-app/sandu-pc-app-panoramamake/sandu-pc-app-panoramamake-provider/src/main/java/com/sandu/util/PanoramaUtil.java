package com.sandu.util;


import com.sandu.panorama.model.roam.Position;
import com.sandu.panorama.model.roam.Roam;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/7/13.
 */
public class PanoramaUtil {

    public static String getAth(Roam roamA, Roam roamB){
        double x = 0,y = 0;
        x = roamB.getPosition().getZ() - roamA.getPosition().getZ();
        y = roamB.getPosition().getX() - roamA.getPosition().getX();
        double result = Math.atan2(y,x);
        return ((result * 180 / Math.PI)-roamA.getRotation().getY())+"";
    }

    public static String getAtv(Roam roamA, Roam roamB){
        double x = 0,y = 0;
        y = roamB.getPosition().getY() - roamA.getPosition().getY();
        x = distance(roamA.getPosition(),roamB.getPosition());
        Double result = Math.asin(y/x);
        return result.toString();
    }

    /**
     * 获取两个坐标的距离
     * @param ap
     * @param bp
     * @return
     */
    public static double distance(Position ap, Position bp){
        double d = (bp.getX()-ap.getX())*(bp.getX()-ap.getX()) + (bp.getZ()-ap.getZ())*(bp.getZ()-ap.getZ());
        return Math.sqrt(d);
    }

    /**
     * 通过两个位置之间的具体排序。
     * 为了当两个热点坐标位置相等时，把距离近的覆盖到距离远的热点上
     */
//    public static class ComparatorT implements Comparator {
//        @Override
//        public int compare(Object o1, Object o2) {
//            Hotspot hotspot1 = (Hotspot) o1;
//            Hotspot hotspot2 = (Hotspot) o2;
//            if( hotspot1.getDistance() > hotspot2.getDistance() ){
//                return -1;
//            }
//            if( hotspot1.getDistance() < hotspot2.getDistance() ){
//                return 1;
//            }
//            return 0;
//        }
//    }

}
