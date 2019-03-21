package com.sandu.web.volume.room.controller;

import com.sandu.api.volume.room.service.DrawVolumeRoomService;
import com.sandu.common.BaseController;
import com.sandu.common.ReturnData;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.util.http.RequestBody;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/v1/volume/room")
@Api(tags = "量房", description = "DrawVolumeRoomController")
public class DrawVolumeRoomController extends BaseController {

    @Autowired
    @Qualifier("KCDrawVolumeRoomServiceImpl")
    DrawVolumeRoomService drawVolumeRoomService;

    /**
     * 科创保存量房数据
     * @param requestBody
     * @param zipFile
     * @return
     */
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ReturnData saveVolumeRoom(RequestBody requestBody,
                                     @RequestParam("zipFile") MultipartFile zipFile) {
        ReturnData returnData = ReturnData.builder();
        if (requestBody == null) {
            return returnData.status(false).code(ResponseEnum.PARAM_ERROR);
        }

        if (zipFile == null || zipFile.isEmpty()) {
            return returnData.status(false).code(ResponseEnum.PARAM_ERROR);
        }

        drawVolumeRoomService.saveVolumeRoomOfErrorable(requestBody, zipFile);
        return returnData.status(true).code(ResponseEnum.SUCCESS);
    }
}

