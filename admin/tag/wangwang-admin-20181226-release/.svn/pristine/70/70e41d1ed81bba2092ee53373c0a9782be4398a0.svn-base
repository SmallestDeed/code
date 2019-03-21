package com.sandu.common.util.file;

import com.sandu.common.context.SystemContextHolder;
import com.sandu.util.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.env.Environment;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.sandu.common.util.formater.FormatterUtil.nameFormatter;
import static com.sandu.common.util.formater.FormatterUtil.pathFormatter;
import static com.sandu.constant.Punctuation.*;


/**
 * creator by bvvy
 */
@Slf4j
public class FilePathUtil {

    public static final String UPLOAD_PATH_FORMAT = "upload.path.format";
    private static final String FILEKEY_SUFFIX = ".upload.path";
    private static final Integer RANDOM_PATH_LENGTH = 6;
    public static Map<String, String> platforms = new HashMap<String, String>() {{
        put("windowPc", "AA");
        put("newWindowsPc", "AA");
        put("android", "BB");
        put("ios", "CC");
        put("ipad", "DD");
        put("macBookPc", "EE");
        put("web", "FF");
    }};

    /**
     * 获取基础路径 applicaiton.properties中配的
     *
     * @return path
     */
    public static String storagePath() {
        return SystemContextHolder.getSystemContext().getStoragePath();
    }


    public static String removeStr(String source, String target) {

        return source.replace(target, EMPTY);
    }

    /**
     * 获取存储路径
     *
     * @param filename 文件名
     * @param filekey  filekey
     * @param now      时间
     * @return 路径
     */
    public static String getRealPath(String filename, String filekey, LocalDateTime now) {
        String real = formatPath(storagePath(), fileKeyPath(filekey, now));
        mkdirIfNotExsists(real);
        return real + filename(now) + DOT + getExtension(filename);
    }


    public static String absolutePath(String platform, String module, LocalDateTime now, String filename) {
        String abs = formatPath(storagePath(), platformPath(platform), module, datePath(now));
        mkdirIfNotExsists(abs);
        return abs + filename(now) + DOT + getExtension(filename);
    }

    public static String absolutePath(String platform, String module, String type, LocalDateTime now, String filename) {
        String abs = storagePath() + formatPath(filePath(platform, module, type, now));
        mkdirIfNotExsists(abs);
        return abs + filename(now) + DOT + getExtension(filename);
    }


    private static String platformPath(String platform) {
        return platforms.get(platform);
    }

    /**
     * 通过完整路径获取配置文件中的路径
     *
     * @param realPath 完整路径
     * @return path
     */
    public static String relativePath(String realPath) {
        return realPath.replace(storagePath(), EMPTY);
    }

    /**
     * 获取在配置文件中的路径 + 文件名
     *
     * @param filename filename
     * @param filekey  filekey
     * @param now      now
     * @return path
     */
    public static String relativePath(String filename, String filekey, LocalDateTime now) {
        return fileKeyPath(filekey, now) + filename(now) + DOT + getExtension(filename);
    }


    /**
     * 文件名 使用当前日期作为名称
     *
     * @param now 当前日期
     * @return 当前日期的名称字符串 20171220174521456
     */
    public static String filename(LocalDateTime now) {
        return randomPath() + UNDERLINE + now.format(nameFormatter);
    }


    /**
     * 日期路径
     *
     * @param now 当前日期
     * @return 当前日期的路径格式  2017/12/20
     */
    public static String datePath(LocalDateTime now) {
        return now.format(pathFormatter);
    }


    /**
     * 日期
     *
     * @param now       日期
     * @param formatter 格式化
     * @return 结果
     */
    public static String datePath(LocalDateTime now, DateTimeFormatter formatter) {
        return now.format(formatter);
    }


    /**
     * 获取后缀名
     *
     * @param filename 源文件名
     * @return 后缀名
     */
    public static String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    /**
     * 获取文件名后缀 包含 .
     *
     * @param filename xxx.jpg
     * @return .jpg
     */
    public static String getSuffix(String filename) {
        return DOT + getExtension(filename);
    }

    private static void mkdirIfNotExsists(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 获取当前服务器地址
     *
     * @return 服务器地址
     */
    public static String serverPath() {

        Environment environment = ApplicationContextUtil.getBean(Environment.class);
        return environment.getProperty("server.url");
//        return SystemContextHolder.getSystemContext().getServerPath();
    }


    /**
     * 拼接地址
     *
     * @param paths 多个地址
     * @return 拼接结果
     */
    public static String formatPath(String... paths) {
        StringBuilder builder = new StringBuilder();
        for (String path : paths) {
            path = removeStartSlash(path);
            builder.append(path);
            if (!path.endsWith(SLASH)) {
                builder.append(SLASH);
            }
        }
        return builder.toString();
    }

    /**
     * 原系统 从配置文件中获取地址
     *
     * @param fileKey filekey
     * @return path
     */
    public static String fileKeyPath(String fileKey, LocalDateTime now) {
        Environment environment = ApplicationContextUtil.getBean(Environment.class);
        String path = environment.getProperty(fileKey + FILEKEY_SUFFIX);
        path = removeStartSlash(path);
        return replaceDate(path, now);
    }

    public static String filePath(String platform, String module, String type, LocalDateTime now) {
        Environment environment = ApplicationContextUtil.getBean(Environment.class);
        String path = environment.getProperty(UPLOAD_PATH_FORMAT);
        path = path
                .replace("_platform_", platform)
                .replace("_module_", module)
                .replace("_type_", type);
        path = formatDatePath(path, now);
        return path;
    }

    private static String formatDatePath(String path, LocalDateTime now) {

        String proPath = path.substring(path.indexOf(LEFT_SQUARE_BRACKETS) + 1, path.indexOf(RIGHT_SQUARE_BRACKETS));
        String nowPath = now.format(DateTimeFormatter.ofPattern(proPath));
        path = path.replace(LEFT_SQUARE_BRACKETS + proPath + RIGHT_SQUARE_BRACKETS, nowPath);
        return path;
    }

    public static String fileKeyPath(String fileKey) {
        return fileKey.replace(DOT, SLASH);
    }

    /**
     * 移除路径开始的 斜杠
     *
     * @param path path
     * @return path
     */
    public static String removeStartSlash(String path) {
        if (path.startsWith(SLASH)) {
            path = path.substring(1);
        }
        return path;
    }

    private static String replaceDate(String filePath, LocalDateTime now) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String dateInfo = now.format(formatter);
        String[] dateInfoArrays = dateInfo.split("_");
        if (filePath.contains("[yyyy]")) {
            filePath = filePath.replace("[yyyy]", dateInfoArrays[0]);
        }
        if (filePath.contains("[MM]")) {
            filePath = filePath.replace("[MM]", dateInfoArrays[1]);
        }
        if (filePath.contains("[dd]")) {
            filePath = filePath.replace("[dd]", dateInfoArrays[2]);
        }
        if (filePath.contains("[HH]")) {
            filePath = filePath.replace("[HH]", dateInfoArrays[3]);
        }
        if (filePath.contains("[mm]")) {
            filePath = filePath.replace("[mm]", dateInfoArrays[4]);
        }
        if (filePath.contains("[ss]")) {
            filePath = filePath.replace("[ss]", dateInfoArrays[5]);
        }
        return filePath;
    }


    /**
     * 随机字符串
     *
     * @return str
     */
    public static String randomPath() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < RANDOM_PATH_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

}
