package com.sandu.test.draw;

import com.sandu.api.volume.room.model.VolumeRoomData;
import com.sandu.api.volume.room.service.DrawVolumeRoomService;
import com.sandu.util.http.RequestBody;
import com.sandu.util.http.Signature;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
@SpringBootTest
//@RunWith(SpringRunner.class)
public class DrawVolumeRoomTests {

    @Autowired
    DrawVolumeRoomService drawVolumeRoomService;

    String data = "{\"projectKey\":\"2638D791-03C2-4484-ACD1-5D9A6C7D688E\",\"houseName\":\"test sae 接口\",\"houseUUID\":null,\"mobile\":\"13254678979\",\"province\":\"北京市\",\"city\":\"北京市\",\"dist\":\"朝阳区\"}";

    @Test
    public void test() {
        RequestBody requestBody = new RequestBody();
        requestBody.setClientType(1);
        requestBody.setTimestamp(System.currentTimeMillis());

        VolumeRoomData volumeRoomData2 = new VolumeRoomData();
        volumeRoomData2.setProvince("深圳市");
        volumeRoomData2.setCity("深圳市");
        volumeRoomData2.setDist("宝安区");
        volumeRoomData2.setHouseName("2638D791-03C2-4484-ACD1-5D9A6C7D688E");
        volumeRoomData2.setMobile("18712345678");
        volumeRoomData2.setProjectKey("2638D791-03C2-4484-ACD1-5D9A6C7D688E");
        volumeRoomData2.setLivingName("小区名2");

        try {
           // Map<String, Object> stringObjectMap = drawVolumeRoomService.saveVolumeRoom(dataStr);
            String dataStr = new ObjectMapper().writeValueAsString(volumeRoomData2);
            requestBody.setData(dataStr);
            requestBody.setSign(Signature.getSignature(requestBody));
//           log.debug("{}", stringObjectMap);
            log.debug("{}", requestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void testZipFile() throws IOException {
        StringBuilder buf = new StringBuilder();
        ZipFile zf = new ZipFile("C:\\Users\\Sandu\\Desktop\\B871025E-8F7C-4CFA-840F-F1CDD6C13F3C.zip");
        Enumeration<? extends ZipEntry> entries = zf.entries();
        ZipEntry zipEntry;
        while (entries.hasMoreElements() && (zipEntry = entries.nextElement()) != null) {
            Scanner scanner = new Scanner(zf.getInputStream(zipEntry));
            while (scanner.hasNextLine()) {
                buf.append(scanner.nextLine());
            }
            scanner.close();
        }

        log.debug("{}", buf.toString());
    }

//    @Test
    public void testZipFile1() throws IOException {
        File file = new File("C:\\Users\\Sandu\\Desktop\\2638D791-03C2-4484-ACD1-5D9A6C7D688E.zip");
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        Map<String, InputStream> map = new HashMap<>();
        ZipEntry zipEntry;
        while (entries.hasMoreElements() && (zipEntry = entries.nextElement()) != null) {
            map.put(zipEntry.getName(), zipFile.getInputStream(zipEntry));
//            if (zipEntry.getName().matches(".+\\.lf$")) {
//                InputStream zipInputStream = zipFile.getInputStream(zipEntry);
//                FileUtils.copyInputStreamToFile(zipInputStream, new File("C:\\Users\\Sandu\\Desktop\\test.txt"));
//            }
        }

        log.debug("map => {}", map);


//        InputStream zipInputStream = zipFile.getInputStream(zipFile.getEntry("B871025E-8F7C-4CFA-840F-F1CDD6C13F3C.lf"));
//        FileUtils.copyInputStreamToFile(zipInputStream, new File("C:\\Users\\Sandu\\Desktop\\test.txt"));
//        zipInputStream.close();
    }
}
