//package com.sandu.test.draw;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sandu.api.house.bo.UserLoginBO;
//import com.sandu.api.house.input.DrawBaseHouseSubmitDTO;
//import com.sandu.api.house.input.DrawBaseProductDTO;
//import com.sandu.api.house.input.DrawSpaceCommonDTO;
//import com.sandu.api.house.input.PublicProductInfoDTO;
//import com.sandu.api.house.model.*;
//import com.sandu.api.house.service.SystemDictionaryService;
//import com.sandu.api.product.model.BaseProduct;
//import com.sandu.api.product.service.DrawBaseProductService;
//import com.sandu.api.res.model.ResModel;
//import com.sandu.api.res.service.ResModelService;
//import com.sandu.common.constant.*;
//import com.sandu.common.constant.attr.DoorAttr;
//import com.sandu.common.constant.house.*;
//import com.sandu.exception.BusinessException;
//import com.sandu.exception.DrawBusinessException;
//import com.sandu.service.house.dao.BaseHouseMapper;
//import com.sandu.service.product.dao.BaseProductMapper;
//import com.sandu.service.product.dao.DrawBaseProductMapper;
//import com.sandu.service.templet.impl.DrawDesignTempletProductServiceImpl;
//import com.sandu.util.Regex;
//import com.sandu.util.Utils;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.stereotype.Service;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Slf4j
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class V2Submit {
//
//    @Autowired
//    BaseHouseMapper baseHouseMapper;
//
//    @Autowired
//    DrawSpaceCommonService drawSpaceCommonService;
//
//    @Autowired
//    DrawDesignTempletService1 drawDesignTempletService1;
//
//    @Autowired
//    DrawBaseProductService1 drawBaseProductService1;
//
//    @Test
//    public void test() {
//
//        String data = "{\"houseId\":\"462\",\"spaceCommonDTOList\":[{\"spaceCommonFunctionValueKey\":\"toilet\",\"spaceCommonArea\":\"23.65278\",\"spaceCommonShape\":\"长方形\",\"spaceCommonFileId\":54327,\"spacePicId\":3074,\"drawBaseProductDTOList\":[{\"uniqueId\":\"f9cef737-cce1-44f2-bda6-6fc5b4a21e19\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54271,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"43517b75-23ba-4c38-8cd3-76d71472342c\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54272,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"6868e1b3-60fc-47f7-a96e-cf8565cfe3f6\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54273,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"257f7f6b-8e25-4fa4-a155-aa34c8ad2f9e\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54274,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"67fc7de6-4f48-4a6e-bd62-37a7d47faad3\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54275,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"2c62b8b8-9dad-4723-8b1e-dce60aa13ee1\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54276,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"12363f17-2770-4a2b-b62d-ee5777831617\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54277,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"7bc5a702-eefa-4b3e-8287-202e0dd2e91c\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_weisjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54264,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"b36a03f6-949f-4a6d-97da-84891fcc1ca4\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_weisjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54265,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"3982b9ef-b75d-4df3-a957-5855409c85fb\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_weisjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54266,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"6a7b1fb0-81f2-4422-b4d2-0a9337afa97e\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_weisjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54267,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"832fcf3d-6bc4-4668-97cd-b90efc5443a5\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_weisjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54268,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"0e6d4938-3f11-4abf-9268-195d89696ebf\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_weisjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54269,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"3985157b-f9b7-472c-b29d-8fd411875774\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_weisjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54270,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"4a3fc967-68d2-4d1b-93b5-8ee6f1c8fa2b\",\"bigTypeValueKey\":null,\"smallTypeValueKey\":null,\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54280,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":2,\"bindUniqueId\":null},{\"uniqueId\":\"07d77cd4-361f-486c-b4a9-3552f059a0cd_dim\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"basic_dimz\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54263,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"c3d720a3-10f0-4dd7-b112-b8d5a49833ce_dimyt\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"basic_dimyt\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54262,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"07d77cd4-361f-486c-b4a9-3552f059a0cd_tianh\",\"bigTypeValueKey\":\"tianh\",\"smallTypeValueKey\":\"basic_tianh\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54278,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"c01c0363-cfe3-48df-867b-be9fefe9b15c\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"basic_dimmk\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54261,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null}]},{\"spaceCommonFunctionValueKey\":\"restaurant\",\"spaceCommonArea\":\"21.25373\",\"spaceCommonShape\":\"长方形\",\"spaceCommonFileId\":54328,\"spacePicId\":3075,\"drawBaseProductDTOList\":[{\"uniqueId\":\"0d79107d-0b32-4dd9-a6cd-859991f3914c\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54307,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"5710c4ac-2b70-40a5-a226-2e14f7cf12e4\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54308,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"43517b75-23ba-4c38-8cd3-76d71472342c\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54309,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"83d01446-7a0c-495d-8acd-432680edbe0f\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54310,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"722de5e4-c26b-4ec5-b6b7-97c9a6a15e61\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"164\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54281,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"0d79107d-0b32-4dd9-a6cd-859991f3914c\"},{\"uniqueId\":\"d17c73aa-d7e8-4a7e-ad52-8c053b4bceac\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"90\",\"productWidth\":\"4\",\"productHeight\":\"50\",\"productFileId\":54290,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"0d79107d-0b32-4dd9-a6cd-859991f3914c\"},{\"uniqueId\":\"359936d9-7811-42de-9279-80c6aeafba74\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"63\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54291,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"0d79107d-0b32-4dd9-a6cd-859991f3914c\"},{\"uniqueId\":\"33aa12f4-8d9b-4098-82c0-97cdac78947d\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"61\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54292,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"0d79107d-0b32-4dd9-a6cd-859991f3914c\"},{\"uniqueId\":\"bcaf73e5-21e9-44c3-a331-da90a2a0ea39\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"187\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54293,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"5710c4ac-2b70-40a5-a226-2e14f7cf12e4\"},{\"uniqueId\":\"c658176e-d78e-49cd-baa8-20a0de922ec5\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"90\",\"productWidth\":\"4\",\"productHeight\":\"50\",\"productFileId\":54294,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"5710c4ac-2b70-40a5-a226-2e14f7cf12e4\"},{\"uniqueId\":\"73e38ce0-86b8-4203-b4b8-29b33e8518fa\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"89\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54295,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"5710c4ac-2b70-40a5-a226-2e14f7cf12e4\"},{\"uniqueId\":\"96fede36-914e-4ec6-80f9-9474e7f4cde2\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"90\",\"productWidth\":\"4\",\"productHeight\":\"50\",\"productFileId\":54296,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"5710c4ac-2b70-40a5-a226-2e14f7cf12e4\"},{\"uniqueId\":\"19765dae-5f22-4403-9d58-c8f8785026ee\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"63\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54297,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"5710c4ac-2b70-40a5-a226-2e14f7cf12e4\"},{\"uniqueId\":\"bf90e79d-1c8d-49f2-b67f-4d82bf455e09\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"69\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54282,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"43517b75-23ba-4c38-8cd3-76d71472342c\"},{\"uniqueId\":\"ccc1de2f-4a9a-4770-bfe2-1b644c300cd7\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"90\",\"productWidth\":\"4\",\"productHeight\":\"50\",\"productFileId\":54283,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"43517b75-23ba-4c38-8cd3-76d71472342c\"},{\"uniqueId\":\"54fe46dd-e55a-4d3f-96b0-9495ed7d2a17\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"22\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54284,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"43517b75-23ba-4c38-8cd3-76d71472342c\"},{\"uniqueId\":\"9d3f62ff-b40e-4af5-b417-ff9a7dbe0b5e\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"90\",\"productWidth\":\"4\",\"productHeight\":\"50\",\"productFileId\":54285,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"43517b75-23ba-4c38-8cd3-76d71472342c\"},{\"uniqueId\":\"73f17c63-ef66-4779-8255-4d291a485255\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"139\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54286,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"43517b75-23ba-4c38-8cd3-76d71472342c\"},{\"uniqueId\":\"a40f0266-5951-4982-97b9-02274765659a\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"159\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54287,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"A\",\"modelType\":0,\"bindUniqueId\":\"83d01446-7a0c-495d-8acd-432680edbe0f\"},{\"uniqueId\":\"7c301e9f-5b4a-4a8a-b6da-4988e39279f9\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"90\",\"productWidth\":\"4\",\"productHeight\":\"50\",\"productFileId\":54288,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"83d01446-7a0c-495d-8acd-432680edbe0f\"},{\"uniqueId\":\"682dc946-c4a7-4745-95d2-05627405625b\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"136\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54289,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"B\",\"modelType\":0,\"bindUniqueId\":\"83d01446-7a0c-495d-8acd-432680edbe0f\"},{\"uniqueId\":\"8139d9b2-8033-42b2-a91c-4ba5ea2bdee0\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_ruhm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54301,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"3f79bfa9-b70a-42a8-a772-26ce0305ca28\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_fangjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54302,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"169d95e9-e613-4951-a3ae-a5a9c8eacd2c\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_fangjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54303,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"b36a03f6-949f-4a6d-97da-84891fcc1ca4\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_weisjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54304,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"3982b9ef-b75d-4df3-a957-5855409c85fb\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_weisjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54305,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"31bd235c-52f9-49a2-937f-5dd86bd34b8f\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_ruhm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54306,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"8673022f-a448-406f-a975-a31e284230ff\",\"bigTypeValueKey\":null,\"smallTypeValueKey\":null,\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54312,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":2,\"bindUniqueId\":null},{\"uniqueId\":\"5ed1af84-f2b9-4afa-8911-03367bddaf47\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"tijx\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54298,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"b32f20af-1e2b-4de1-8659-b395e8c51d0b_dim\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"basic_dimz\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54300,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"b32f20af-1e2b-4de1-8659-b395e8c51d0b_tianh\",\"bigTypeValueKey\":\"tianh\",\"smallTypeValueKey\":\"basic_tianh\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54311,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"e4ec32d1-d93c-4da5-a662-a2bfec7bb815\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"basic_dimmk\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54299,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null}]},{\"spaceCommonFunctionValueKey\":\"balcony\",\"spaceCommonArea\":\"20.40473\",\"spaceCommonShape\":\"长方形\",\"spaceCommonFileId\":54329,\"spacePicId\":3076,\"drawBaseProductDTOList\":[{\"uniqueId\":\"67fc7de6-4f48-4a6e-bd62-37a7d47faad3\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54321,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"899a91bd-09f4-45eb-b4dd-0292e0d8fb01\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54322,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"2c62b8b8-9dad-4723-8b1e-dce60aa13ee1\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54323,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"12363f17-2770-4a2b-b62d-ee5777831617\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54324,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"6a7b1fb0-81f2-4422-b4d2-0a9337afa97e\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_weisjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54316,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"832fcf3d-6bc4-4668-97cd-b90efc5443a5\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_weisjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54317,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"0e6d4938-3f11-4abf-9268-195d89696ebf\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_weisjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54318,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"02c16abe-7b7a-4c6f-9d4c-3f2fd250b1fb\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_yangtm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54319,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"87e51db7-f91f-42fd-98a3-564b65e96c3c\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_yangtm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54320,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"87f9bcea-f57b-4477-844c-b388170ffa2e\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"tijx\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54313,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"c3d720a3-10f0-4dd7-b112-b8d5a49833ce_dim\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"basic_dimz\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54315,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"c3d720a3-10f0-4dd7-b112-b8d5a49833ce_tianh\",\"bigTypeValueKey\":\"tianh\",\"smallTypeValueKey\":\"basic_tianh\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54325,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"2d623570-862f-45b7-b4dc-cd58e35fc0c8\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"basic_dimmk\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54314,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null}]},{\"spaceCommonFunctionValueKey\":\"room\",\"spaceCommonArea\":\"16.84676\",\"spaceCommonShape\":\"长方形\",\"spaceCommonFileId\":54326,\"spacePicId\":3077,\"drawBaseProductDTOList\":[{\"uniqueId\":\"2c62b8b8-9dad-4723-8b1e-dce60aa13ee1\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54253,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"3bc709c0-73cb-495e-9b5a-8e8436a8b394\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54254,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"b27e300b-8b55-4761-8291-c29aef8dc841\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54255,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"4c94966f-e021-4ae1-8408-701877f078d6\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54256,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"67fc7de6-4f48-4a6e-bd62-37a7d47faad3\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54257,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"899a91bd-09f4-45eb-b4dd-0292e0d8fb01\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54258,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"12363f17-2770-4a2b-b62d-ee5777831617\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_qiangm\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54259,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"47a6cc7c-befa-4436-861b-c5b105109089\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"17\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54233,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"N\",\"modelType\":0,\"bindUniqueId\":\"2c62b8b8-9dad-4723-8b1e-dce60aa13ee1\"},{\"uniqueId\":\"0b567ae8-fd4e-41b6-ab0d-af4387ef75e6\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"90\",\"productWidth\":\"4\",\"productHeight\":\"50\",\"productFileId\":54237,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"K\",\"modelType\":0,\"bindUniqueId\":\"2c62b8b8-9dad-4723-8b1e-dce60aa13ee1\"},{\"uniqueId\":\"b27ab854-f2c7-4053-82e3-e4f2d2d7434e\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"11\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54238,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"K\",\"modelType\":0,\"bindUniqueId\":\"2c62b8b8-9dad-4723-8b1e-dce60aa13ee1\"},{\"uniqueId\":\"3ab2b144-0e09-4461-9b84-3249aebbed7e\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"90\",\"productWidth\":\"4\",\"productHeight\":\"50\",\"productFileId\":54239,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"K\",\"modelType\":0,\"bindUniqueId\":\"2c62b8b8-9dad-4723-8b1e-dce60aa13ee1\"},{\"uniqueId\":\"ec189325-f8a0-4f43-9a51-07a764bdcee4\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"105\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54240,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"K\",\"modelType\":0,\"bindUniqueId\":\"2c62b8b8-9dad-4723-8b1e-dce60aa13ee1\"},{\"uniqueId\":\"0821b404-89e6-4087-95d1-c419489e7227\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"634\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54241,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"J\",\"modelType\":0,\"bindUniqueId\":\"3bc709c0-73cb-495e-9b5a-8e8436a8b394\"},{\"uniqueId\":\"d53a3e4a-5d28-4e4d-8388-0f7c649e7743\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"209\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54242,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"K\",\"modelType\":0,\"bindUniqueId\":\"b27e300b-8b55-4761-8291-c29aef8dc841\"},{\"uniqueId\":\"9de496cd-c0c5-4f18-adc9-2fd731b7a193\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"190\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54243,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"K\",\"modelType\":0,\"bindUniqueId\":\"4c94966f-e021-4ae1-8408-701877f078d6\"},{\"uniqueId\":\"23d467c4-ce01-44f1-a579-de4632960929\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"90\",\"productWidth\":\"4\",\"productHeight\":\"50\",\"productFileId\":54244,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"K\",\"modelType\":0,\"bindUniqueId\":\"4c94966f-e021-4ae1-8408-701877f078d6\"},{\"uniqueId\":\"808b8eb6-c927-4a98-8bc7-6241c95ff5a8\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"89\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54234,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"K\",\"modelType\":0,\"bindUniqueId\":\"4c94966f-e021-4ae1-8408-701877f078d6\"},{\"uniqueId\":\"6f65eed8-f11d-4cab-9afd-1659970d0b80\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"90\",\"productWidth\":\"4\",\"productHeight\":\"50\",\"productFileId\":54235,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"K\",\"modelType\":0,\"bindUniqueId\":\"4c94966f-e021-4ae1-8408-701877f078d6\"},{\"uniqueId\":\"146c757a-3d4c-44bb-9b9b-dbf948de046a\",\"bigTypeValueKey\":\"qiangm\",\"smallTypeValueKey\":\"basic_beij\",\"productLength\":\"127\",\"productWidth\":\"4\",\"productHeight\":\"250\",\"productFileId\":54236,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":\"\",\"wallType\":\"K\",\"modelType\":0,\"bindUniqueId\":\"4c94966f-e021-4ae1-8408-701877f078d6\"},{\"uniqueId\":\"3f79bfa9-b70a-42a8-a772-26ce0305ca28\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_fangjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54249,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"169d95e9-e613-4951-a3ae-a5a9c8eacd2c\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_fangjm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54250,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"02c16abe-7b7a-4c6f-9d4c-3f2fd250b1fb\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_yangtm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54251,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"87e51db7-f91f-42fd-98a3-564b65e96c3c\",\"bigTypeValueKey\":\"meng\",\"smallTypeValueKey\":\"basic_yangtm\",\"productLength\":\"90\",\"productWidth\":\"12\",\"productHeight\":\"210\",\"productFileId\":54252,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"6e650b24-2fc3-4dae-afc5-6218766ac3cd\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"tijx\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54245,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"f3e45a41-3198-4dd7-a15b-3f99cf84575b_dim\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"basic_dimz\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54248,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"c3d720a3-10f0-4dd7-b112-b8d5a49833ce_dimyt\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"basic_dimyt\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54247,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"f3e45a41-3198-4dd7-a15b-3f99cf84575b_tianh\",\"bigTypeValueKey\":\"tianh\",\"smallTypeValueKey\":\"basic_tianh\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54260,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null},{\"uniqueId\":\"783f4b67-21a7-44d7-9bf4-877ef2189ac2\",\"bigTypeValueKey\":\"dim\",\"smallTypeValueKey\":\"basic_dimmk\",\"productLength\":null,\"productWidth\":null,\"productHeight\":null,\"productFileId\":54246,\"productId\":0,\"groupProductId\":0,\"groupUniqueId\":null,\"isMainProduct\":0,\"groupType\":0,\"isChanged\":2,\"wallOrientation\":null,\"wallType\":null,\"modelType\":0,\"bindUniqueId\":null}]}]}";
//
//        UserLoginBO loginBO = new UserLoginBO();
//        loginBO.setId(123456L);
//        loginBO.setLoginName("18712345678");
//
//        DrawBaseHouse drawHouse = new DrawBaseHouse();
//        drawHouse.setId(1L);
//        drawHouse.setDataType(1);
//
//        DrawBaseHouseSubmitDTO dtoNew = null;
//
//        try {
//            dtoNew = new ObjectMapper().readValue(data, DrawBaseHouseSubmitDTO.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        List<DrawSpaceBO> drawSpaces = new ArrayList<>();
//
//        for (DrawSpaceCommonDTO drawSpace : dtoNew.getSpaceCommonDTOList()) {
//            // 处理空间
//            DrawSpaceBO drawSpaceBO = new DrawSpaceBO();
//            drawSpaceCommonService.getDrawSpaceWrapper(drawSpace, drawSpaceBO, loginBO, drawHouse);
//
//            // 处理样板房
//            drawDesignTempletService1.getDrawDesignTempletWrapper(drawSpace, drawSpaceBO, loginBO, drawHouse);
//
//            // base product
//            drawBaseProductService1.getDrawBaseProductWrapper(drawSpace, drawSpaceBO, loginBO, drawHouse);
//
//            drawSpaces.add(drawSpaceBO);
//        }
//
//        // drawHouseService.saveDrawHouseBySubmit(drawHouse, drawSpaces);
//    }
//}
//
//@Getter
//@Service
//class DrawSpaceCommonService {
//
//    @Autowired
//    private List<DrawSpaceWrapper> wrappers;
//
//    @Autowired
//    BaseHouseMapper baseHouseMapper;
//
//    public final static String DEFAULTS_SPACE_NAME = "其它";
//
//    boolean getDrawSpaceWrapper(DrawSpaceCommonDTO drawSpace, DrawSpaceBO drawSpaceBO, UserLoginBO loginBO, DrawBaseHouse drawHouse) {
//        DrawSpaceCommon drawSpaceCommon = new DrawSpaceCommon();
//        // save drawSpaceCommon
//        drawSpaceBO.setDrawSpaceCommon(drawSpaceCommon);
//
//        drawSpaceCommon.setSysCode(Utils.getSysCode(6));
//        drawSpaceCommon.setMainLength(drawSpace.getSpaceCommonMainLength());
//        drawSpaceCommon.setMainWidth(drawSpace.getSpaceCommonMainWidth());
//        drawSpaceCommon.setSpaceShape(DrawSpaceCommonConstant.DEFAULTS_SHAPE_RECTANGLE + "");
//        // 空间图片:前端暂时无法获取
//        drawSpaceCommon.setPicId(drawSpace.getSpacePicId());
//
//        // 未知属性
//        drawSpaceCommon.setSpaceNum(1);
//        // 没有意义,转换到space_common表要重新定义该值
//        drawSpaceCommon.setCreator(loginBO.getLoginName());
//        drawSpaceCommon.setModifier(loginBO.getLoginName());
//        Date now = new Date();
//        drawSpaceCommon.setGmtCreate(now);
//        drawSpaceCommon.setGmtModified(now);
//        drawSpaceCommon.setIsDeleted(DrawSpaceCommonConstant.DEFAULTS_IS_DELETED);
//        drawSpaceCommon.setIsStandardSpace(1);
//        drawSpaceCommon.setSyncStatus("NOT_SYNCHRONIZED");
//        drawSpaceCommon.setOpenStatus(2);
//        drawSpaceCommon.setBakeBeforeFileId(drawSpace.getSpaceCommonFileId());
//        drawSpaceCommon.setDrawBaseHouseId(drawHouse.getId());
//
//        // 0、平台；1、个人
//        drawSpaceCommon.setDataType(drawHouse.getDataType());
//        // 发布状态
//        drawSpaceCommon.setStatus(getPutawayState(drawHouse));
//
//        // 执行DrawSpaceCommon 处理
//        return this.wrapper(drawSpace, drawSpaceBO);
//    }
//
//    private Integer getPutawayState(DrawBaseHouse drawHouse) {
//        if (DrawBaseHouseConstant.DATA_PERSONAL.equals(drawHouse.getDataType())) {
//            return SpaceCommonStatusConstant.HAS_BEEN_RELEASE;
//        }
//
//        // 检查原先是否发布，如果发布继续为发布状态
//        if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() > 0) {
//            Integer putawayState = baseHouseMapper.getPutawayState(drawHouse.getBaseHouseId());
//            return DesignTempletStatusConstant.HAS_BEEN_RELEASE.equals(putawayState) ? SpaceCommonStatusConstant.HAS_BEEN_RELEASE : SpaceCommonStatusConstant.TESTING;
//            // return (putawayState == null) ? SpaceCommonStatusConstant.TESTING : putawayState;
//        }
//
//        return SpaceCommonStatusConstant.TESTING;
//    }
//
//    private boolean wrapper(Object warpSource, Object warpTarget) {
//        // 执行DrawSpaceCommon 处理
//        for (Wrapper handler : wrappers) {
//            boolean wrap = handler.wrap(warpSource, warpTarget);
//            if (wrap) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//}
//
//@Slf4j
//@Getter
//@Service
//class DrawDesignTempletService1 {
//
//    @Autowired
//    BaseHouseMapper baseHouseMapper;
//
//    @Autowired
//    private List<DrawDesignTempletWrapper> wrappers;
//
//    public boolean getDrawDesignTempletWrapper(DrawSpaceCommonDTO drawSpace, DrawSpaceBO drawSpaceBO,
//                                               UserLoginBO loginBO, DrawBaseHouse drawHouse) {
//        DrawSpaceCommon drawSpaceCommon = drawSpaceBO.getDrawSpaceCommon();
//
//        // setDrawDesignTemplet
//        DrawDesignTemplet drawDesignTemplet = new DrawDesignTemplet();
//        drawSpaceBO.setDrawDesignTemplet(drawDesignTemplet);
//
//        Date now = new Date();
//        drawDesignTemplet.setSysCode(Utils.getSysCode(6));
//        drawDesignTemplet.setDesignCode(getDesignCode(drawSpaceCommon.getSpaceCode()));
//        drawDesignTemplet.setDesignName(drawSpaceCommon.getSpaceName() + "户型绘制样板房");
//        drawDesignTemplet.setIsRecommend(1);
//        drawDesignTemplet.setGmtCreate(now);
//        drawDesignTemplet.setGmtModified(now);
//        drawDesignTemplet.setUserId(Integer.valueOf(loginBO.getId() + ""));
//        drawDesignTemplet.setCreator(loginBO.getLoginName());
//        drawDesignTemplet.setModifier(loginBO.getLoginName());
//        drawDesignTemplet.setIsDeleted(DrawDesignTempletConstant.DESIGNTEMPLET_ISDELETE_DEFAULT);
//        drawDesignTemplet.setSyncStatus("NOT_SYNCHRONIZED");
//        // 样板房图
//        drawDesignTemplet.setPicId(drawSpaceCommon.getPicId());
//        drawDesignTemplet.setEffectPic(Objects.toString(drawSpaceCommon.getPicId(), ""));
//        drawDesignTemplet.setEffectPlanIds(Objects.toString(drawSpaceCommon.getPicId(), ""));
//        // 是否有天花区域；0、否(defaults)；1是
//        drawDesignTemplet.setIsRegionalCeiling(drawSpace.getIsRegionalCeiling());
//
//        // 0、平台数据；1、个人数据
//        drawDesignTemplet.setDataType(drawHouse.getDataType());
//
//        // 获取发布状态
//        drawDesignTemplet.setPutawayState(getPutawayState(drawHouse));
//
//        // drawDesignTemplet.setSpaceCommonId(Integer.valueOf(drawSpaceCommon.getId() + ""));
//
//        // 卫生间处理 space_layout_type
//        return this.wrapper(drawSpace, drawDesignTemplet);
//    }
//
//    private String getDesignCode(String arg) {
//        if (arg == null) {
//            return "unknown_" + Utils.random();
//        }
//
//        String[] split = arg.split("_");
//        if (split.length > 0) {
//            split[split.length - 1] = Utils.generateRandomDigitString(6);
//            return Arrays.stream(split).collect(Collectors.joining("_"));
//        }
//
//        return "unknown_" + Utils.random();
//    }
//
//    private Integer getPutawayState(DrawBaseHouse drawHouse) {
//        if (DrawBaseHouseConstant.DATA_PERSONAL.equals(drawHouse.getDataType())) {
//            return DesignTempletStatusConstant.HAS_BEEN_RELEASE;
//        }
//
//        // 检查原先是否发布，如果发布继续为发布状态
//        if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() > 0) {
//            Integer putawayState = baseHouseMapper.getPutawayState(drawHouse.getBaseHouseId());
//            return DesignTempletStatusConstant.HAS_BEEN_RELEASE.equals(putawayState) ? DesignTempletStatusConstant.HAS_BEEN_RELEASE : DesignTempletStatusConstant.TESTING;
//            // return (putawayState == null) ? DesignTempletStatusConstant.TESTING : putawayState;
//        }
//
//        return DesignTempletStatusConstant.TESTING;
//    }
//
//    private boolean wrapper(Object warpSource, Object warpTarget) {
//        // 执行DrawSpaceCommon 处理
//        for (Wrapper handler : wrappers) {
//            boolean wrap = handler.wrap(warpSource, warpTarget);
//            if (wrap) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//}
//
//@Slf4j
//@Getter
//@Service
//class DrawBaseProductService1 {
//
//    public static final int DEFAULT_STRATEGY_LOOP = 20;
//    public static final String PRODUCT_CODE_PREFIX = "baimo_";
//
//    @Autowired
//    BaseHouseMapper baseHouseMapper;
//
//    @Autowired
//    SystemDictionaryService dictionaryService;
//
//    @Autowired
//    DrawBaseProductMapper drawBaseProductMapper;
//
//    @Autowired
//    BaseProductMapper baseProductMapper;
//
//    @Autowired
//    DrawBaseProductService drawBaseProductService;
//
//    @Autowired
//    private List<BaseProductFilter> filters;
//
//    @Autowired
//    private List<DrawBaseProductWrapper> bpWrappers;
//
//    @Autowired
//    private List<DrawDesignTempletProductWrapper> dtpWrappers;
//
//    public boolean getDrawBaseProductWrapper(DrawSpaceCommonDTO drawSpace, DrawSpaceBO drawSpaceBO,
//                                             UserLoginBO loginBO, DrawBaseHouse drawHouse) {
////        DrawDesignTempletBO drawDesignTempletBO = drawSpaceBO.getDrawDesignTempletBO();
////        // draw base product
////        drawDesignTempletBO.setDrawBaseProductBOList(new ArrayList<>());
////        // 识别为窗框/栏杆之类/阳台门
////        drawDesignTempletBO.setPublicProductInfoDTOList(new ArrayList<>());
//
//        DrawDesignTemplet drawDesignTemplet = drawSpaceBO.getDrawDesignTemplet();
//        // draw base product
//        drawSpaceBO.setDrawBaseProductBOList(new ArrayList<>());
//        // 识别为窗框/栏杆之类/阳台门
//        drawSpaceBO.setPublicProductInfoDTOList(new ArrayList<>());
//
//        // 整理绑定点信息
////        Map<String, List<Long>> bingInfoMap = this.getBingInfoMap(dto);
//
//        for (DrawBaseProductDTO drawBaseProductDTO : drawSpace.getDrawBaseProductDTOList()) {
//            // 过滤软装产品
//            boolean filter = this.filter(drawBaseProductDTO, drawSpaceBO);
//            if (filter) {
//                continue;
//            }
//
//            // 验证
//            this.validate(drawBaseProductDTO);
//            DrawBaseProductBO drawBaseProductBO = new DrawBaseProductBO();
//            drawSpaceBO.getDrawBaseProductBOList().add(drawBaseProductBO);
//            // darw base product
//            this.wrapDrawBaseProduct(drawBaseProductDTO, drawBaseProductBO, loginBO, drawHouse);
//
//
//            // design templet product
//            DrawDesignTempletProduct drawDesignTempletProduct = new DrawDesignTempletProduct();
//            DrawSpaceProductBO drawSpaceProductBO = new DrawSpaceProductBO(drawSpaceBO, drawBaseProductBO);
//            this.wrapDesignTempletProduct(drawBaseProductDTO, drawSpaceProductBO, loginBO, drawHouse);
//
//        }
//
//        return false;
//    }
//
//    boolean wrapDrawBaseProduct(DrawBaseProductDTO drawBaseProductDTO, DrawBaseProductBO drawBaseProductBO, UserLoginBO loginBO, DrawBaseHouse drawHouse) {
//        SystemDictionary smallTypeDict = dictionaryService.findOneByValueKey(drawBaseProductDTO.getSmallTypeValueKey());
//        if (smallTypeDict == null) {
//            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("数据字典未找到;valuekey = " + drawBaseProductDTO.getSmallTypeValueKey()));
//        }
//        SystemDictionary tyepDict = dictionaryService.findOneByValueKey(smallTypeDict.getType());
//        if (tyepDict == null) {
//            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("数据字典未找到;valuekey = " + drawBaseProductDTO.getBigTypeValueKey()));
//        }
//
//        DrawBaseProduct drawBaseProduct = new DrawBaseProduct();
//        drawBaseProductBO.setDrawBaseProduct(drawBaseProduct);
//
//        Date now = new Date();
//        String randomNumStr = Utils.generateRandomDigitString(6);
//        String sysCode = Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + randomNumStr;
//
//        // product_code
//        String productCode = drawBaseProductService.getProductCode(smallTypeDict.getValuekey());
//        drawBaseProduct.setProductCode(productCode);
//        drawBaseProduct.setProductName(tyepDict.getName() + "_" + smallTypeDict.getName() + "_" + randomNumStr);
//
//        drawBaseProduct.setSysCode(sysCode);
//        drawBaseProduct.setGmtCreate(now);
//        drawBaseProduct.setGmtModified(now);
//        drawBaseProduct.setCreator(loginBO.getLoginName());
//        drawBaseProduct.setModifier(loginBO.getLoginName());
//        drawBaseProduct.setProductLength(drawBaseProductDTO.getProductLength());
//        drawBaseProduct.setProductWidth(drawBaseProductDTO.getProductWidth());
//        drawBaseProduct.setProductHeight(drawBaseProductDTO.getProductHeight());
//        drawBaseProduct.setIsDeleted(DrawBaseProductConstant.DEFAULTS_IS_DELETED_);
//        drawBaseProduct.setHouseTypeValues(DrawBaseProductConstant.DEFAULTS_HOUSE_TYPES);
//        drawBaseProduct.setProductTypeValue(tyepDict.getValue() + "");
//        drawBaseProduct.setProductSmallTypeValue(smallTypeDict.getValue());
//        drawBaseProduct.setProductTypeMark(tyepDict.getValuekey());
//        drawBaseProduct.setProductSmallTypeMark(smallTypeDict.getValuekey());
//        drawBaseProduct.setSyncStatus("NOT_SYNCHRONIZED");
//        drawBaseProduct.setCodeIsEmploy(1);
//        drawBaseProduct.setFullPaveLength(drawBaseProductDTO.getProductLength());
//        drawBaseProduct.setBrandId(-1);
//        drawBaseProduct.setBakeBeforeFileId(drawBaseProductDTO.getProductFileId());
//
//        // 发布状态
//        drawBaseProduct.setPutawayState(getPutawayState(drawHouse));
//
//        // wrapper
//        // this.wrapper(drawBaseProductDTO, drawDesignTempletBO);
//        return this.boWrapper(drawBaseProductDTO, drawBaseProductBO);
//    }
//
//    boolean wrapDesignTempletProduct(DrawBaseProductDTO drawBaseProductDTO, DrawSpaceProductBO drawSpaceProductBO,
//                                     UserLoginBO loginBO, DrawBaseHouse drawHouse) {
//
//        if (drawBaseProductDTO.getModelType() == null) {
//            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("产品信息中的modleType字段不能为空"));
//        }
//
//        // public文件不保存在样板房产品表
////        if (DrawBaseProductConstant.MODEL_TYPE_PUBLIC.intValue() == drawBaseProductDTO.getModelType().intValue()) {
////            log.debug("跳过不处理的公共部分{}(productId)", drawBaseProductDTO.getUniqueId());
////            return true;
////        }
//
//        DrawBaseProductBO drawBaseProductBO = new DrawBaseProductBO ();
//        drawSpaceProductBO.getDrawSpaceBO().getDrawBaseProductBOList().add(drawBaseProductBO);
//
//        DrawBaseProduct drawBaseProduct = drawBaseProductBO.getDrawBaseProduct();
//        DrawDesignTempletProduct drawDesignTempletProduct = new DrawDesignTempletProduct();
//        drawBaseProductBO.setDrawDesignTempletProduct(drawDesignTempletProduct);
//
//        Date now = new Date();
//        drawDesignTempletProduct.setGmtCreate(now);
//        drawDesignTempletProduct.setGmtModified(now);
//        drawDesignTempletProduct.setCreator(loginBO.getLoginName());
//        drawDesignTempletProduct.setModifier(loginBO.getLoginName());
//        drawDesignTempletProduct.setIsDeleted(DrawDesignTempletProductConstant.DEFAULTS_IS_DELETED);
//        drawDesignTempletProduct.setUniqueId(drawBaseProductDTO.getUniqueId());
//        drawDesignTempletProduct.setWallOrientation(drawBaseProductDTO.getWallOrientation());
//        drawDesignTempletProduct.setWallType(drawBaseProduct.getWallType());
//        drawDesignTempletProduct.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
//
////        drawDesignTempletProduct.setBindParentProductid(Utils.listToStr(bingInfoMap.get(drawBaseProductDTO.getUniqueId())));
////        drawDesignTempletProduct.setProductId(drawBaseProduct.getId().intValue());
////      drawDesignTempletProduct.setDesignTempletId(Integer.valueOf(drawSpace.getDesignTempletId() + ""));
//
//        return this.dtpWrapper(drawBaseProductDTO, drawSpaceProductBO);
//    }
//
//    private void validate(DrawBaseProductDTO drawBaseProductDTO) {
//        if (drawBaseProductDTO.getModelType() == null) {
//            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("产品信息中的modleType字段不能为空"));
//        }
//
//        if (drawBaseProductDTO.getUniqueId() == null) {
//            throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("产品信息中的uniqueId字段不能为空"));
//        }
//
//        if (org.apache.commons.lang3.StringUtils.isEmpty(drawBaseProductDTO.getBigTypeValueKey())) {
//            throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR_BIGTYPEVALUEKEY_EMPTY);
//        }
//
//        if (org.apache.commons.lang3.StringUtils.isEmpty(drawBaseProductDTO.getSmallTypeValueKey())) {
//            throw new DrawBusinessException(false, ResponseEnum.PARAM_ERROR_BIGTYPEVALUEKEY_EMPTY);
//        }
//    }
//
//    /**
//     * 获取发布状态
//     *
//     * @param drawHouse
//     * @return
//     */
//    private Integer getPutawayState(DrawBaseHouse drawHouse) {
//        if (DrawBaseHouseConstant.DATA_PERSONAL.equals(drawHouse.getDataType())) {
//            return BaseProductStatusConstant.HAS_BEEN_RELEASE;
//        }
//
//        // 检查原先是否发布，如果发布继续为发布状态
//        if (drawHouse.getBaseHouseId() != null && drawHouse.getBaseHouseId() > 0) {
//            Integer putawayState = baseHouseMapper.getPutawayState(drawHouse.getBaseHouseId());
//            return (putawayState == null) ? BaseProductStatusConstant.TESTING : putawayState;
//        }
//
//        return BaseProductStatusConstant.TESTING;
//    }
//
//    private boolean boWrapper(Object warpSource, Object warpTarget) {
//        // 执行DrawSpaceCommon 处理
//        for (Wrapper handler : bpWrappers) {
//            boolean wrap = handler.wrap(warpSource, warpTarget);
//            if (wrap) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    private boolean dtpWrapper(Object warpSource, Object warpTarget) {
//        // 执行DrawSpaceCommon 处理
//        for (Wrapper handler : bpWrappers) {
//            boolean wrap = handler.wrap(warpSource, warpTarget);
//            if (wrap) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    private boolean filter(Object warpSource, Object warpTarget) {
//        // 执行DrawSpaceCommon 处理
//        for (BaseProductFilter handler : filters) {
//            boolean wrap = handler.filter(warpSource, warpTarget);
//            if (wrap) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * 拼装绑定点信息
//     * Map<String, List<String>> bingInfoMap
//     * key:主墙uniqueId
//     * value:背景墙draw_base_product -> id
//     * @param dto
//     * @return
//     */
//    private Map<String, List<Long>> getBingInfoMap(DrawBaseHouseSubmitDTO dto) {
//        // 先整理出一份uniqueId对应drawBaseProductId的map ->start
//        Map<String, Long> uniqueIdToDrawBaseProductIdMap = new HashMap<>();
//
//        for (DrawSpaceCommonDTO drawSpaceCommonDTO : dto.getSpaceCommonDTOList()) {
//            for (DrawBaseProductDTO drawBaseProductDTO : drawSpaceCommonDTO.getDrawBaseProductDTOList()) {
//                if (org.apache.commons.lang3.StringUtils.isEmpty(drawBaseProductDTO.getUniqueId())) {
//                    throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSE_SUBMIT_FAILED);
//                }
//                uniqueIdToDrawBaseProductIdMap.put(drawBaseProductDTO.getUniqueId(), drawBaseProductDTO.getProductId());
//            }
//        }
//        // 先整理出一份uniqueId对应drawBaseProductId的map ->end
//
//        Map<String, List<Long>> bingInfoMap = new HashMap<>();
//
//        for (DrawSpaceCommonDTO drawSpaceCommonDTO : dto.getSpaceCommonDTOList()) {
//            for (DrawBaseProductDTO drawBaseProductDTO : drawSpaceCommonDTO.getDrawBaseProductDTOList()) {
//                String bindUniqueId = drawBaseProductDTO.getBindUniqueId();
//                String uniqueId = drawBaseProductDTO.getUniqueId();
//                if (org.apache.commons.lang3.StringUtils.isEmpty(bindUniqueId)) {
//                    continue;
//                }
//                if (org.apache.commons.lang3.StringUtils.isEmpty(uniqueId)) {
//                    throw new DrawBusinessException(false, ResponseEnum.DRAWBASEHOUSE_SUBMIT_FAILED);
//                }
//                if (!uniqueIdToDrawBaseProductIdMap.containsKey(uniqueId)) {
//                    log.error("cannot find value by key:{}; map:{}", bindUniqueId, uniqueIdToDrawBaseProductIdMap.toString());
//                }
//                if (bingInfoMap.containsKey(bindUniqueId)) {
//                    List<Long> drawProductIdList = bingInfoMap.get(bindUniqueId);
//                    if (drawProductIdList == null || drawProductIdList.size() == 0) {
//                        drawProductIdList = new ArrayList<>();
//                        drawProductIdList.add(uniqueIdToDrawBaseProductIdMap.get(uniqueId));
//                        bingInfoMap.put(bindUniqueId, drawProductIdList);
//                    } else {
//                        drawProductIdList.add(uniqueIdToDrawBaseProductIdMap.get(uniqueId));
//                    }
//                } else {
//                    List<Long> drawProductIdList = new ArrayList<>();
//                    drawProductIdList.add(uniqueIdToDrawBaseProductIdMap.get(uniqueId));
//                    bingInfoMap.put(bindUniqueId, drawProductIdList);
//                }
//            }
//        }
//        return bingInfoMap;
//    }
//}
//
//
//@Setter
//@Getter
//@ToString
//class DrawSpaceBO implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    DrawSpaceCommon drawSpaceCommon;
////    DrawDesignTempletBO drawDesignTempletBO;
//
//    DrawDesignTemplet drawDesignTemplet;
//    List<DrawBaseProductBO> drawBaseProductBOList;
//    List<PublicProductInfoDTO> publicProductInfoDTOList;
//    List<RegionMark> regionMarks;
//
//    public DrawSpaceBO() {
//        this.regionMarks = buildRegionMark();
//    }
//
//    /**
//     * @return
//     */
//    private List<RegionMark> buildRegionMark() {
//        List<RegionMark> regions = new ArrayList<>();
//        RegionMarkConstant[] regionMarks = RegionMarkConstant.values();
//        for (RegionMarkConstant regionMark : regionMarks) {
//            regions.add(new RegionMark(regionMark));
//        }
//
//        return regions;
//    }
//}
//
//@Setter
//@Getter
//@ToString
//class DrawDesignTempletBO implements Serializable {
//
//    private static final long serialVersionUID = -9176234224310322317L;
//
//    DrawDesignTemplet drawDesignTemplet;
//    List<DrawBaseProductBO> drawBaseProductBOList;
//    List<PublicProductInfoDTO> publicProductInfoDTOList;
//}
//
//@Setter
//@Getter
//@ToString
//class DrawBaseProductBO implements Serializable {
//
//    private static final long serialVersionUID = 1556270042020108251L;
//
//    DrawBaseProduct drawBaseProduct;
//    DrawDesignTempletProduct drawDesignTempletProduct;
//}
//
//interface filter {
//    boolean filter(Object warpSource, Object warpTarget);
//}
//interface BaseProductFilter extends filter {}
//interface Wrapper {
//    /**
//     * @param wrapSource 包装的源
//     * @param wrapTarget 包装的目标
//     * @return
//     */
//    boolean wrap(Object wrapSource, Object wrapTarget);
//
//    default void validate(Object wrapSource, Object wrapTarget) {
//        Objects.requireNonNull(wrapSource, "warpSource 不能为空");
//        Objects.requireNonNull(wrapTarget, "warpTarget 不能为空");
//    }
//}
//interface DrawSpaceWrapper extends Wrapper {}
//interface DrawDesignTempletWrapper extends Wrapper {}
//interface DrawBaseProductWrapper extends Wrapper {}
//interface DrawDesignTempletProductWrapper extends Wrapper {}
//
///**
// * 处理绘制空间的名字、面积、空间编码、functionId等
// */
//@Service
//class DefaultDrawSpaceWrapper implements DrawSpaceWrapper {
//
//    @Autowired
//    SystemDictionaryService dictionaryService;
//
//    @Override
//    @Transactional(readOnly = true)
//    public boolean wrap(Object wrapSource, Object wrapTarget) {
//
//        this.validate(wrapSource, wrapTarget);
//
//        DrawSpaceBO drawSpaceBO = (DrawSpaceBO) wrapTarget;
//        DrawSpaceCommonDTO drawSpace = (DrawSpaceCommonDTO) wrapSource;
//
//        // 类型/面积
//        SystemDictionary dict = null;
//        if (!StringUtils.isEmpty(drawSpace.getSpaceCommonFunctionValueKey())) {
//            dict = dictionaryService.findOneByValueKey(drawSpace.getSpaceCommonFunctionValueKey());
//        }
//
//        SystemDictionary area = null;
//        if (!StringUtils.isEmpty(drawSpace.getSpaceCommonArea()) && dict != null) {
//            area = dictionaryService.findOneByTypeAndArea(dict.getValuekey(),
//                    Double.valueOf(drawSpace.getSpaceCommonArea()));
//        }
//
//        // 空间类型
//        drawSpaceBO.getDrawSpaceCommon().setSpaceFunctionId(dict == null ? null : dict.getValue());
//
//        // 空间编码
//        drawSpaceBO.getDrawSpaceCommon().setSpaceCode(this.getSpaceCode(area, Utils.generateRandomDigitString(6)));
//
//        // 空间名
//        drawSpaceBO.getDrawSpaceCommon().setSpaceName(this.getSpaceName(dict, area, drawSpace.getSpaceCommonArea()));
//
//        // 空间面积
//        String nullDefault = Utils.getDecimalFormat(new Double(drawSpace.getSpaceCommonArea()));
//        drawSpaceBO.getDrawSpaceCommon().setSpaceAreas(area == null ? nullDefault : Objects.toString(area.getValue(), nullDefault));
//
//        return false;
//    }
//
//    private String getSpaceCode(SystemDictionary dict, String randomNumStr) {
//        return (dict == null ? "unknown" : dict.getAtt6()) + "_" + System.nanoTime() + "_" + randomNumStr;
//    }
//
//    private String getSpaceName(SystemDictionary dict, SystemDictionary area, String nullDefault) {
//        nullDefault = Utils.getDecimalFormat(new Double(nullDefault));
//        String spaceArea = Objects.toString(area == null ? nullDefault : area.getValue(), nullDefault);
//        return (dict == null ? DrawSpaceCommonService.DEFAULTS_SPACE_NAME : Objects.toString(dict.getName(),
//                DrawSpaceCommonService.DEFAULTS_SPACE_NAME)) + spaceArea + "平";
//    }
//}
//
//@Slf4j
//@Service
//class DefaultDrawDesignTempletHandler implements DrawDesignTempletWrapper {
//
//    @Override
//    public boolean wrap(Object wrapSource, Object wrapTarget) {
//
//        this.validate(wrapSource, wrapTarget);
//
//        DrawDesignTemplet drawDesignTemplet = (DrawDesignTemplet) wrapTarget;
//        DrawSpaceCommonDTO source = (DrawSpaceCommonDTO) wrapSource;
//
//        // 卫生间处理 space_layout_type
//        if (HouseType.TOILET.toString().equalsIgnoreCase(source.getSpaceCommonFunctionValueKey())) {
//            String spaceLayoutType = getSpaceLayoutType(source.getDrawBaseProductDTOList());
//            drawDesignTemplet.setSpaceLayoutType(spaceLayoutType);
//            log.info("卫生间 spaceLayoutType 处理 ==> {}", spaceLayoutType);
//        }
//
//        return false;
//    }
//
//    /**
//     * 处理卫生间 space layout type 值
//     * </p>
//     * {@link com.sandu.common.constant.SpaceLayoutType}
//     * </p>
//     *
//     * @param dtp
//     * @return (A, B, AB, N)
//     */
//    private String getSpaceLayoutType(List<DrawBaseProductDTO> dtp) {
//        if (dtp == null || dtp.isEmpty()) {
//            return SpaceLayoutType.N.toString();
//        }
//
//        // A => basic_edba, basic_baba
//        // B => basic_ahba, basic_dzas, basic_asba
//        // AB => A + B 都含有
//        // N => 啥都没有
//        int DEFAULTS_INITIAL_CAPACITY = 3;
//        Set<String> set = new HashSet<>(DEFAULTS_INITIAL_CAPACITY);
//        for (DrawBaseProductDTO p : dtp) {
//            SpaceLayoutType layoutType = SpaceLayoutType.contains(p.getSmallTypeValueKey());
//            set.add(layoutType.toString());
//            // break;
//            if (set.size() >= DEFAULTS_INITIAL_CAPACITY) {
//                break;
//            }
//        }
//
//        if (set.isEmpty()) {
//            return SpaceLayoutType.N.toString();
//        }
//
//        String layout = set.stream().collect(Collectors.joining());
//        if (layout.length() >= 2 && layout.contains(SpaceLayoutType.N.toString())) {
//            layout = layout.replace(SpaceLayoutType.N.toString(), "");
//        }
//
//        return layout.matches(Regex.SPACE_LAYOUT_B_TYPE.getValue())
//                ? org.apache.commons.lang3.StringUtils.reverse(layout) : layout;
//    }
//}
//
//@Slf4j
//@Service
//class DefaultBaseProductHandler implements BaseProductFilter, DrawBaseProductWrapper {
//
//    @Autowired
//    DoorAttr doorAttr;
//
//    @Override
//    public boolean filter(Object wrapSource, Object wrapTarget) {
//
//        this.validate(wrapSource, wrapTarget);
//
//        DrawSpaceBO drawSpaceBO = (DrawSpaceBO) wrapTarget;
//        DrawBaseProductDTO source = (DrawBaseProductDTO) wrapSource;
//
//        // 软装部分不处理
//        if (DrawBaseProductConstant.MODEL_TYPE_SOFT.intValue() == source.getModelType().intValue()) {
//            source.setSoft(true);
//            return true;
//        }
//
//        // 公共部分不处理，保存公共
//        if (DrawBaseProductConstant.MODEL_TYPE_PUBLIC.intValue() == source.getModelType().intValue()) {
//            // 识别为窗框/栏杆之类/阳台门
//            if (source.getProductFileId() == null) {
//                throw new DrawBusinessException(false, ResponseEnum.CUSTOM_RESPONSEENUM.setMessage("产品信息中的productFileId字段不能为空"));
//            }
//
//            PublicProductInfoDTO publicProductInfoDTO = new PublicProductInfoDTO(source.getProductFileId(), source.getUniqueId());
//            drawSpaceBO.getPublicProductInfoDTOList().add(publicProductInfoDTO);
//            return true;
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean wrap(Object wrapSource, Object wrapTarget) {
//        this.validate(wrapSource, wrapTarget);
//
//        DrawBaseProductBO drawBaseProductBO = (DrawBaseProductBO) wrapTarget;
//        DrawBaseProductDTO source = (DrawBaseProductDTO) wrapSource;
//
//        // 厨房门、入户门、房间门、卫生间门属性处理
//        this.doorAttrWrap(source, drawBaseProductBO);
//
//        return false;
//    }
//
//    /**
//     * 厨房门、入户门、房间门、卫生间门属性处理
//     *
//     * @param source
//     * @param drawBaseProductBO
//     * @return
//     */
//    private boolean doorAttrWrap(DrawBaseProductDTO source, DrawBaseProductBO drawBaseProductBO) {
//        // 厨房门、入户门、房间门、卫生间门属性处理
//        if (doorAttr.contains(drawBaseProductBO.getDrawBaseProduct().getProductSmallTypeMark()) && doorAttr.containsProAttrType(source.getProAttrType())) {
//            DoorAttr.DoorAttrType attrType = doorAttr.getDoorAttrType(drawBaseProductBO.getDrawBaseProduct().getProductSmallTypeMark());
//            if (attrType != null) {
//                drawBaseProductBO.getDrawBaseProduct().setProAttrKey(Objects.toString(attrType.getLongCode(), ""));
//                String attrValType = doorAttr.getDoorAttrValueType(drawBaseProductBO.getDrawBaseProduct().getProductSmallTypeMark(), source.getProAttrType());
//                drawBaseProductBO.getDrawBaseProduct().setProAttrValKey(attrValType);
//                log.info("处理门的属性，{}(proAttrType), {}(attrType), {}(attrValType)", attrType, attrValType, source.getProAttrType());
//            }
//        }
//
//        return false;
//    }
//}
//
//@Slf4j
//@Service
//class DefaultDesignTempletProductHandler implements DrawDesignTempletProductWrapper {
//
//    @Autowired
//    BaseProductMapper baseProductMapper;
//
//    @Autowired
//    ResModelService resModelService;
//
//    @Autowired
//    DrawBaseProductService drawBaseProductService;
//
//    @Override
//    public boolean wrap(Object wrapSource, Object wrapTarget) {
//        this.validate(wrapSource, wrapTarget);
//
//        DrawSpaceProductBO drawSpaceProductBO = (DrawSpaceProductBO) wrapTarget;
//        DrawBaseProductDTO source = (DrawBaseProductDTO) wrapSource;
//
//        this.groupProductIndexWrapper(source, drawSpaceProductBO.getDrawBaseProductBO().getDrawDesignTempletProduct());
//        this.changedProductWrapper(source, drawSpaceProductBO.getDrawBaseProductBO().getDrawDesignTempletProduct());
//        this.regionMarkWrapper(source, drawSpaceProductBO);
//
//        return false;
//    }
//
//    public boolean groupProductIndexWrapper(DrawBaseProductDTO source, DrawDesignTempletProduct drawDesignTempletProduct) {
//        // 组合产品处理
//        if (source.getGroupProductId() != null && source.getGroupProductId() > 0) {
//            // 组合产品
//            drawDesignTempletProduct.setProductGroupId(source.getGroupProductId());
//            // 是否主产品 ==> 0-> 否、1-> 是
//            drawDesignTempletProduct.setIsMainProduct(source.getIsMainProduct());
//            // 组合类型 ==> 0-> 组合、1-> 结构
//            drawDesignTempletProduct.setGroupType(DrawDesignTempletProductConstant.GROUP_PRODUCT_TYPE);
//            // 组合产品唯一标识，判断存在两个相同的组合产品的唯一标识
//            drawDesignTempletProduct.setGroupUniqueId(source.getGroupUniqueId());
//
//            // 通用app需要这样处理,用来区分多个相同的组合产品;plan_group_id.
//            // 处理组合的产品，相同的组合产品的planGroupId = groupProductId_后缀一样
////            drawDesignTempletProduct.setPlanGroupId(source.getGroupProductId() + "_" + getGroupIndex(idxMap, source.getGroupUniqueId()));
//        }
//
//        return false;
//    }
//
//    /**
//     * 处理组合的产品，相同的组合产品的planGroupId = groupProductId_后缀一样
//     *
//     * @param idxMap
//     * @param groupUniqueId
//     * @return
//     */
//    private String getGroupIndex(Map<String, String> idxMap, String groupUniqueId) {
//        if (org.apache.commons.lang3.StringUtils.isEmpty(groupUniqueId)) {
//            throw new BusinessException(false, ResponseEnum.GROUP_UNIQUEID_EMPTY);
//        }
//
//        if (idxMap.containsKey(groupUniqueId)) {
//            return idxMap.get(groupUniqueId);
//        } else {
//            String groupIndex = Objects.toString(System.nanoTime());
//            idxMap.put(groupUniqueId, groupIndex);
//            return groupIndex;
//        }
//    }
//
//    public boolean changedProductWrapper(DrawBaseProductDTO source, DrawDesignTempletProduct drawDesignTempletProduct) {
//        // 加入软装白膜逻辑
//        if (source.isSoft()) {
//            drawDesignTempletProduct.setCreateProductStatus(DrawDesignTempletProductConstant.SOFT_PRODUCT);
//
//            // 处理拉伸过的软装白膜产品
//            // copy一条被拉伸的白膜产品，fullPaveLength = 被拉伸的长度
//            this.handlerChangedSoftProduct(source, drawDesignTempletProduct);
//        }
//        return false;
//    }
//
//    /**
//     * 处理拉伸过的软装白膜产品.
//     * </p>
//     * copy一条拉被伸的白膜产品，fullPaveLength = 被拉伸的长度
//     *
//     * @param baseProduct
//     * @param wrapProduct
//     */
//    private void handlerChangedSoftProduct(DrawBaseProductDTO baseProduct, DrawDesignTempletProduct wrapProduct) {
//        if (baseProduct == null) {
//            log.warn("参数[baseProduct]为空！");
//            return;
//        }
//
//        // 是否拉伸过 == !1
//        if (!DrawBaseProductConstant.IS_CHANGED_SOFT.equals(baseProduct.getIsChanged())) {
//            log.debug("白膜产品 {}(productId)没有拉伸，不处理", baseProduct.getProductId());
//            return;
//        }
//
//        log.info("处理拉伸的软装白膜产品, {}(productId)", baseProduct.getProductId());
//        BaseProduct newProduct = baseProductMapper.selectByPrimaryKey(baseProduct.getProductId());
//
//        Objects.requireNonNull(newProduct, ResponseEnum.SOFT_PRODUCT_NOT_FOUND.getMessage());
//        Objects.requireNonNull(newProduct.getWindowsU3dmodelId(), ResponseEnum.U3D_MODEL_NOT_FOUND.getMessage());
//
//        // copy 产品的模型文件
//        ResModel resFile = resModelService.selectByPrimaryKey(newProduct.getWindowsU3dmodelId());
//        Long windowsU3dmodelId = resModelService.copyModel(resFile, SystemConfigEnum.BASEPRODUCT_U3DMODEL_WINDOWSPC_UPLOAD.getFileKey(), false);
//        if (windowsU3dmodelId == null) {
//            throw new DrawBusinessException(false, ResponseEnum.FILE_COPY_FAILED);
//        }
//
//        Date now = new Date();
//        // id ==> null
//        newProduct.setId(null);
//        newProduct.setGmtCreate(now);
//        newProduct.setGmtModified(now);
//        // 拉伸标识
//        newProduct.setIsStretched(DrawBaseProductConstant.IS_CHANGED_SOFT);
//        // 全铺长度
//        newProduct.setFullPaveLength(baseProduct.getProductLength());
//        // u3d model 文件
//        newProduct.setWindowsU3dmodelId(windowsU3dmodelId.intValue());
//        // product_code
//        String productCode = drawBaseProductService.getProductCode(baseProduct.getSmallTypeValueKey());
//        newProduct.setProductCode(productCode);
//
//        // save
//        baseProductMapper.insertSelective(newProduct);
//        wrapProduct.setProductId(newProduct.getId().intValue());
//    }
//
//
//    /**
//     * 地面、天花区域标识处理
//     * 相同的类型以基数累加，
//     * eg: 同一个空间里有5块地面以基数10开始累加，regionMark ==> 10 11...15;
//     * 不同类型基数不一样{@link com.sandu.common.constant.RegionMarkConstant}
//     *
//     * @param source
//     * @param drawSpaceProductBO
//     * @return
//     */
//    public boolean regionMarkWrapper(DrawBaseProductDTO source, DrawSpaceProductBO drawSpaceProductBO) {
//        if (RegionMarkConstant.contains(source.getSmallTypeValueKey())) {
//            int regionMark = getRegionMark(drawSpaceProductBO.getDrawSpaceBO().getRegionMarks(), source.getSmallTypeValueKey());
//            log.debug("地面、天花区域标识处理, regionMark ==> {}", regionMark);
//            if (regionMark > 0) {
//                drawSpaceProductBO.getDrawBaseProductBO().getDrawDesignTempletProduct().setRegionMark(Objects.toString(regionMark, Utils._ONE_VALUE));
//            }
//        }
//        return false;
//    }
//
//    /**
//     *
//     * 地面、天花区域标识处理.
//     *
//     * <pre>
//     * 相同的类型以基数累加.
//     * 	eg: 同一个空间里有5块地面以基数10开始累加，regionMark ==> 10 11...15;
//     * 不同类型基数不一样{@link com.sandu.common.constant.RegionMarkConstant}
//     * </pre>
//     *
//     * @param regions
//     * @param valueKey
//     * @return -1 标识不是地面类型处理
//     */
//    private int getRegionMark(List<RegionMark> regions, String valueKey) {
//        if (regions == null || regions.isEmpty()) {
//            return -1;
//        }
//
//        for (RegionMark region : regions) {
//            int regionMark = region.getRegionMark(valueKey);
//            if (regionMark > 0) {
//                return regionMark;
//            }
//        }
//
//        return -1;
//    }
//}
//
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//class DrawSpaceProductBO implements Serializable {
//    private static final long serialVersionUID = 4674031331676683096L;
//    DrawSpaceBO drawSpaceBO;
//    DrawBaseProductBO drawBaseProductBO;
//}
//
///**
// * 区域标识处理类
// *
// * created by songjianming@sanduspace.cn on 2018/3/19
// */
//class RegionMark {
//
//    private int index;
//    private RegionMarkConstant region;
//
//    RegionMark(RegionMarkConstant region) {
//        this.region = region;
//        this.index = region.getIndex();
//    }
//
//    int getRegionMark(String valueKey) {
//        if (this.region.getValuekey().equals(valueKey)) {
//            return index++;
//        }
//        return -1;
//    }
//}