import com.sandu.ProductProvider;
import com.sandu.goods.dao.BaseGoodsSPUMapper;
import com.sandu.goods.model.BO.GoodsBO;
import com.sandu.goods.output.ActivityVO;
import com.sandu.order.MallBaseOrder;
import com.sandu.order.dao.MallBaseOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductProvider.class)
public class GroupPurchaseActivityTests {

    @Autowired
    BaseGoodsSPUMapper baseGoodsSPUMapper;

    @Autowired
    MallBaseOrderMapper mallBaseOrderMapper;

//    @Test
    public void test() {
        List<GoodsBO> listGoodsBO = new ArrayList<>();

        GoodsBO goodsBO1 = new GoodsBO();
        goodsBO1.setId(630297);
        GoodsBO goodsBO2 = new GoodsBO();
        goodsBO2.setId(58987);
        GoodsBO goodsBO3 = new GoodsBO();
        goodsBO3.setId(93375);

        listGoodsBO.add(goodsBO1);
        listGoodsBO.add(goodsBO2);
        listGoodsBO.add(goodsBO3);

        Map<Integer, ActivityVO> integerActivityVOMap = mapGoodsActivity(listGoodsBO);
        log.error("{}", integerActivityVOMap);
    }

    /**
     * @param listGoodsBO
     */
    private Map<Integer, ActivityVO> mapGoodsActivity(List<GoodsBO> listGoodsBO) {
        if (listGoodsBO == null || listGoodsBO.isEmpty()) {
            log.info("拼团信息：为找到商品");
            return new HashMap<>(0);
        }

        List<ActivityVO> listActivityVO = baseGoodsSPUMapper.listGoodsActivity(listGoodsBO);
        if (listActivityVO == null || listActivityVO.isEmpty()) {
            return new HashMap<>(0);
        }

        return listActivityVO.stream().collect(Collectors.toMap(ActivityVO::getId, a -> a, (p, n) -> n));
    }

    @Test
    public void test2() {
        List<MallBaseOrder> listOrder = new ArrayList<>();

        MallBaseOrder order1 = new MallBaseOrder();
        order1.setActivityId(1);

        MallBaseOrder order2 = new MallBaseOrder();
        order2.setActivityId(2);

        MallBaseOrder order3 = new MallBaseOrder();
        order3.setActivityId(14);

        listOrder.add(order1);
        listOrder.add(order2);
        listOrder.add(order3);

        Map<Integer, ActivityVO> integerActivityVOMap = mapOrderGoodsActivity(listOrder);
        log.error("{}", integerActivityVOMap);
    }

    /**
     * added by songjianming@sanduspace.cn on 2018/12/13
     * 单的活动信息
     *
     * @param listOrder
     */
    private Map<Integer, ActivityVO> mapOrderGoodsActivity(List<MallBaseOrder> listOrder) {
        if (listOrder == null || listOrder.isEmpty()) {
            log.info("拼团订单：没有订单信息");
            return new HashMap<>(0);
        }

        List<ActivityVO> listActivity = mallBaseOrderMapper.listOrderGoodsActivity(listOrder);
        if (listActivity == null || listActivity.isEmpty()) {
            log.info("拼团订单：未找到拼团订单信息", listActivity.size());
            return new HashMap<>(0);
        }

        log.info("拼团订单：找到{}个拼团订单信息", listActivity.size());

        return listActivity.stream().collect(Collectors.toMap(ActivityVO::getId, a -> a, (p, n) -> n));
    }
}
