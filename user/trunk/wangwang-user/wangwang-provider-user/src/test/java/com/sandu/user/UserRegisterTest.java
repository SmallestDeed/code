package com.sandu.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2017/12/9 16:33
 */

@Slf4j
public class UserRegisterTest {

    public static void main(String[] args) {

        List<LotteryRate> items = new ArrayList<>();

//        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(10).name("波音735一架").lotteryRate(1).build()).build());
//        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(10).name("iPhone x").lotteryRate(2).build()).build());
//        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(10).name("震动棒").lotteryRate(3).build()).build());
//        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(10).name("加班卡").lotteryRate(5).build()).build());
//        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(10).name("脑残片一盒").lotteryRate(9).build()).build());
//        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(10).name("东莞一条龙服务").lotteryRate(5).build()).build());
//        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(10).name("天上人间一夜游").lotteryRate(5).build()).build());
//        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(10).name("充气娃娃").lotteryRate(10).build()).build());
//        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(10).name("谢谢参与").lotteryRate(60).build()).build());

        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(0).name("电影票3张").lotteryRate(0).build()).build());
        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(50).name("现金红包").lotteryRate(1).build()).build());
        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(-1).name("1000套室内设计资料").lotteryRate(40).build()).build());
        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(0).name("2019元回家基金（可折现）").lotteryRate(0).build()).build());
        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(0).name("脑残片一盒").lotteryRate(0).build()).build());
        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(100).name("10度币（可用于购买版权收费方案）").lotteryRate(9).build()).build());
        items.add(LotteryRate.builder().award(ActivityAward.builder().qty(-1).name("谢谢参与").lotteryRate(50).build()).build());

        Lottery lottery = new Lottery(items);
        for (int i = 0; i < 1; i ++) {
            lottery.run();
        }
    }

    @Getter
    @Setter
    @Builder
    @ToString
    public static class ActivityAward {
        String name;
        int qty;
        int lotteryRate;
    }

    public static class Lottery {
        int begin = 0;
        int radix = 100;
        List<LotteryRate> lotteryRates;

        public Lottery(List<LotteryRate> items) {
            lotteryRates = new ArrayList<>(items.size());
            this.initiate(items);
            log.error("lotteryRates => {}", lotteryRates);
        }

        void initiate(List<LotteryRate> items) {
            // 排序
            items.sort(Comparator.comparingInt(o -> o.getAward().getLotteryRate()));
            for (int index = 0; index < items.size(); index++) {
                LotteryRate lotteryRate = items.get(index);
                int total = lotteryRate.getAward().getLotteryRate();

                lotteryRate.setBegin(total <= 0 ? 0 : (index == 0 ? begin : lotteryRates.get(index - 1).getEnd() + 1));
                lotteryRate.setEnd(total <= 0 ? 0 : (index == 0 ? total : lotteryRates.get(index - 1).getEnd() + total));

                lotteryRates.add(lotteryRate);
            }
        }

        LotteryRate run() {
            int hit = (int)(Math.random() * radix);
            for (LotteryRate item : lotteryRates) {
                if (item.test(hit)) {
                    log.error("中奖了 {}(hit)\t抽中 {}", hit, item.getAward().getName());
                    return item;
                }
            }

            return null;
        }
    }

    @Getter
    @Setter
    @Builder
    public static class LotteryRate {
        int begin;
        int end;

        ActivityAward award;

        boolean test(int hit) {
            return hit >= begin && hit <= end;
        }

        @Override
        public String toString() {
            return award.getLotteryRate() + "@[" + begin + "-" + end + "]";
        }
    }
}
