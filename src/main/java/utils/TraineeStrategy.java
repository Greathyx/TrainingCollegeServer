package utils;

/**
 * 计算会员等级，优惠折扣和积分的工具类
 */
public class TraineeStrategy {

    private final static int[] levelArr = {100, 200, 500, 1000, 2000,
            5000, 10000, 20000, 50000, 100000};

    private final static double[] discountArr = {0.99, 0.98, 0.95, 0.9,
            0.88, 0.85, 0.8, 0.77, 0.75, 0.7};

    /**
     * 会员等级策略
     * 根据累计消费金额返回不同的等级
     * 最高等级为10级
     *
     * @param expenditure 累计消费
     * @return 会员等级
     */
    public static int getLevel(double expenditure) {
        int level = 0;
        for (int integer : levelArr) {
            if (expenditure >= integer) {
                level++;
            }
        }
        return level;
    }

    /**
     * 会员折扣策略
     * 根据会员等级返回不同的优惠折扣
     *
     * @param level 会员等级
     * @return 优惠折扣
     */
    public static double getDiscount(int level) {
        if (level == 0) {
            return 1;
        }
        else {
            return discountArr[level - 1];
        }
    }

    /**
     * 会员积分策略
     * 单笔消费每满100，获取5积分
     *
     * @param expenditure 单笔消费金额
     * @return 会员积分
     */
    public static int getCredit(double expenditure) {
        return (int) Math.floor(expenditure / 100) * 5;
    }

}
