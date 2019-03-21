package com.sandu.common.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class ThumbnailUtil {

    /**
     * 按大小压缩
     * @param sourceFile    源文件
     * @param width         宽
     * @param height        高
     * @param keepAspectRatio   是否保持原图比例
     * @param targetPath    目标路径
     * @return              缩略图路径
     */
    public static String compressBySize(File sourceFile, int width, int height, boolean keepAspectRatio, String targetPath){
        try {
            if( width <= 0 || height <= 0 || StringUtils.isBlank(targetPath) ){
                return null;
            }
            Thumbnails.of(sourceFile).size(width,height).keepAspectRatio(keepAspectRatio).toFile(targetPath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return targetPath;
    }

    /**
     * 尺寸不变，按质量压缩图片文件大小
     * @param sourceFile    源文件
     * @param quality       质量比例（例：0.3 表示原图30%的效果）
     * @param targetPath
     * @return
     */
    public static String compressByQuality(File sourceFile, double quality, String targetPath){
        try {
            if( quality >= 1 || Double.isNaN(quality) || Double.isInfinite(quality) || StringUtils.isBlank(targetPath) ){
                return null;
            }
            Thumbnails.of(sourceFile).scale(1).outputQuality(quality).toFile(targetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return targetPath;
    }

    /**
     * 按比例压缩
     * @param sourceFile    源文件
     * @param scale         压缩比例。小于1表示缩小，大于1表示放大
     * @param targetPath    目标路径
     * @return              压缩后路径
     */
    public static String compressByScale(File sourceFile, double scale, String targetPath) {
        try {
            if (scale <= 0 || Double.isNaN(scale) || Double.isInfinite(scale) || StringUtils.isBlank(targetPath)) {
                return null;
            }
            Thumbnails.of(sourceFile).scale(scale).toFile(targetPath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return targetPath;
    }

    public static ThumbnailUtil.Builder<String> test(String a){
        return new ThumbnailUtil.Builder();
    }

    public static ThumbnailUtil.Builder<Integer> test(Integer a){
        return new ThumbnailUtil.Builder();
    }

    public static class Builder<T>{
        public Builder(){

        }

        public ThumbnailUtil.Builder<T> a(){
            return this;
        }

        public ThumbnailUtil.Builder<T> b(){
            return this;
        }
    }

    public static void main(String[] args) {
//        ThumbnailUtil.test(1).a().b();
        System.out.println(Double.isNaN(111));
    }
}
