package com.sandu.job;

import com.sandu.api.base.service.RedisService;
import com.sandu.api.designplan.service.biz.DesignPlanRecommendedBizService;
import com.sandu.node.constant.NodeInfoConstant;
import com.sandu.node.model.NodeInfoDetail;
import com.sandu.system.service.NodeDataService;
import com.sandu.system.service.NodeInfoBizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DesignPlanJob {
    @Autowired
    private DesignPlanRecommendedBizService designPlanRecommendedBizService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private NodeInfoBizService nodeInfoBizService;
    @Autowired
    private NodeDataService nodeDataService;
    // 刷方案点赞量、收藏量、浏览量相关数据
    public static Map<String, String> map;
    // 刷店铺浏览量的相关数据
    public static List<String> aShopList;
    public static List<String> bShopList;
    public static Map<String, String> shopMap;

    public void run() {
        if (this.map != null && this.getRunning(this.map)) {
            // redis锁
            String key = NodeInfoConstant.NODE_INFO_DATA_SYNCHRONIZED_THREAD_KEY;
            String value = UUID.randomUUID().toString();
            if (redisService.set(key, value, "NX", "EX", 600)) {
                log.info("----------------------------开始增加方案点赞收藏浏览量数据!!!");
                ExecutorService service = Executors.newFixedThreadPool(5);
                // 执行推荐方案任务
                this.executeRecommended(service);
                // 执行全屋方案任务
                this.executeFullHouse(service);
                service.shutdown();
                while (true) {
                    if (service.isTerminated()) {
                        // 释放redis锁
                        if (value.equals(redisService.get(key))) {
                            redisService.del(key);
                        }
                        break;
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }
        this.increaseShopView();
    }

    private void executeRecommended(ExecutorService service){
        // 推荐方案ID集合和置顶的推荐方案ID集合
        List<Integer> recommendedIdList = designPlanRecommendedBizService.getAllRecommendedId();
        List<Integer> superiorRecommendedIdList = designPlanRecommendedBizService.getAllSuperiorRecommendedId();
        // 去重
        if (recommendedIdList != null && superiorRecommendedIdList != null) {
            recommendedIdList = recommendedIdList.stream()
                    .filter(recommendedId -> !superiorRecommendedIdList.contains(recommendedId))
                    .collect(Collectors.toList());
        }
        // 置顶方案数量较少，直接执行
        if (superiorRecommendedIdList != null && superiorRecommendedIdList.size() > 0) {
            this.increase(superiorRecommendedIdList, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_RECOMMENDED, 1, this.map);
        }
        // 剩下的方案多线程执行
        if (recommendedIdList != null && recommendedIdList.size() > 0) {
            this.execute(recommendedIdList, service, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_RECOMMENDED, this.map);
        }
    }

    private void executeFullHouse(ExecutorService service){
        // 全屋方案ID集合和置顶的全屋方案ID集合
        List<Integer> fullHouseIdList = designPlanRecommendedBizService.getAllFullHouseId();
        List<Integer> superiorFullHouseIdList = designPlanRecommendedBizService.getAllSuperiorFullHouseId();
        // 去重
        if (fullHouseIdList != null && superiorFullHouseIdList != null) {
            fullHouseIdList = fullHouseIdList.stream()
                    .filter(recommendedId -> !superiorFullHouseIdList.contains(recommendedId))
                    .collect(Collectors.toList());
        }
        // 置顶方案数量较少，直接执行
        if (superiorFullHouseIdList != null && superiorFullHouseIdList.size() > 0) {
            this.increase(superiorFullHouseIdList, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_FULL_HOUSE, 1, this.map);
        }
        // 剩下的方案多线程执行
        if (fullHouseIdList != null && fullHouseIdList.size() > 0) {
            this.execute(fullHouseIdList, service, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_FULL_HOUSE, this.map);
        }
    }

    private void execute(List<Integer> idList, ExecutorService service, Byte nodeType, Map<String, String> map){
        int count = idList.size();
        int listSize = (int)Math.ceil(count/3.0) > 10000 ? 10000 : (int)Math.ceil(count/3.0);
        int threadNum = (int) Math.ceil(count / (double)listSize);
        for (int i = 0; i < threadNum; i++) {
            List<Integer> subIdList = idList.subList(i * listSize, (i + 1) * listSize > count ? count : (i + 1) * listSize);
            final int level = i + 1;
            service.submit(() -> this.increase(subIdList, nodeType, level > 3 ? 3 : level, map));
        }
    }

    private void increase(List<Integer> subIdList, Byte nodeType, int level, Map<String, String> map) {
        int fail = 0;
        long startTime = System.currentTimeMillis();
        for (Integer id : subIdList) {
            Integer nodeId = nodeInfoBizService.registerNodeInfo(id, nodeType);
            if (nodeId != null && nodeId > 0) {
                try {
                    // 增加收藏数
                    Integer favoriteNum = this.getNum(0 + ((level - 1) * 6),1 + ((level - 1) * 6), map);
                    this.increase(nodeId, NodeInfoConstant.SYSTEM_DICTIONARY_DETAIL_TYPE_VIRTUAL_FAVORITE, favoriteNum);
                    // 增加点赞数
                    Integer likeNum = this.getNum(2 + ((level - 1) * 6),3 + ((level - 1) * 6), map);
                    this.increase(nodeId, NodeInfoConstant.SYSTEM_DICTIONARY_DETAIL_TYPE_VIRTUA_LIKE, likeNum);
                    // 增加浏览数
                    Integer viewNum = this.getNum(4 + ((level - 1) * 6),5 + ((level - 1) * 6), map);
                    this.increase(nodeId, NodeInfoConstant.SYSTEM_DICTIONARY_DETAIL_TYPE_VIRTUA_VIEW, viewNum);

                }catch (Exception e){
                    log.error(e.getMessage() + "----------------------自动增加方案点赞收藏数,nodeId:{}，失败数:{}", nodeId, (++fail));
                }
            }else {
                log.error("----------------------自动增加方案点赞收藏数，失败数:{}", (++fail));
            }
        }
        log.info("----------------------自动增加方案点赞收藏数成功,成功数:{}, 耗时:{}ms", subIdList.size(), System.currentTimeMillis() - startTime);
    }

    private void increase(Integer nodeId, Byte detailType, int num){
        // key和桶key
        String key = detailType + ":" + nodeId;
        String bucketKey = NodeInfoConstant.BUCKET_KEY_PREFIX_NODE_INFO_DETAIL +
                this.getBucketId(key, NodeInfoConstant.BUCKET_KEY_BIT_LENGTH);

        this.createNodeInfoDetail(nodeId, detailType);
        if (designPlanRecommendedBizService.increase(nodeId, detailType, num) > 0) {
            redisService.mapIncrby(bucketKey, key, (long) num);
        }
    }

    private String getBucketId(String key, int bit) {
        try {
            return new String(this.getBucketId(key.getBytes(), bit));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("获取BucketId异常!");
        }
    }

    private byte[] getBucketId(byte[] key, int bit) throws NoSuchAlgorithmException {
        // 获取MD5散列值
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        mdInst.update(key);
        byte[] md5 = mdInst.digest();
        // byte数组存放BucketId，公式(bit - 1) / 7 + 1计算出存放bit位数所需要的字节数
        byte[] bucketId = new byte[(bit - 1) / 7 + 1];
        // 01111111111...11111110b
        int a = (int) Math.pow(2, bit % 7) - 2;
        md5[bucketId.length - 1] = (byte) (md5[bucketId.length - 1] & a);
        System.arraycopy(md5, 0, bucketId, 0, bucketId.length);
        // 将非ASCII码转换为ASCII码(ASCII码在0~127之间）
        for (int i = 0; i < bucketId.length; i++) {
            if (bucketId[i] < 0) {
                bucketId[i] &= 127;
            }
        }
        return bucketId;
    }

    private int getNodeInfoDetail(Integer nodeId, Byte detailType){
        NodeInfoDetail nodeInfoDetail = new NodeInfoDetail();
        nodeInfoDetail.setNodeId(nodeId);
        nodeInfoDetail.setDetailType(detailType);
        nodeInfoDetail.setValue(0);
        nodeInfoDetail.setModifier("system");
        nodeInfoDetail.setGmtModified(new Date());
        nodeInfoDetail.setUuid(UUID.randomUUID().toString());
        nodeInfoDetail.setCreator("system");
        nodeInfoDetail.setGmtCreate(nodeInfoDetail.getGmtModified());
        nodeInfoDetail.setIsDeleted(new Byte("0"));
        nodeInfoDetail.setRemark("系统自动生成");
        return nodeDataService.insertNodeInfoDetail(nodeInfoDetail);
    }

    private int getNum(int start, int end, Map<String, String> map){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String hour = calendar.get(Calendar.HOUR_OF_DAY) + "";

        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String key = "," + entry.getKey() + ",";
            if (key.contains("," + hour + ",")) {
                String value = entry.getValue();
                String[] values = value.split(",");
                int min = Integer.parseInt(values[start]);
                int max = Integer.parseInt(values[end]);
                double ratio = Math.random();
                return min + (int)(ratio * (max - min + 1));
            }
        }
        return 0;
    }

    private boolean getRunning(Map<String, String> map){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String hour = calendar.get(Calendar.HOUR_OF_DAY) + "";

        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String key = "," + entry.getKey() + ",";
            if (key.contains("," + hour + ",")) {
                return true;
            }
        }
        return false;
    }

    private void createNodeInfoDetail(Integer nodeId, Byte detailType){
        NodeInfoDetail nodeInfoDetailQuery = new NodeInfoDetail();
        nodeInfoDetailQuery.setDetailType(detailType);
        nodeInfoDetailQuery.setNodeId(nodeId);
        if (!nodeInfoBizService.isNodeInfoDetailExists(nodeId, detailType)){
            int num = this.getNodeInfoDetail(nodeId, detailType);
            if (num <= 0){
                throw new RuntimeException("生成节点详情失败!");
            }
        }
    }

    // 刷店铺浏览量
    private void increaseShopView(){
        if (this.shopMap != null && this.getRunning(this.shopMap)) {
            List<Integer> a = designPlanRecommendedBizService.getShopIdListByNameList(aShopList);
            List<Integer> b = designPlanRecommendedBizService.getShopIdListByNameList(bShopList);
            List<Integer> ab = new ArrayList<>((a == null ? 0 : a.size()) + (b == null ? 0 : b.size()));
            if (a != null && a.size() > 0) {
                this.increase(a, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_SHOP, 1, this.shopMap);
                ab.addAll(a);
            }
            if (b != null && b.size() > 0) {
                this.increase(b, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_SHOP, 2, this.shopMap);
                ab.addAll(b);
            }
            List<Integer> all = designPlanRecommendedBizService.getShopIdList(ab);
            if (all != null && all.size() > 0) {
                int count = all.size();
                int listSize = (int)Math.ceil(count/3.0) > 1000 ? 1000 : (int)Math.ceil(count/3.0);
                int threadNum = (int) Math.ceil(count / (double)listSize);
                for (int i = 0; i < threadNum; i++) {
                    List<Integer> subIdList = all.subList(i * listSize, (i + 1) * listSize > count ? count : (i + 1) * listSize);
                    final int level = i + 1;
                    this.increase(subIdList, NodeInfoConstant.SYSTEM_DICTIONARY_NODE_TYPE_SHOP, level > 3 ? 3 : level, this.shopMap);
                }
            }
        }
    }
}
