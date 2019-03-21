package com.nork.common.pano.util;

import com.nork.system.service.impl.ResRenderPicServiceImpl;
import net.coobird.thumbnailator.Thumbnails;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class OpenCVUtil {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(OpenCVUtil.class);

    public static OpenCVUtil.Builder def(){
        return OpenCVUtil.Builder.def();
    }

    public static OpenCVUtil.Builder size(int targetWidth, int targetHeight){
        if( targetWidth <= 0 || targetHeight <= 0 ){
            throw new RuntimeException("目标图片长宽不能小于1");
        }
        return OpenCVUtil.Builder.setSize(targetWidth, targetHeight);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        /*BufferedImage bufferedImage = ConverUtil.bufferRead("C:\\Users\\Administrator\\Desktop\\新建文件夹\\111\\C08_0688_001_719240_748281.jpg");
        Mat[] cube = OpenCVUtil.size(2048, 2048).shear(bufferedImage, "");
        OpenCVUtil.mergeImage(cube, 256, "");*/
        //String sourcePath = "C:\\\\Users\\\\Administrator\\\\Desktop\\\\新建文件夹\\\\111\\\\img720_18-07-10_04-57-55.jpg";
        //String targetPath = "C:\\Users\\Administrator\\Desktop\\新建文件夹\\SD_Toilet\\panos\\SD_Toilet_Pano.tiles\\output\\";

        String sourcePath = "D:\\nork\\img720_18-07-06_06-35-57.jpg";
        String targetPath = "D:\\nork\\AA\\test1\\";

        OpenCVUtil.doShear(sourcePath, targetPath);
        long endTime = System.currentTimeMillis();
        System.out.println("=======全景图切割耗时："+ (endTime - startTime) +"======");
    }

    /**
     *
     * @param sourcePath    原图文件路径
     * @param targetPath    生成的文件存放目录路径(注意：需要以斜杠结尾)
     */
    public static void doShear(String sourcePath, String targetPath){
        if( !targetPath.endsWith("/")){
            targetPath = targetPath + "/";
        }
        BufferedImage bufferedImage = ConverUtil.bufferRead(sourcePath);
        // 将720原图切成6片
        Mat[] cube = OpenCVUtil.size(2048, 2048).shear(bufferedImage, targetPath);
        // 将6张图片拼接成预览图
        OpenCVUtil.mergeImage(cube, 256, targetPath);
    }

    /**
     * 全景预览图合成
     */
    private static Mat mergeImage(Mat[] cube, int width, String filePath){

        Mat mat = new Mat(width * cube.length, width, cube[0].type());
        for(int i=0;i<cube.length;i++) {
            Mat side = ConverUtil.matResize(cube[i], width, width);
            mat.put(i*width, 0, getByte(side));
        }
        ConverUtil.matSave(filePath + "preview.jpg", mat);
        return mat;
    }

    private static byte[] getByte(Mat mat) {
        int width = mat.cols();
        int height = mat.rows();
        int dims = mat.channels();
        byte[] rgbdata = new byte[width*height*dims];
        mat.get(0, 0, rgbdata);
        return rgbdata;
    }

    public static class Builder<T>{

        private static int targetWidth = 1024;
        private static int targetHeight = 1024;

        static {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//            System.load("E:\\Users\\Administrator\\Desktop\\opencv300\\opencv\\build\\java\\x64opencv_java300.dll");
        }

        // l, f, r, b, u, d
        static double[][] imageTransform = {
                {-Math.PI /2,0},
                {0, 0},
                {Math.PI /2,0},
                {Math.PI,0},
                {0,-Math.PI/2},
                {0,Math.PI}
        };

        private Builder(int targetWidth, int targetHeight){
            this.targetWidth = targetWidth;
            this.targetHeight = targetHeight;
        }

        public static OpenCVUtil.Builder def(){
            return new OpenCVUtil.Builder(1024, 1024);
        }

        public static OpenCVUtil.Builder setSize(int targetWidth, int targetHeight){
            return new OpenCVUtil.Builder(targetWidth, targetHeight);
        }

        /**
         * 全景图切割，返回6张图
         * @param bufferedImage 原图
         * @param targetPath    生成的图片存放目录路径
         * @return
         */
        private static Mat[] shear(BufferedImage bufferedImage, String targetPath){
            Mat mat = ConverUtil.bufferToMat(bufferedImage, bufferedImage.getType());

            Mat[] cube = new Mat[6];
            for( int i = 0; i < cube.length; i++ ){
                cube[i] = sideCubeMapImage(mat, i, targetWidth, targetHeight, targetPath);
            }
            return cube;
        }

        /**
         * 全景图切割，单面处理
         * @param source        原图
         * @param sideId        方位ID（哪一边）
         * @param sideWidth
         * @param sideHeight
         * @param targetPath    生成的图片存放目录路径
         * @return
         */
        private static Mat sideCubeMapImage(Mat source, final int sideId, final int sideWidth, final int sideHeight, String targetPath){
            Mat result = new Mat(sideWidth, sideHeight, source.type());

//            System.out.println("==========handle "+sideId+" start ===========");
            float sourceWidth = source.cols();
            float sourceHeight = source.rows();     // 获取图片的行列数量

            Mat mapx = new Mat(sideHeight, sideWidth, CvType.CV_32F);
            Mat mapy = new Mat(sideHeight, sideWidth, CvType.CV_32F);       //分配图的x,y轴

            // Calculate adjacent (ak) and opposite (an) of the
            // triangle that is spanned from the sphere center
            //to our cube face.
            final double an = Math.sin(Math.PI / 4);
            final double ak = Math.cos(Math.PI / 4);                                          //计算相邻ak和相反an的三角形张成球体中心

            double ftu = imageTransform[sideId][0];
            double ftv = imageTransform[sideId][1];

            // For each point in the target image,
            // calculate the corresponding source coordinates.                      对于每个图像计算相应的源坐标
            for (int y = 0; y < sideHeight; y++) {
                for (int x = 0; x < sideWidth; x++) {

                    // Map face pixel coordinates to [-1, 1] on plane               将坐标映射在平面上
                    float nx = (float)y / (float)sideHeight - 0.5f;
                    float ny = (float)x / (float)sideWidth - 0.5f;

                    nx *= 2;
                    ny *= 2;

                    // Map [-1, 1] plane coord to [-an, an]
                    // thats the coordinates in respect to a unit sphere
                    // that contains our box.
                    nx *= an;
                    ny *= an;

                    double u, v;

                    // Project from plane to sphere surface.
                    if (ftv == 0) {
                        // Center faces
                        u = Math.atan2(nx, ak);
                        v = Math.atan2(ny * Math.cos(u), ak);
                        u += ftu;
                    }
                    else if (ftv > 0) {
                        // Bottom face
                        double d = Math.sqrt(nx * nx + ny * ny);
                        v = Math.PI / 2 - Math.atan2(d, ak);
                        u = Math.atan2(ny, nx);
                    }
                    else {
                        // Top face
                        //cout << "aaa";
                        double d = Math.sqrt(nx * nx + ny * ny);
                        v = -Math.PI / 2 + Math.atan2(d, ak);
                        u = Math.atan2(-ny, nx);
                    }

                    // Map from angular coordinates to [-1, 1], respectively.
                    u = u / (Math.PI);
                    v = v / (Math.PI / 2);

                    // Warp around, if our coordinates are out of bounds.
                    while (v < -1) {
                        v += 2;
                        u += 1;
                    }
                    while (v > 1) {
                        v -= 2;
                        u += 1;
                    }

                    while (u < -1) {
                        u += 2;
                    }
                    while (u > 1) {
                        u -= 2;
                    }

                    // Map from [-1, 1] to in texture space
                    u = u / 2.0f + 0.5f;
                    v = v / 2.0f + 0.5f;

                    u = u*(sourceWidth-1);
                    v = v*(sourceHeight-1);

                    mapx.put(x, y, u);
                    mapy.put(x, y, v);
                }
            }

            // Do actual  using OpenCV's remap
            Imgproc.remap(source, result, mapx, mapy, Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT, new Scalar(0, 0, 0));
            // l, f, r, b, u, d
            try {
                BufferedImage rotateBufferedImage = null;
                switch(sideId){
                    case 0 :
                        ConverUtil.matSave(targetPath + "pano_l.jpg", result);
                        break;
                    case 1:
                        ConverUtil.matSave(targetPath + "pano_f.jpg", result);
                        break;
                    case 2:
                        ConverUtil.matSave(targetPath + "pano_r.jpg", result);
                        break;
                    case 3:
                        ConverUtil.matSave(targetPath + "pano_b.jpg", result);
                        break;
                    case 4:
                        rotateBufferedImage = ConverUtil.matToBuffer(".jpg", result);
                        Thumbnails.of(rotateBufferedImage).rotate(90).scale(1).toFile(targetPath + "pano_u.jpg");
                        break;
                    case 5:
                        rotateBufferedImage = ConverUtil.matToBuffer(".jpg", result);
                            Thumbnails.of(rotateBufferedImage).rotate(-90).scale(1).toFile(targetPath + "pano_d.jpg");
                        break;
                    default:break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println("==========handle "+sideId+" over ===========");

            return result;
        }
    }

}
